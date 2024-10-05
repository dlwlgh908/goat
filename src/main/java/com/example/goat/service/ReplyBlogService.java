package com.example.goat.service;

import com.example.goat.dto.*;

import java.security.Principal;
import java.util.List;

public interface ReplyBlogService {

    public void register(ReplyBlogDTO dto, Principal principal);

    public List<ReplyBlogDTO> selectAll();


    public void delete(Long num);

    public ReplyBlogDTO detale(Long num);

    public RPageResponseDTO<ReplyBlogDTO> list(RPageRequestDTO rPageRequestDTO, Long blog_num);

    void modify(ReplyBlogDTO dto);
    //페이징 처리, 검색 처리 한 목록

}
