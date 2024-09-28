package com.example.goat.dto;


import com.example.goat.constant.Role;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
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
}