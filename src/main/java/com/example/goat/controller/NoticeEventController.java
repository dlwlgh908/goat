package com.example.goat.controller;

import com.example.goat.dto.*;
import com.example.goat.service.BlogService;
import com.example.goat.service.NoticeEventService;
import com.example.goat.service.ReplyBlogService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@Log4j2
@RequiredArgsConstructor
@RequestMapping("/notice")
public class NoticeEventController {

    private final NoticeEventService noticeEventService;

    @GetMapping("/register")
    public void register(NoticeEventDTO noticeEventDTO){
        log.info("등록 get 진입 완료");
    }

    @PostMapping("/register")
    public String registerPost(@Valid NoticeEventDTO noticeEventDTO, BindingResult bindingResult, Model model){


        if(bindingResult.hasErrors()){
            return "notice/register";
        }
        noticeEventService.register(noticeEventDTO);
        return "redirect:/notice/list";
    }

@ResponseBody
@GetMapping("/countappend")
public ResponseEntity<String> countappend(Long num){

log.info(num);
    //병렬로 연결되서 증가되는 함수

    try {
        noticeEventService.countappend(num);   //증가시킬 본문번호 를 사용 메소드 호출
    } catch (EntityNotFoundException e) {

        return new ResponseEntity<String>("fail", HttpStatus.BAD_REQUEST);
    }

    return new ResponseEntity<String>("success", HttpStatus.OK);






}


    @GetMapping("/list")
    public String list(PageRequestDTO pageRequestDTO, Model model){

        //log.info("페이지 리퀘스트 들어오는지 테스트 : "+pageRequestDTO);

        PageResponseDTO<NoticeEventDTO> noticeEventDTOPageResponseDTO = noticeEventService.list(pageRequestDTO);

        //log.info("노티스 페이지 리퀘스트 들어오는지 테스트 : "+noticeEventDTOPageResponseDTO);

        model.addAttribute("noticeEventDTOPageResponseDTO", noticeEventDTOPageResponseDTO);


        return "notice/list";
    }

    @GetMapping("/detale")
    public void detale(Model model, Long num){


        model.addAttribute("noticeEventDTO", noticeEventService.detale(num));
        log.info("get 디테일 진입 완료");
    }

    @GetMapping("/delete")
    public String delete(Model model, Long num){
       noticeEventService.delete(num);
       return "redirect:/notice/list";
    }
    @GetMapping("/modify")
    public String modify(Model model, Long num){
        model.addAttribute("noticeEventDTO", noticeEventService.detale(num));
        return "notice/modify";
    }
    @PostMapping("/modify")
    public String modify(@Valid NoticeEventDTO noticeEventDTO, BindingResult bindingResult){


        if(bindingResult.hasErrors()){
            return "notice/modify";
        }
        noticeEventService.modify(noticeEventDTO);
        return "redirect:/notice/list";
    }
}
