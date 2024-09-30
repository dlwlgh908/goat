package com.example.goat.repository.search;

import com.example.goat.entity.*;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.JPQLQuery;
import lombok.extern.log4j.Log4j2;
import org.hibernate.validator.internal.util.logging.Log;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.Arrays;
import java.util.List;

@Log4j2
public class NoticeSearchImpl extends QuerydslRepositorySupport implements NoticeSearch {

    public  NoticeSearchImpl(){

        super(NoticeEvent.class);
    }
    @Override
    public Page<NoticeEvent> searchAllNo(String[] types, String keyword, Pageable pageable) {

        log.info("123");

        QNoticeEvent noticeEvent = QNoticeEvent.noticeEvent; // q 도메인 객체, select * from blog
        JPQLQuery<NoticeEvent> query = from(noticeEvent);


        System.out.println(query);

        //select * from board where 타입 = 키워드
        if (types != null && types.length > 0 && keyword != null) {
            BooleanBuilder booleanBuilder = new BooleanBuilder();
            for (String str : types) {

                switch (str) {

                    case "c":
                        booleanBuilder.or(noticeEvent.content.contains(keyword));
                        break;

                    case "t":
                        booleanBuilder.or(noticeEvent.title.contains(keyword));
                        break;
                }
            }
            query.where(booleanBuilder);

        }


        query.where(noticeEvent.num.gt(0L));
        System.out.println("where 문 추가" + query);
        //select * from board where title = %keyword% or content = %keyword% and bno > 0

        this.getQuerydsl().applyPagination(pageable, query);//리미트 알려주
        List<NoticeEvent> noticeEventList = query.fetch();

        log.info("찍히나요?"+noticeEventList);



        long count = query.fetchCount(); // row수


        return new PageImpl<>(noticeEventList, pageable, count);
    }
}
