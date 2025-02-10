package com.th.hair.user;

import com.th.hair.common.AppProperties;
import com.th.hair.common.Const;
import com.th.hair.common.MyCookieUtils;
import com.th.hair.entity.User;
import com.th.hair.exception.AuthErrorCode;
import com.th.hair.exception.CommonErrorCode;
import com.th.hair.exception.RestApiException;
import com.th.hair.repository.UserRepository;
import com.th.hair.response.ApiResponse;
import com.th.hair.response.ResVo;
import com.th.hair.security.AuthenticationFacade;
import com.th.hair.security.JwtTokenProvider;
import com.th.hair.security.MyPrincipal;
import com.th.hair.security.MyUserDetails;
import com.th.hair.user.model.UserCoordinateDto;
import com.th.hair.user.model.UserSignInDto;
import com.th.hair.user.model.UserSignInVo;
import com.th.hair.user.model.UserSignUpDto;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static com.th.hair.common.Const.rtName;
import static com.th.hair.exception.AuthErrorCode.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserSerivce {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final AppProperties appProperties;
    private final MyCookieUtils myCookieUtils;
    private final AuthenticationFacade authenticationFacade;

    @Transactional
    public ApiResponse<?> postSignUp(UserSignUpDto dto) {
        Optional<User> uidCheck = userRepository.findByUid(dto.getUid());
        if (uidCheck.isPresent()) {
            throw new RestApiException(DUPLICATED_UID);
        }
        Optional<User> emailCheck = userRepository.findByEmail(dto.getEmail());
        if (emailCheck.isPresent()) {
            throw new RestApiException(DUPLICATED_EMAIL);
        }
        String hashedUpw = passwordEncoder.encode(dto.getUpw());
        User user = new User();
        user.setNm(dto.getNm());
        user.setUpw(hashedUpw);
        user.setUid(dto.getUid());
        user.setEmail(dto.getEmail());

        userRepository.save(user);

        return new ApiResponse<>(null);
    }

    //로그인
    public UserSignInVo postSignIn(HttpServletResponse res, UserSignInDto dto) {
        Optional<User> optEntity = userRepository.findByUid(dto.getUid());
        User entity = optEntity.orElseThrow(() -> new RestApiException(AuthErrorCode.LOGIN_FAIL));
        if (!passwordEncoder.matches(dto.getUpw(), entity.getUpw())) {
            throw new RestApiException(AuthErrorCode.LOGIN_FAIL);
        }

        MyPrincipal myPrincipal = MyPrincipal.builder()
                .iuser(entity.getIuser())
                .build();

        String at = jwtTokenProvider.generateAccessToken(myPrincipal);
        String rt = jwtTokenProvider.generateRefreshToken(myPrincipal);

        int rtCookieMaxAge = appProperties.getJwt().getRefreshCookieMaxAge();
        myCookieUtils.deleteCookie(res, rtName);
        myCookieUtils.setCookie(res, rtName, rt, rtCookieMaxAge);

        return UserSignInVo.builder()
                .accessToken(at)
                .build();
    }

    //로그 아웃
    public ResVo signout(HttpServletResponse res, HttpServletRequest req) {
        SecurityContextLogoutHandler logoutHandler = new SecurityContextLogoutHandler();
        logoutHandler.logout(req, res, SecurityContextHolder.getContext().getAuthentication());
        myCookieUtils.deleteCookie(res, "accessToken");
        myCookieUtils.deleteCookie(res, "refreshToken");
        return new ResVo(Const.SUCCESS);
    }

    //accessToken 재발급
    public UserSignInVo getRefreshToken(HttpServletRequest req) {
        Cookie cookie = myCookieUtils.getCookie(req, rtName);
        if (cookie == null) {
            return UserSignInVo.builder()
                    .accessToken(null)
                    .build();
        }
        String token = cookie.getValue();
        if (!jwtTokenProvider.isValidateToken(token)) {
            return UserSignInVo.builder()
                    .accessToken(null)
                    .build();
        }
        MyUserDetails myUserDetails = (MyUserDetails) jwtTokenProvider.getUserDetailsFromToken(token);
        MyPrincipal myPrincipal = myUserDetails.getMyPrincipal();

        String at = jwtTokenProvider.generateAccessToken(myPrincipal);

        return UserSignInVo.builder()
                .accessToken(at)
                .build();
    }
}
