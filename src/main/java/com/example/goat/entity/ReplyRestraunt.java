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
public class ReplyRestraunt {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "replyRestraunt_num")
    private Long num;

    private String	writer;

    @Size(min = 2, max = 200)
    @Column(nullable = false)
    private String	content;

    private LocalDateTime reg;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="restraunt_num")
    private Restraunt restraunt;



}
