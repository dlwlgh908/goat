package com.example.goat.dto;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor      //이거 빠졌ㄸ$ㅏ
@Builder
public class CityDTO {

    private Long cno;

    private String cname;
}
