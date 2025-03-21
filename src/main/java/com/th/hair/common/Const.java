package com.th.hair.common;

public class Const {
    public static final int PAGE_SIZE = 10;

    public static final int SUCCESS = 1;
    public static final int FAIL = 0;
    public static final int SIGN_IN_SUCCESS = 1;
    public static final int PAGE_ROWCOUNT = 5; // 리뷰 페이징

    public static final String PASSWORD_IS_BLANK = "비밀번호를 입력해주세요.";
    public static final String ID_IS_BLANK = "아이디를 입력해주세요.";
    public static final String NM_IS_BLANK = "닉네임을 입력해주세요.";
    public static final String EMAIL_IS_BLANK = "이메일을 입력해주세요.";
    public static final String NOT_ALLOWED_ID = "아이디는 영어와 숫자만 사용할 수 있고 영어를 하나 이상 포함한 4~10자리이어야 합니다.";
    public static final String NOT_ALLOWED_PASSWORD = "비밀번호는 공백을 제외한 영어와 숫자, 특수문자를 하나 이상 포함한 8~16자리이어야 합니다.";
    public static final String NOT_ALLOWED_EMAIL = "올바르지 않은 이메일 형식입니다.";

    public static final String rtName = "refreshToken";
}
