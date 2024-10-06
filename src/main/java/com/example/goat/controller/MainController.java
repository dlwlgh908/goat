package com.example.goat.controller;

import com.example.goat.dto.*;
import com.example.goat.entity.Place;
import com.example.goat.repository.BlogRepository;
import com.example.goat.repository.VoteRepository;
import com.example.goat.service.BlogService;
import com.example.goat.service.PlaceService;
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
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
@Log4j2
@RequiredArgsConstructor
public class MainController {

    private final PlaceService placeService;
    private final BlogService blogService;
//    private final BlogService blogService;
//    private final ReplyBlogService replyBlogService;
//    private final VoteRepository voteRepository;
//    private final BlogRepository blogRepository;
//
    @GetMapping("/main/home")
    public String main(Model model, BlogDTO blogDTO, PlaceDTO placeDTO){
        log.info("등록 get 진입 완료" + blogDTO);

        List<PlaceDTO> mainplaceDTO = placeService.mainRank(placeDTO);
//        List<Map<String, String>>  mainplaceDTO = new ArrayList<>();
//        Map<String , String> asf = new HashMap<>();
//        asf.put("total", "2");
//        asf.put("cname", "울산광역시");
//        mainplaceDTO.add(asf);
        model.addAttribute("mainRankDTO", mainplaceDTO);
        log.info(mainplaceDTO);

        List<PlaceDTO> mainplaceRestaurantDTO = placeService.mainRankRestaurant(placeDTO);
        model.addAttribute("mainRankRestaurantDTO", mainplaceRestaurantDTO);
        log.info(mainplaceRestaurantDTO);

        List<BlogDTO> mainedRankBlogDTO = blogService.mainRankBlog(blogDTO);
        model.addAttribute("mainRankBlogDTO", mainedRankBlogDTO);
        log.info(mainedRankBlogDTO);
        return "main/home";
    }


    @GetMapping("/place/list")
    public String placeList(Model model, PlaceDTO placeDTO){
        List<PlaceDTO> placeListDTO = placeService.list(placeDTO);
        log.info(placeListDTO);
        model.addAttribute("placeListDTO", placeListDTO);
        model.addAttribute("type", new String());
        return "place/list";
    }


}
