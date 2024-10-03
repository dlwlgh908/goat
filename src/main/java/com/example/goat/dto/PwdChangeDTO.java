package com.example.goat.dto;


import com.example.goat.constant.Role;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class PwdChangeDTO {

    private Long accountNum;

    // 비밀번호 변경을 위한 필드 추가
    @NotEmpty(message = "현재 비밀번호는 필수 입력 사항입니다.")
    private String currentPassword;

    @NotEmpty(message = "새 비밀번호는 필수 입력 사항입니다.")
    @Size(min = 2, max = 20, message = "새 비밀번호는 2자 이상 20자 이하이어야 합니다.")
    private String newPassword;

    @NotEmpty(message = "새 비밀번호 확인은 필수 입력 사항입니다.")
    private String confirmPassword;

    // 새 비밀번호와 비밀번호 확인 일치 확인
    @AssertTrue(message = "새 비밀번호와 비밀번호 확인이 일치해야 합니다.")
    public boolean isNewPasswordMatching() {
        return newPassword != null && newPassword.equals(confirmPassword);
    }


}