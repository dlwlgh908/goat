package com.example.goat.controller;

import com.example.goat.dto.*;
import com.example.goat.entity.Blog;
import com.example.goat.entity.Place;
import com.example.goat.entity.Vote;
import com.example.goat.repository.BlogRepository;
import com.example.goat.repository.PlaceRepository;
import com.example.goat.repository.VoteRepository;
import com.example.goat.service.BlogService;
import com.example.goat.service.PlaceService;
import com.example.goat.service.ReplyBlogService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletResponse;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;

@Controller
@Log4j2
@RequiredArgsConstructor
@RequestMapping("/place")
public class PlaceController {

    private final BlogService blogService;
    private final ReplyBlogService replyBlogService;
    private final VoteRepository voteRepository;
    private final BlogRepository blogRepository;

    private final PlaceService placeService;
    @GetMapping("/register")
    public void register(BlogDTO blogDTO){
        log.info("등록 get 진입 완료");
    }


    @PostMapping("/register")
    public String registerPost(@Valid BlogDTO blogDTO, BindingResult bindingResult, Model model, @AuthenticationPrincipal UserDetails user
            , Long[] nnum
    ){
        log.info(blogDTO);
        log.info(blogDTO);
        log.info(blogDTO);
        log.info(blogDTO);

        if(bindingResult.hasErrors()){
            return "blog/register";
        }
        Long blognum= blogService.register(blogDTO, user);

        if(nnum != null && nnum.length !=0){
            for(Long num   : nnum){
                log.info("저장된 장소값"+ num);
                //기존 저장된 장소의 값을 가져오는 service(장소num, 본문부모번호blognum)

                try {
                    Place place = placeService.readOne(num, blognum);

                    log.info("수정된 장소정보 : " + place);
                } catch (EntityNotFoundException e) {
                    model.addAttribute("msg", "장소정보를 찾을수 없습니다.");
                    return "place/register";
                }

                //sㅓ비스에서는 장소넘으로 저장된 장소엔티티를 찾고
                // 저장된 부모엔티티를 찾아서 이결과를
                //장소넘에 있는 set부모(이결과에)   하면 @tran젝션이 있으면 자동 업데이트
            }
        }
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
//        log.info("값이 있는지"+rPageResponseDTO);
        model.addAttribute("replyBlogDTO",new ReplyBlogDTO());
        model.addAttribute("rPageResponseDTO",rPageResponseDTO);
//        log.info("get 디테일 진입 완료");

        if(user != null){
            if(voteRepository.voteCheck(user.getUsername(),num)==null){
                log.info("1번으로 체크됨");
                model.addAttribute("voteCheck", "1");
            }else {
                model.addAttribute("voteCheck","2");
                log.info("2번으로 체크됨");
            }



        }




    }

    @GetMapping("/delete")
    public String delete(Model model, Long num){
        blogService.delete(num);
        return "redirect:/place/list";
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
        return "redirect:/place/list";
    }


    @GetMapping("/vote")
    public String vote(Long num, @AuthenticationPrincipal UserDetails user, VoteDTO voteDTO){
        log.info("넘버"+num);
        log.info("유저"+user);
        blogService.vote(num, user, voteDTO);

        return "redirect:/blog/detale?num="+num;
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

    @ResponseBody
    @PostMapping("/savePlaces")
    public   ResponseEntity savePlaces(@RequestBody PlaceDTO dto ){

        log.info(dto);

        Long result =  placeService.saveplace(dto);

        if(result == null){
            return new ResponseEntity<String>("야 틀렸어", HttpStatus.BAD_REQUEST);

        }


        return new ResponseEntity<Long>(result, HttpStatus.OK);

    }



}
