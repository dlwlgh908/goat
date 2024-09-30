package com.example.goat.dto;

import com.example.goat.entity.Account;
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
public class VoteDTO {

    private Long vno;

    private Long accountNum;

    private Long blog_num;
}
