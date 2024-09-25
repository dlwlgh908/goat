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

@Controller
@Log4j2
@RequiredArgsConstructor
@RequestMapping("/account")
public class AccountController {
    private final AccountService accountService;

  //회원가입 페이지 이동
   @GetMapping("/regist")
    public void regist(AccountDTO accountDTO) {
       log.info("등록get 진입");
   }

   //회원가입 처리
   @PostMapping("/regist")
    public String registPost(@Valid AccountDTO accountDTO, BindingResult bindingResult, Model model) {
       log.info("회원가입 요청 : " + accountDTO);

       if (bindingResult.hasErrors()) {
           log.error("회원가입 유효성 검사 실패 : " + bindingResult.getAllErrors());
           return "account/regist"; //유효성 검사 실패 시 다시 회원가입 페이지로 이동

           accountService.update(accountDTO); // 회원 정보 업데이트
       }
   }

}
