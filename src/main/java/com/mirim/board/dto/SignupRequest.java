package com.mirim.board.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class SignupRequest {
    @NotBlank(message = "아이디는 필수입니다.")//로그인 아이디가 비어있으면 안된다.
    @Size(min =4, max = 20, message = "아이디는 4~20자 이내 여야 합니다.")
    private String loginId;

    @NotBlank(message = "패스워드느 필수입니다.")//로그인 아이디가 비어있으면 안된다.
    @Size(min =8, max = 20, message = "패스워드는 8~20자 이내 여야 합니다.")
    private String password;

    @NotBlank(message = "사용자 이름은 필수입니다.")//로그인 아이디가 비어있으면 안된다.
    @Size(max = 30, message = "사용자 이름은 30자 이내 여야 합니다.")
    private String name;

    public String getLoginId() {
        return loginId;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }
}
