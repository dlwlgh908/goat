package com.example.goat.entity;


import jakarta.persistence.*;
import lombok.*;

@Entity // 엔티티임을 명시
@Getter
@Setter
@ToString
@NoArgsConstructor // 기본 생성자
@AllArgsConstructor
public class Place {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "place_num")
    private Long num;

    private String place_name;

    private Long API_id;

    private String category_name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="cno")
    private City city;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="blog_num")
    private Blog blog;

}
