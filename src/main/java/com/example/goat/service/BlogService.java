package com.example.goat.service;

import com.example.goat.dto.BlogDTO;
import com.example.goat.dto.PageRequestDTO;
import com.example.goat.dto.PageResponseDTO;

import java.util.List;

public interface BlogService {

    public void register(BlogDTO blogDTO);

    public List<BlogDTO> selectAll();

    public void upadate(BlogDTO blogDTO);

    public void delete(Long num);

    public BlogDTO detale(Long num);

    public PageResponseDTO<BlogDTO> list(PageRequestDTO pageRequestDTO);

    void modify(BlogDTO blogDTO);
    //페이징 처리, 검색 처리 한 목록

}
