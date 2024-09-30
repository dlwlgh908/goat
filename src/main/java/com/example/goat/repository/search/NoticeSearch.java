package com.example.goat.repository.search;

import com.example.goat.entity.NoticeEvent;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface NoticeSearch {

    Page<NoticeEvent> searchAllNo(String[] types, String keyword, Pageable pageable);
}
