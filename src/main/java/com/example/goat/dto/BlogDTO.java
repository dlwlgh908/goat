package com.example.goat.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class BlogDTO {

    private Long	blog_num;

    @NotBlank(message = "제목은 필수 입력 사항입니다.")
    private String	title;

    @NotBlank(message = "내용은 필수 입력 사항입니다.")
    private String	content;

    @Builder.Default
    private int	schedule = 1;

    @Builder.Default
    private int	companion = 1 ;

    private LocalDateTime reg;

    private Long	readCount;

    private Long	recommendCount;

    private Long	account_num;

    private int	city_num;
}
