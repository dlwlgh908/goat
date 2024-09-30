package com.example.goat.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity // 엔티티임을 명시
@Getter
@Setter
@ToString
@NoArgsConstructor // 기본 생성자
@AllArgsConstructor
public class Vote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long vno;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="account_num")
    private Account	account;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="blog_num")
    private Blog	blog;

}
