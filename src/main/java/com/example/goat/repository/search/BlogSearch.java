package com.example.goat.repository.search;

import com.example.goat.entity.Blog;
import com.example.goat.entity.NoticeEvent;
import com.example.goat.entity.ReplyBlog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BlogSearch {
    // 제목검색, 내용검색, 제목+내용검색
    // 페이징 처리까지

    Page<Blog> searchAll(String[] types, String keyword, Pageable pageable);
    Page<ReplyBlog> searchAllRe(String[] types, String keyword, Pageable pageable, Long blog_num);


}
