package com.example.goat.repository.search;

import com.example.goat.entity.Blog;
import com.example.goat.entity.QBlog;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.JPQLQuery;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.Arrays;
import java.util.List;

@Log4j2
public class BlogSearchImpl extends QuerydslRepositorySupport implements BlogSearch{


    public  BlogSearchImpl(){super(Blog.class);}
    @Override
    public Page<Blog> searchAll(String[] types, String keyword, Pageable pageable) {
        QBlog blog = QBlog.blog; // q 도메인 객체, select * from blog
        JPQLQuery<Blog> query = from(blog);
        log.info("searchAll입력된 값" + Arrays.toString(types));
        log.info("searchAll입력된 값" + keyword);

        System.out.println(query);
        //and 혹은 or 등을 사용하는 객체
        //select * from board // 제목을 입력
        // %ddd% select * from board where title = %안녕%
        //String[] types 만들어서 올꺼임 t c w title content writer
        // tc title = %keyword% or content = %keyword%

        //select * from board where a = s or
        if (types != null && types.length > 0 && keyword != null) {
            BooleanBuilder booleanBuilder = new BooleanBuilder();
            for (String str : types) {

                switch (str) {
                    case "t":
                        booleanBuilder.or(blog.title.contains(keyword));
                        break;

                    case "c":
                        booleanBuilder.or(blog.content.contains(keyword));
                        break;

                    case "w":
                        booleanBuilder.or(blog.account.name.contains(keyword));
                        break;
                    case "y":
                        booleanBuilder.or(blog.city.name.contains(keyword));
                        break;
                }
            }
            query.where(booleanBuilder);

        }


        query.where(blog.num.gt(0L));
        System.out.println("where 문 추가" + query);
        //select * from board where title = %keyword% or content = %keyword% and bno > 0

        this.getQuerydsl().applyPagination(pageable, query);//리미트 알려주

        List<Blog> blogList = query.fetch();
        log.info("서치 임플에 찍혔는지 확인");

        long count = query.fetchCount(); // row수


        return new PageImpl<>(blogList, pageable, count);
    }
}
