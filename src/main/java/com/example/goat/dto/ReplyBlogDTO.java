package com.example.goat.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;

@Setter
@Getter
@ToString
public class ReplyBlogDTO {

    private Long	replyBlog_num;

    private String	writer;

    @Length(min = 2, max = 50, message = "내용은 2글자 이상 50글자 이하로 작성해주십시오.")
    @NotBlank(message = "내용은 필수 입력 사항입니다.")
    private String	content;

    private LocalDateTime reg;

    private long account_num;

    private long blog_num;

}
