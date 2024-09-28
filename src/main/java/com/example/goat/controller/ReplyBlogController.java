package com.example.goat.controller;

import com.example.goat.dto.BlogDTO;
import com.example.goat.dto.PageRequestDTO;
import com.example.goat.dto.PageResponseDTO;
import com.example.goat.dto.ReplyBlogDTO;
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

@Controller
@Log4j2
@RequiredArgsConstructor
@RequestMapping("/replyblog")
public class ReplyBlogController {

    private final ReplyBlogService replyBlogService;
    private final BlogService blogService;

    @PostMapping("/register")
    public String registerPost(@Valid ReplyBlogDTO replyBlogDTO, BindingResult bindingResult,
                               PageRequestDTO pageRequestDTO,Model model){
        log.info(replyBlogDTO);

        if(bindingResult.hasErrors()){
            BlogDTO blogDTO =  blogService.detale(replyBlogDTO.getBlog_num());
            model.addAttribute("blogDTO",blogDTO);

            PageResponseDTO<ReplyBlogDTO> pageRequestDTOPageResponseDTO = replyBlogService.list(pageRequestDTO, replyBlogDTO.getBlog_num());
            model.addAttribute("pageRequestDTOPageResponseDTO",pageRequestDTOPageResponseDTO);
            return "blog/detale";
        }

        replyBlogService.register(replyBlogDTO);
        return "redirect:/blog/detale?num="+replyBlogDTO.getBlog_num();

    }

    @PostMapping("/modify")
    public String modifyPost(@Valid ReplyBlogDTO replyBlogDTO, BindingResult bindingResult,
                             PageRequestDTO pageRequestDTO,Model model){
        log.info(replyBlogDTO+"체크포인트 09271630");
        replyBlogService.modify(replyBlogDTO);
        return "redirect:/blog/detale?num="+replyBlogDTO.getBlog_num();
    }
        @GetMapping("/delete")
    public String delete(Long num, Long blognum){
       replyBlogService.delete(num);
            return "redirect:/blog/detale?num="+blognum;
    }

//    @PostMapping("/register")
//    public String registerPost(@Valid ReplyBlogDTO replyBlogDTO, BindingResult bindingResult, Model model){
//
//
//        if(bindingResult.hasErrors()){
//            return "blog/detale";
//        }
//        blogService.register(blogDTO);
//        return "redirect:/blog/list";
//    }
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
//    public void detale(Model model, Long num){
//
//        model.addAttribute("blogDTO", blogService.detale(num));
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
