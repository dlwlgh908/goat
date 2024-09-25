package com.example.goat.controller;

import com.example.goat.dto.BlogDTO;
import com.example.goat.dto.PageRequestDTO;
import com.example.goat.dto.PageResponseDTO;
import com.example.goat.entity.Blog;
import com.example.goat.service.BlogService;
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
@RequestMapping("/blog")
public class BlogController {

    private final BlogService blogService;

    @GetMapping("/register")
    public void register(BlogDTO blogDTO){
        log.info("등록 get 진입 완료");
    }

    @PostMapping("/register")
    public String registerPost(@Valid BlogDTO blogDTO, BindingResult bindingResult, Model model){


        blogService.register(blogDTO);
        return "blog/register";
    }

    @GetMapping("/list")
    public void list(Model model, PageRequestDTO pageRequestDTO){

        log.info("페이지 리퀘스트 들어오는지 테스트 : "+pageRequestDTO);
        PageResponseDTO<BlogDTO> blogDTOPageResponseDTO = blogService.list(pageRequestDTO);

        blogDTOPageResponseDTO.getDtoList().forEach(blogDTO -> log.info(blogDTO));
        model.addAttribute("boardDTOPageResponseDTO", boardDTOPageResponseDTO);

        return null;
    }

}
