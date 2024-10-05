package com.example.goat.repository;

import com.example.goat.dto.BlogDTO;
import com.example.goat.dto.PlaceDTO;
import com.example.goat.entity.Account;
import com.example.goat.entity.Blog;
import com.example.goat.entity.City;
import com.example.goat.entity.Vote;
import com.example.goat.repository.search.BlogSearch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BlogRepository extends JpaRepository<Blog, Long>, BlogSearch {

    @Query("select a from Blog a where a.account.email=:email and a.num=:num")
    Blog userCheck(String email, Long num);

    @Query("select new com.example.goat.dto.BlogDTO(b.title as title, c.cname as cname, b.recommendCount as recommendCount, b.num as num) from Blog b join City c on b.city.cno = c.cno order by b.recommendCount desc limit 5")
    public List<BlogDTO> mainBlogRank();


}
