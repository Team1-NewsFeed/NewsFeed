package com.sparta.newsfeed.dto;

import com.sparta.newsfeed.entity.UserStatus;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;


@Getter
public class SignUpRequestDto {

    //아이디
    @NotBlank(message = "사용자 아이디는 비워둘 수 없습니다.")
    @Size(min = 10, max = 20, message = "사용자 ID는 최소 10글자 이상, 최대 20글자 이하여야 합니다.")
    @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "사용자 ID는 대소문자 포함 영문과 숫자만 허용됩니다.")
    private String userId;

    // 비밀번호
    @NotBlank(message = " 비밀번호는 비워둘 수 없습니다.")
    @Size(min = 10, message = "비밀번호는 최소 10글자 이상이어야 합니다.")
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[~!@#$%^&*()_+])[A-Za-z\\d~!@#$%^&*()_+]{10,}$"
            , message = "비밀번호는 대소문자 포함 영문, 숫자, 특수문자를 최소 1글자씩 포함해야 합니다.")
    private String password;

    //이름
//    @NotBlank(message = " 이름을 비워둘 수 없습니다.")
    private String username;

    //이매일
//    @NotBlank(message = " 이매일을 비워둘 수 없습니다.")
    @Email
    private String email;

    //한줄 소개
    private String one_liner;

    //유저 상태 코드
    private UserStatus userStatus = UserStatus.ACTIVE;
}
