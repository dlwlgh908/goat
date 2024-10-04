package com.example.goat.controller;

import com.example.goat.dto.AccountDTO;
import com.example.goat.service.AccountService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.List;

@Controller
@Log4j2
@RequiredArgsConstructor
@RequestMapping("/account")
public class AccountController {
    private final AccountService accountService;

    //회원가입
    //회원가입을 하려면 회원가입 페이지를 사용자에게 보여줘야한다
    //회원가입페이지를 작성 후 돌려주는 것은 post로 받는다
   @GetMapping("/register")
    public String regist(AccountDTO accountDTO) {

       return "account/register";

   }

   //회원가입 처리
   @PostMapping("/register")
    public String registP(@Valid AccountDTO accountDTO,
                          BindingResult bindingResult, Model model) {
       log.info("회원가입 get에서 포스트로 넘어온 값 : " + accountDTO);

       // 비밀번호와 비밀번호 확인이 일치하는지 검사
       if (accountDTO.getPassword() == null || !accountDTO.getPassword().equals(accountDTO.getPassword1())) {
           bindingResult.rejectValue("password1", "error.password1", "비밀번호가 일치하지 않습니다.");
       }

       //비밀번호 확인 필드가 비어있는지 검사
       if (accountDTO.getPassword1() == null || accountDTO.getPassword1().isEmpty()) {
           bindingResult.rejectValue("password1", "error.password1.empty", "비밀번호 확인은 필수 입력 사항입니다");
       }

       // 다른 필드에 오류가 있는지 확인
       if (bindingResult.hasErrors()) {
           log.info("입력 오류 발생");
           log.info(bindingResult.getAllErrors()); // 에러 로그 출력
           return "account/register"; // 오류가 있을 시 다시 회원가입 페이지로 이동
       }

       try {
           //회원가입 처리
           accountService.register(accountDTO);
       } catch (IllegalStateException e) {
           log.info(e.getMessage());
           model.addAttribute("msg", e.getMessage());
           return "account/register";
       }

       // 회원가입 성공 시 로그인 페이지로 리다이렉트
       return "redirect:/account/login";
   }

   //로그인
   @GetMapping("/login")
   public String login() {
       return "account/login";
   }

    // 이메일 찾기 페이지
    @GetMapping("/find-email")
    public String findEmail() {
        return "account/find-email"; // 이메일 찾기 페이지를 보여줍니다.
    }

    // 이메일 찾기 처리
    @PostMapping("/find-email")
    public ResponseEntity<String> findEmailByNameAndPhone(@RequestParam String name, @RequestParam String phone) {
        try {
            String email = accountService.findEmailByNameAndPhone(name, phone);
            return ResponseEntity.ok(email);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    //회원 목록 보기(관리자 전용)
    @GetMapping("/list")
    public String getAllusers(Model model) {
       log.info("회원 목록 요청 처리 시작");
       List<AccountDTO> users = accountService.getAllUsers(); // 회원 목록 가져오기
       log.info("회원 목록 가져오기 완료, 총 회원 수 : {}", users.size()); //회원 수 로그
       model.addAttribute("users", users);
       return "account/list";
    }

    // 회원 탈퇴 처리
    @PostMapping("/delete")
    public String deleteAccount(@RequestParam("email") String email, Principal principal) {
        log.info("회원 탈퇴 요청 : 이메일 = {}", email); //탈퇴 요청 로그

        //혹시나 로그인이 안되어 있다면 그에 따라 체크
        if(principal == null){
            return "redirect:/account/login";
        }

        try {
            accountService.deleteAccount(email); //회원 탈퇴 처리 로직 호출
            log.info("회원 탈퇴 완료: 이메일 = {}", email); // 탈퇴 완료 로그
        } catch (Exception e) {
            log.error("회원 탈퇴 중 오류 발생: 이메일 = {}, 오류 = {}", email, e.getMessage()); // 에러 발생 시 로그 출력
            return "redirect:/error"; // 에러 발생 시 에러 페이지로 리디렉션
        }


        //추가사항 로그아웃 여부
        if(principal.getName().equals(email)){
            log.info("현재 로그인한 유저입니다.. 로그아웃합니다.");
            return "redirect:/account/logout";
        }else {
            log.info("현재 로그인한 유저가 아닙니다.");
            return "redirect:/account/list";
        }



    }
  }

