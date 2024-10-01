package com.example.goat.dto;

import com.example.goat.entity.Blog;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDate;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor      //이거 빠졌ㄸ$ㅏ
@Builder
public class PlaceDTO {

    private Long num;

    private String place_name;

    private Long api_id;

    private String category_name;

    private Long blog_num;
}
