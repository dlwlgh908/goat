package com.example.goat.repository;

import com.example.goat.entity.Account;
import com.example.goat.entity.Blog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlogRepository extends JpaRepository<Blog, Long> {
}
