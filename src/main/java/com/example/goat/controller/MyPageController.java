package com.example.goat.controller;

import com.example.goat.dto.AccountDTO;
import com.example.goat.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;


@Controller
@RequiredArgsConstructor
public class MyPageController {

    private final AccountService accountService;

    //마이페이지로 이동
    @GetMapping("/mypage")
    public String myPage(@AuthenticationPrincipal UserDetails user, Model model) {
        //로그인 된 사용자 정보가 있는지 확인
        if(user == null) {
            // 사용자가 로그인하지 않은 경우, 로그인 페이지로 리다이렉트
            return "redirect:/login"; //로그인 페이지 URL로 변경
        }

        // 로그인 된 사용자 정보 가져오기
        String email = user.getUsername();
        AccountDTO accountDTO = accountService.findbyEmail(email);

        //모델에 사용자 정보 추가
        model.addAttribute("accountDTO", accountDTO);
        return "account/mypage"; // myPage.html로 이동
    }
}
