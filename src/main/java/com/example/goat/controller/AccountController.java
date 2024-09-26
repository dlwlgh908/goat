package com.example.goat.controller;

import com.example.goat.dto.AccountDTO;
import com.example.goat.service.AccountService;
import com.example.goat.service.AccountServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Map;

@Controller
@Log4j2
@RequiredArgsConstructor
@RequestMapping("/account")
public class AccountController {
    private final AccountService accountService;

  //회원가입 페이지 이동
   @GetMapping("/register")
    public void regist(AccountDTO accountDTO) {

       log.info("등록get 진입");
   }

   //회원가입 처리
   @PostMapping("/register")
    public String registP(@Valid AccountDTO accountDTO, BindingResult bindingResult,
                          Errors errors, Model model, RedirectAttributes redirectAttributes) {
       log.info("회원가입 요청 : " + accountDTO);

       if (bindingResult.hasErrors()) {
           log.error("회원가입 유효성 검사 실패 : {} " + bindingResult.getAllErrors());
           model.addAttribute("accountDTO", accountDTO); //입력 값 유지
           return "account/register"; //유효성 검사 실패 시 다시 회원가입 페이지로 이동
       }

       //비밀번호 일치 여부 검사
       if(!accountDTO.getPassword1().equals(accountDTO.getPassword2())) {
           bindingResult.rejectValue("password2", "error.password2", "비밀번호와 비밀번호 확인이 일치하지 않습니다");
           model.addAttribute("accountDTO", accountDTO); //입력 값 유지
           return "account/register";
       }

       accountService.register(accountDTO, bindingResult); // 서비스 계층에서 회원가입 처리
       redirectAttributes.addFlashAttribute("message", "회원가입이 완료되었습니다.");

       return "redirect:/account/login"; // 회원가입 성공 후 회원 목록으로 리다이렉트
   }

   @GetMapping("/login")
    public void rLogin(Model model) {
       log.info("로그인 페이지 요청");
   }

   @PostMapping("/login")
    public String rLogin(@RequestParam String email, @RequestParam String password,
                         RedirectAttributes redirectAttributes, Model model) {
       log.info("로그인 요청 : 이메일 = {}, 비밀번호 = {} ", email, password);

       boolean isAuthenticated = accountService.authenticate(email, password);

       if (isAuthenticated) {

           return "redirect:/account/register"; //로그인 성공 후 대시보드 또는 메인 페이지로 리다이렉트(아직 설정 안함)
       } else {
           model.addAttribute("errorss", "로그인에 실패하였습니다");
           return "account/login";
       }

   }



}
