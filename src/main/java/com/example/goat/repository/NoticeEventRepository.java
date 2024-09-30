package com.example.goat.repository;

import com.example.goat.entity.Account;
import com.example.goat.entity.NoticeEvent;
import com.example.goat.repository.search.BlogSearch;
import com.example.goat.repository.search.NoticeSearch;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoticeEventRepository extends JpaRepository<NoticeEvent, Long>, NoticeSearch {
}
