package com.example.goat.controller;

import com.example.goat.dto.*;
import com.example.goat.entity.Blog;
import com.example.goat.entity.Vote;
import com.example.goat.service.BlogService;
import com.example.goat.service.ReplyBlogService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;

@Controller
@Log4j2
@RequiredArgsConstructor
@RequestMapping("/blog")
public class BlogController {

    private final BlogService blogService;
    private final ReplyBlogService replyBlogService;

    @GetMapping("/register")
    public void register(BlogDTO blogDTO){
        log.info("등록 get 진입 완료");
    }

    @PostMapping("/register")
    public String registerPost(@Valid BlogDTO blogDTO, BindingResult bindingResult, Model model){


        if(bindingResult.hasErrors()){
            return "blog/register";
        }
        blogService.register(blogDTO);
        return "redirect:/blog/list";
    }

    @GetMapping("/list")
    public String list( PageRequestDTO pageRequestDTO, Model model){

        log.info("페이지 리퀘스트 들어오는지 테스트 : "+pageRequestDTO);

        PageResponseDTO<BlogDTO> blogDTOPageResponseDTO = blogService.list(pageRequestDTO);
//
//        if(blogDTOPageResponseDTO.getDtoList() != null){
//            blogDTOPageResponseDTO.getDtoList().forEach(blogDTO -> log.info(blogDTO));
//        }

        model.addAttribute("blogDTOPageResponseDTO", blogDTOPageResponseDTO);

        return "blog/list";
    }

    @GetMapping("/detale")
    public void detale(Model model, Long num, RPageRequestDTO rPageRequestDTO){


        model.addAttribute("blogDTO", blogService.detale(num));
        RPageResponseDTO<ReplyBlogDTO> rPageResponseDTO =  replyBlogService.list(rPageRequestDTO, num);
        log.info("값이 있는지"+rPageResponseDTO);
        model.addAttribute("replyBlogDTO",new ReplyBlogDTO());
        model.addAttribute("rPageResponseDTO",rPageResponseDTO);
        log.info("get 디테일 진입 완료");
    }

    @GetMapping("/delete")
    public String delete(Model model, Long num){
       blogService.delete(num);
       return "redirect:/blog/list";
    }
    @GetMapping("/modify")
    public String modify(Model model, Long num){
        model.addAttribute("blogDTO", blogService.detale(num));
        return "blog/modify";
    }
    @PostMapping("/modify")
    public String modify(@Valid BlogDTO blogDTO, BindingResult bindingResult){


        if(bindingResult.hasErrors()){
            return "blog/modify";
        }
        blogService.modify(blogDTO);
        return "redirect:/blog/list";
    }

    @GetMapping("/vote")
    public void vote(Long num, @AuthenticationPrincipal UserDetails user, VoteDTO voteDTO){
        log.info("넘버"+num);
        log.info("유저"+user);
        blogService.vote(num, user, voteDTO);
    }

    @ResponseBody
    @GetMapping("/countappend")
    public   ResponseEntity<String> countappend(Long num){


        //병렬로 연결되서 증가되는 함수

        try {
            blogService.countappend(num);   //증가시킬 본문번호 를 사용 메소드 호출
        } catch (EntityNotFoundException e) {

            return new ResponseEntity<String>("fail", HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<String>("success", HttpStatus.OK);






    }



}
