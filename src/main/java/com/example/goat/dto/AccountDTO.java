package com.example.goat.dto;


import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class AccountDTO {

    private Long accountNum;

    @NotBlank(message = "이메일은 필수 입력 사항입니다.")
    @Email(message = "유효한 이메일 형식이 아닙니다 ")
    private String email;

    @NotBlank(message = "비밀번호는 필수 입력 사항입니다.")
    @Size(min = 2, message = "비밀번호는 최소 2자 이상이여야합니다")
    private String password1;

    @NotBlank(message = "비밀번호 확인은 필수 입력 사항입니다.")
    private String password2;

    @NotBlank(message = "이름은 필수 입력 사항입니다.")
    private String name;

    @NotBlank(message = "연락처는 필수 입력 사항입니다.")
    @Pattern(regexp = "^\\d{10,11}$", message = "유효한 연락처 형식이 아닙니다. 10자리 또는 11자리 숫자를 입력하세요")
    private String phone;

    private String emailDomain;


    private int role;

    public Long getAccount_num() {
        return null;
    }

}