package com.example.goat.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Entity // 엔티티임을 명시
@Getter
@Setter
@ToString
@NoArgsConstructor // 기본 생성자
@AllArgsConstructor // 모든 parameter를 가지고 있는 생성자
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "account_num")
    private Long num;

    private String	email;

    private String	password1;

    private String	password2;

    private String	name;

    private String	Phone;

    private int role;
}
