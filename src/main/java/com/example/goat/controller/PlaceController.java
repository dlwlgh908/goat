package com.example.goat.controller;

import com.example.goat.dto.*;
import com.example.goat.repository.VoteRepository;
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

@Controller
@Log4j2
@RequiredArgsConstructor
@RequestMapping("/place")
public class PlaceController {

    private final BlogService blogService;
    private final ReplyBlogService replyBlogService;
    private final VoteRepository voteRepository;

    @GetMapping("/register")
    public void register(BlogDTO blogDTO){
        log.info("등록 get 진입 완료");
    }

    @PostMapping("/register")
    public String registerPost(@Valid BlogDTO blogDTO, BindingResult bindingResult, Model model, @AuthenticationPrincipal UserDetails user){


        if(bindingResult.hasErrors()){
            return "place/register";
        }
        blogService.register(blogDTO, user);
        return "redirect:/place/list";
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

        return "place/list";
    }

    @GetMapping("/detale")
    public void detale(@AuthenticationPrincipal UserDetails user, Model model, Long num, RPageRequestDTO rPageRequestDTO){


        model.addAttribute("blogDTO", blogService.detale(num));
        RPageResponseDTO<ReplyBlogDTO> rPageResponseDTO =  replyBlogService.list(rPageRequestDTO, num);
        log.info("값이 있는지"+rPageResponseDTO);
        model.addAttribute("replyBlogDTO",new ReplyBlogDTO());
        model.addAttribute("rPageResponseDTO",rPageResponseDTO);
        log.info("get 디테일 진입 완료");
        if(voteRepository.voteCheck(user.getUsername(),num)==null){
            log.info("1번으로 체크됨");
            model.addAttribute("voteCheck", "1");
        }

        else model.addAttribute("voteCheck","2");
        log.info("2번으로 체크됨");


    }

    @GetMapping("/delete")
    public String delete(Model model, Long num){
       blogService.delete(num);
       return "redirect:/place/list";
    }
    @GetMapping("/modify")
    public String modify(Model model, Long num){
        model.addAttribute("blogDTO", blogService.detale(num));
        return "place/modify";
    }
    @PostMapping("/modify")
    public String modify(@Valid BlogDTO blogDTO, BindingResult bindingResult){


        if(bindingResult.hasErrors()){
            return "place/modify";
        }
        blogService.modify(blogDTO);
        return "redirect:/place/list";
    }


    @GetMapping("/vote")
    public String vote(Long num, @AuthenticationPrincipal UserDetails user, VoteDTO voteDTO){
        log.info("넘버"+num);
        log.info("유저"+user);
        blogService.vote(num, user, voteDTO);

        return "redirect:/place/detale?num="+num;
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
