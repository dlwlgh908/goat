package com.example.goat.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity // 엔티티임을 명시
@Getter
@Setter
@ToString
@NoArgsConstructor // 기본 생성자
@AllArgsConstructor // 모든 parameter를 가지고 있는 생성자
public class Blog extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "blog_num")
    private Long num;
//PK ID 값



    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="account_num")
    private Account account;

    //Acc랑 조인


    @Size(min = 2, max = 50)
    @Column(nullable = false)
    private String	title;
    //제목



    @Size(min = 2, max = 200)
    private String	content;
    //내용


    @Column(nullable = false)
    private int	schedule;
    //당일치기 ~ 2박 3일
    // 1== 당일치기 2== 1박 2일 3== 2박 3일




    private Long	readCount;
    // 조회수

    private Long	recommendCount;
    //추천수


    private String city;
    // 도시





}
