package com.example.goat.service;

import com.example.goat.dto.*;
import com.example.goat.entity.Blog;
import com.example.goat.entity.Place;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

public interface BlogService {

    public Long register(BlogDTO blogDTO, UserDetails user);

    public List<BlogDTO> selectAll();

    public void upadate(BlogDTO blogDTO);

    public void delete(Long num);

    public BlogDTO detale(Long num);

    public PageResponseDTO<BlogDTO> list(PageRequestDTO pageRequestDTO);

    void modify(BlogDTO blogDTO);
    //페이징 처리, 검색 처리 한 목록

    public Blog countappend(Long num);

    public void vote(Long num, UserDetails user, VoteDTO voteDTO);
    public List<BlogDTO> mainRankBlog(BlogDTO blogDTO);
}
