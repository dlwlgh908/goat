package com.example.goat.controller;

import com.example.goat.dto.AccountDTO;
import com.example.goat.service.AccountService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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





    //회원목록 관리자만 볼거임

  }

