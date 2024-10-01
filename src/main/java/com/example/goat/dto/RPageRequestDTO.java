package com.example.goat.dto;

import lombok.*;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;


@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RPageRequestDTO {
    @Builder.Default // 들어오면 들어온 값 안들어오면 이 값
    private int page = 1;
    @Builder.Default //들어오면 들어온값 안들어오면 이 값 ㅁ라그대로 디폴트
    private int size = 10;


    private String type; // 검색 종류에서 select box : t, c, w, y(타이틀 컨텐츠 작성자 도시)
    // <option value = "t"> 제목 </option>
    // <option value = "c"> 내용</option>
    // <option value = "w"> 작성자 </option>
    // <option value = "y"> 도시 </option>
    // <option value = "tc"> 제목 내용</option>
    // <option value = "ty"> 제목 도시</option>

    private String keyword;

    private String link; // 링크를 만들어줌  ~~~ www.naver.com?bno=3&size=&page=3

    public String[] getTypes(){
        if(type==null || type.isEmpty()){
            return null;
        }
        return type.split("");
    } // 셀렉트 박스 값에 따라 검색 조건을 배열로 담는다.
    public Pageable getPageable(String...props){
        return PageRequest.of(this.page-1, this.size, Sort.by(props).descending());
    }
    public String getLink(){

        if (link == null){
            StringBuffer buffer = new StringBuffer();

            buffer.append("page=" + this.page);
            buffer.append("&size=" + this.size);

            if (type != null && type.length() > 0){

                buffer.append("&type=" + type);

            }

            if (keyword != null){

                try {
                    buffer.append("&keyword=" + URLEncoder.encode(keyword, "UTF-8"));
                } catch (UnsupportedEncodingException e){

                }

            }

            link = buffer.toString();

        }

        return link;

    }
}
