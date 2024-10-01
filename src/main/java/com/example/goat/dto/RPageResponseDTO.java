package com.example.goat.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class RPageResponseDTO<E> {
    private int page; // 현재 페이지
    private int size; // 한페이지 row 수
    private int total; // row 총 개수, 검색 시 총 수량 변경
    //next 에서 마지막이니? 물어 볼 수 있음
    //next는 boolean
    private int start; //  시작 페이지 번호
    private int end; // 끝페이지 번호
    private boolean prev; // 이전 페이지 존재 여부
    private boolean next; // 다음 페이지 존재 여부
    private List<E> dtoList; // 목록에 대한 결과값, select * from table
    // 다른 곳에서도 사용이 가능하도록 컬렉션 사용<Notice>
    // List<Board> List<Member> List


    @Builder(builderMethodName = "withAll")
    public RPageResponseDTO(RPageRequestDTO rPageRequestDTO, List<E> dtoList, int total){
        if(total<=0){
            return;
        }
        this.page = rPageRequestDTO.getPage();
        this.size = rPageRequestDTO.getSize();
        this.total = total;
        this.dtoList=dtoList;


        this.end = (int)(Math.ceil(this.page/10.0)) * 10;
        this.start = this.end - 9 ;
        int last = (int)(Math.ceil(total/(double) size));
        this.end = end>last ? last : end;
        this.prev = this.start > 1;
        this.next = total > this.end * this.size;

    }
}
