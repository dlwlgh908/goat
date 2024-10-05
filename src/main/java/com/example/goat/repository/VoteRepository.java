package com.example.goat.repository;

import com.example.goat.entity.Account;
import com.example.goat.entity.Blog;
import com.example.goat.entity.Vote;
import com.example.goat.repository.search.BlogSearch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface VoteRepository extends JpaRepository<Vote, Long> {

    void deleteByBlogNum (Long num);

    @Query("select a from Vote a where a.account.email=:email and a.blog.num=:num")
    Vote voteCheck(String email, Long num);
}
