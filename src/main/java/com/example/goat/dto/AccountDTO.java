package com.example.goat.dto;


import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class AccountDTO {

    private Long account_num;

    @NotBlank(message = "이메일은 필수 입력 사항입니다.")
    private String email;

    @NotBlank(message = "패스워드 1은 필수 입력 사항입니다.")
    private String pwd1;

    @NotBlank(message = "패스워드 2은 필수 입력 사항입니다.")
    private String pwd2;

    @NotBlank(message = "이름은 필수 입력 사항입니다.")
    private String name;

    @NotBlank(message = "연락처는 필수 입력 사항입니다.")
    private String Phone;

    private int role;
}