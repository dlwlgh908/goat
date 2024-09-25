package com.example.goat.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity // 엔티티임을 명시
@Getter
@Setter
@ToString
@NoArgsConstructor // 기본 생성자
@AllArgsConstructor // 모든 parameter를 가지고 있는 생성자
public class Restraunt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "restraunt_num")
    private Long num;

    private Long restraunt_key;


    private Long recommendCount;

}
