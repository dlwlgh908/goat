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
@AllArgsConstructor
// 모든 parameter를 가지고 있는 생성자
public class NoticeEvent extends BaseEntity {

//@lob 은 일반적인 최대 길이 255를 넘어서 문자를 저장하고 싶을 때 컬럼 데피니션으로 정의
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "noticeEvent_num")
    private Long num;

    @Column(length = 1, nullable = false)
    private int	type;

    @Size(min = 2, max = 50)
    @Column(nullable = false)
    private String	title;

    @Size(min = 2, max = 400)
    @Column(nullable = false)
    private String	content;


    private Long	readCount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="account_num")
    private Account account;

}
