package com.example.goat.service;

import com.example.goat.dto.BlogDTO;
import com.example.goat.dto.NoticeEventDTO;
import com.example.goat.dto.PageRequestDTO;
import com.example.goat.dto.PageResponseDTO;
import com.example.goat.entity.Blog;
import com.example.goat.entity.NoticeEvent;

import java.util.List;

public interface NoticeEventService {

    public void register(NoticeEventDTO noticeEventDTO);

    public List<NoticeEventDTO> selectAll();


    public void delete(Long num);

    public NoticeEventDTO detale(Long num);

    public PageResponseDTO<NoticeEventDTO> list(PageRequestDTO pageRequestDTO);

    void modify(NoticeEventDTO noticeEventDTO);
    //페이징 처리, 검색 처리 한 목록

    public NoticeEvent countappend(Long num);

    }
