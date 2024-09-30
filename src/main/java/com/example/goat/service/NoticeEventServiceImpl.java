package com.example.goat.service;

import com.example.goat.dto.NoticeEventDTO;
import com.example.goat.dto.PageRequestDTO;
import com.example.goat.dto.PageResponseDTO;
import com.example.goat.entity.Blog;
import com.example.goat.entity.NoticeEvent;
import com.example.goat.repository.NoticeEventRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Log4j2
@Transactional
public class NoticeEventServiceImpl implements NoticeEventService {

    private final NoticeEventRepository noticeEventRepository;
    private ModelMapper mapper = new ModelMapper();

    @Override
    public void register(NoticeEventDTO noticeEventDTO) {
        //등록 맵퍼로 맵핑해서 블로그 레파지토리에 세이브함

        NoticeEvent noticeEvent = mapper.map(noticeEventDTO, NoticeEvent.class);
        noticeEventRepository.save(noticeEvent);
    }

    @Override
    public List<NoticeEventDTO> selectAll() {
        List<NoticeEvent> boardList = noticeEventRepository.findAll();
        boardList.stream().map(abc -> mapper.map(abc, NoticeEventDTO.class)).collect(Collectors.toList());
        return null;
    }



    @Override
    public void delete(Long num) {
        noticeEventRepository.deleteById(num);
    }

    @Override
    public NoticeEventDTO detale(Long num) {
        log.info(num);
        NoticeEvent noticeEvent = noticeEventRepository.findById(num).get();
        NoticeEventDTO noticeEventDTO = mapper.map(noticeEvent , NoticeEventDTO.class);

        return noticeEventDTO;
    }

    @Override
    public PageResponseDTO<NoticeEventDTO> list(PageRequestDTO pageRequestDTO) {
        String[] types = pageRequestDTO.getTypes();
        log.info("서비스에서 변환된 : "+ Arrays.toString(types));
        String keyword = pageRequestDTO.getKeyword();
        Pageable pageable = pageRequestDTO.getPageable("num");
        Page<NoticeEvent> noticeEventPage = noticeEventRepository.searchAllNo(types, keyword, pageable);

        //보드 타입의 리스트가 >> 보드 DTO 타입의 리스트로 변환
        List<NoticeEventDTO> noticeEventDTOList =
                noticeEventPage.getContent().stream().map(
                        noticeEvent -> mapper.map(noticeEvent,NoticeEventDTO.class)).collect(Collectors.toList());

//        noticeEventDTOList.forEach(noticeEventDTO -> log.info(noticeEventDTO));

        return PageResponseDTO.<NoticeEventDTO>withAll()
                .pageRequestDTO(pageRequestDTO)
                .dtoList(noticeEventDTOList)
                .total((int) noticeEventPage.getTotalElements())
                .build();
    }

    @Override
    public void modify(NoticeEventDTO noticeEventDTO) {

        NoticeEvent noticeEvent = noticeEventRepository.findById(noticeEventDTO.getNum()).orElseThrow(EntityNotFoundException::new);
        noticeEvent.setContent(noticeEventDTO.getContent());
        noticeEvent.setTitle(noticeEventDTO.getTitle());
    }

    @Override
    public NoticeEvent countappend(Long num) {
        //읽을 본문을 읽어와서 엔티티매니저가 관리할수 있도록 한다.
        NoticeEvent noticeEvent =  noticeEventRepository.findById(num).orElseThrow(EntityNotFoundException::new);


        noticeEvent.setReadCount(noticeEvent.getReadCount()+1);

        return noticeEvent;
    }
}
