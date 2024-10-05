package com.example.goat.controller;

import com.example.goat.dto.*;
import com.example.goat.service.BlogService;
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

import java.security.Principal;

@Controller
@Log4j2
@RequiredArgsConstructor
@RequestMapping("/replyblog")
public class ReplyBlogController {

    private final ReplyBlogService replyBlogService;
    private final BlogService blogService;

    @PostMapping("/register")
    public String registerPost(@Valid ReplyBlogDTO replyBlogDTO, BindingResult bindingResult,
                               RPageRequestDTO rPageRequestDTO, Model model, Principal principal) {
        log.info(replyBlogDTO);

        if (bindingResult.hasErrors()) {
            BlogDTO blogDTO = blogService.detale(replyBlogDTO.getBlog_num());
            model.addAttribute("blogDTO", blogDTO);

            RPageResponseDTO<ReplyBlogDTO> pageRequestDTOPageResponseDTO = replyBlogService.list(rPageRequestDTO, replyBlogDTO.getBlog_num());
            model.addAttribute("pageRequestDTOPageResponseDTO", pageRequestDTOPageResponseDTO);
            return "blog/detale";
        }


        replyBlogService.register(replyBlogDTO, principal);
        return "redirect:/blog/detale?num=" + replyBlogDTO.getBlog_num();

    }

    @PostMapping("/modify")
    public String modifyPost(@Valid ReplyBlogDTO replyBlogDTO, BindingResult bindingResult,
                             RPageRequestDTO rPageRequestDTO, Model model) {
        log.info(replyBlogDTO + "체크포인트 09271630");
        replyBlogService.modify(replyBlogDTO);
        return "redirect:/blog/detale?num=" + replyBlogDTO.getBlog_num();
    }

    @GetMapping("/delete")
    public String delete(Long num, Long blognum) {
        replyBlogService.delete(num);
        return "redirect:/blog/detale?num=" + blognum;
    }
}