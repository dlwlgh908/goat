package com.example.goat.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor      //이거 빠졌ㄸ$ㅏ
@Builder
public class BlogDTO {

    private String cname;
    
    private Long cnum;
    // 엘리야스
    private Long	num;

    @NotBlank(message = "제목은 필수 입력 사항입니다.")
    @Length(min = 2, max = 50, message = "제목은 2 ~ 50글자 이내로 작성 부탁드립니다.")
    private String	title;

    @NotBlank(message = "내용은 필수 입력 사항입니다.")
    @Length(min = 2, max = 200, message = "내용은 2 ~ 200글자 이내로 작성 부탁드립니다.")
    private String	content;

    @Builder.Default
    private int	schedule = 1;

    private LocalDate reg;

    private Long	readCount;

    private Long	recommendCount;

    private String	email;
    private AccountDTO accountDTO;

    public BlogDTO setAccountDTO(AccountDTO accountDTO) {
        this.accountDTO = accountDTO;
        return this;
    }

    private String city;

    private List<PlaceDTO> placeDTOList = new ArrayList<>();
}
