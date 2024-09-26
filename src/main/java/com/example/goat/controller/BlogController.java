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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    public void detale(Model model, Long num){

        model.addAttribute("blogDTO", blogService.detale(num));
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
}
