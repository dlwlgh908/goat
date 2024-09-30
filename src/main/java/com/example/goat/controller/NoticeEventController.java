package com.example.goat.controller;

import com.example.goat.dto.*;
import com.example.goat.service.BlogService;
import com.example.goat.service.NoticeEventService;
import com.example.goat.service.ReplyBlogService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
//
//    @GetMapping("/list")
//    public String list( PageRequestDTO pageRequestDTO, Model model){
//
//        log.info("페이지 리퀘스트 들어오는지 테스트 : "+pageRequestDTO);
//
//        PageResponseDTO<BlogDTO> blogDTOPageResponseDTO = blogService.list(pageRequestDTO);
////
////        if(blogDTOPageResponseDTO.getDtoList() != null){
////            blogDTOPageResponseDTO.getDtoList().forEach(blogDTO -> log.info(blogDTO));
////        }
//
//        model.addAttribute("blogDTOPageResponseDTO", blogDTOPageResponseDTO);
//
//        return "blog/list";
//    }
//
//    @GetMapping("/detale")
//    public void detale(Model model, Long num, PageRequestDTO pageRequestDTO){
//
//
//        model.addAttribute("blogDTO", blogService.detale(num));
//        PageResponseDTO<ReplyBlogDTO> pageResponseDTO =  replyBlogService.list(pageRequestDTO, num);
//        log.info("값이 있는지"+pageResponseDTO);
//        model.addAttribute("replyBlogDTO",new ReplyBlogDTO());
//        model.addAttribute("pageResponseDTO",pageResponseDTO);
//        log.info("get 디테일 진입 완료");
//    }
//
//    @GetMapping("/delete")
//    public String delete(Model model, Long num){
//       blogService.delete(num);
//       return "redirect:/blog/list";
//    }
//    @GetMapping("/modify")
//    public String modify(Model model, Long num){
//        model.addAttribute("blogDTO", blogService.detale(num));
//        return "blog/modify";
//    }
//    @PostMapping("/modify")
//    public String modify(@Valid BlogDTO blogDTO, BindingResult bindingResult){
//
//
//        if(bindingResult.hasErrors()){
//            return "blog/modify";
//        }
//        blogService.modify(blogDTO);
//        return "redirect:/blog/list";
//    }
}
