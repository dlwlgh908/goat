package com.example.goat.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class NoticeEventDTO {

    private Long	noticeEvent_num;

    private int	type;

    private String	title;

    private String	content;

    private LocalDateTime reg;

    private Long	readCount;
}
