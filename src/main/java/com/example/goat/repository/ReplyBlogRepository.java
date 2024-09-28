package com.example.goat.repository;

import com.example.goat.entity.ReplyBlog;
import com.example.goat.repository.search.BlogSearch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ReplyBlogRepository extends JpaRepository<ReplyBlog, Long>, BlogSearch {

    void deleteByBlogNum (Long num);


}
