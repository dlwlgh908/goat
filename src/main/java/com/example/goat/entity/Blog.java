package com.example.goat.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="account_num")
    private Account account;

    @Size(min = 2, max = 50)
    @Column(nullable = false)
    private String	title;

    @Size(min = 2, max = 200)
    private String	content;

    @Column(nullable = false)
    private int	schedule;

    @Column(nullable = false)
    private int	companion;

    private LocalDateTime reg;

    private Long	readCount;

    private Long	recommendCount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="city_num")
    private City city;



}
