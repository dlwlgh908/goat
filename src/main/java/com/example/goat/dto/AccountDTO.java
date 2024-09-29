package com.example.goat.dto;


import com.example.goat.constant.Role;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.*;
import lombok.*;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
@ToString
public class AccountDTO {

    private Long accountNum;

    @NotEmpty(message = "이메일은 필수 입력 사항입니다.")
    @Email(message = "유효한 이메일 형식이 아닙니다 ")
    private String email;

    @NotEmpty(message = "비밀번호는 필수 입력 사항입니다.")
    @Size(min = 2, max = 20, message = "비밀번호는 2자 이상 20자 이하이어야 합니다.")
    private String password;

    @NotEmpty(message = "비밀번호 확인은 필수 입력 사항입니다.")
    private String password1;

    @NotEmpty(message = "이름은 필수 입력 사항입니다.")
    private String name;

    @NotEmpty(message = "연락처는 필수 입력 사항입니다.")
    private String phone;

    @NotNull(message = "권한을 선택해야 합니다")
    private Role role;

    // 비밀번호 변경을 위한 필드 추가
    @NotEmpty(message = "현재 비밀번호는 필수 입력 사항입니다.")
    private String currentPassword;

    @NotEmpty(message = "새 비밀번호는 필수 입력 사항입니다.")
    @Size(min = 2, max = 20, message = "새 비밀번호는 2자 이상 20자 이하이어야 합니다.")
    private String newPassword;

    @NotEmpty(message = "새 비밀번호 확인은 필수 입력 사항입니다.")
    private String confirmPassword;

    // 비밀번호 일치 확인
    @AssertTrue(message = "비밀번호와 비밀번호 확인이 일치해야 합니다.")
    public boolean isPasswordMatching() {
        return password != null && password.equals(password1);
    }

    // 새 비밀번호와 비밀번호 확인 일치 확인
    @AssertTrue(message = "새 비밀번호와 비밀번호 확인이 일치해야 합니다.")
    public boolean isNewPasswordMatching() {
        return newPassword != null && newPassword.equals(confirmPassword);
    }
}