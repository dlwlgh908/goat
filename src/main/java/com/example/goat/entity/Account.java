package com.example.goat.entity;

import com.example.goat.constant.Role;
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
    private Long ano;

    @Column(length = 50, unique = true, nullable = false)
    private String	email;

    private String	password;

    private String	password1;

    private String	name;

    private String	Phone;

    @Enumerated(EnumType.STRING)
    private Role role; //권한을 위하여
}
