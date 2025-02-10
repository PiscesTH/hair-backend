package com.th.hair.user;

import com.th.hair.response.ApiResponse;
import com.th.hair.response.ResVo;
import com.th.hair.user.model.UserCoordinateDto;
import com.th.hair.user.model.UserSignInDto;
import com.th.hair.user.model.UserSignInVo;
import com.th.hair.user.model.UserSignUpDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {
    private final UserSerivce service;

    @PostMapping("/sign-in")
    public ApiResponse<UserSignInVo> postSignIn(HttpServletResponse res, @Valid @RequestBody UserSignInDto dto) {
        return new ApiResponse<>(service.postSignIn(res, dto));
    }

    @PostMapping("/sign-up")
    public ApiResponse<?> postSignUp(@Valid @RequestBody UserSignUpDto dto) {
        return service.postSignUp(dto);
    }

    @PostMapping("/sign-out")
    public ApiResponse<ResVo> postSignout(HttpServletResponse res, HttpServletRequest req) {
        return new ApiResponse<>(service.signout(res, req));
    }

    @GetMapping("/refresh-token")
    public ApiResponse<UserSignInVo> getRefreshToken(HttpServletRequest req) {
        return new ApiResponse<>(service.getRefreshToken(req));
    }
}
