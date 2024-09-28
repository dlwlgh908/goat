package com.example.goat.controller;

import com.example.goat.dto.AccountDTO;
import com.example.goat.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/account")
public class ModifyProfileController {

    private final AccountService accountService;

   // @GetMapping("/modifyprofile")
    //public String modifyProfile(Model model) {
        //AccountDTO accountDTO = accountService.getAccountDetails(); //사용자 정보 가져오기
       // model.addAttribute("accountDTO", accountDTO);
       //return "account/modifyprofile"; //modifyprofile.html 파일의 경로
    //}

}
