package com.example.goat.controller;

import com.example.goat.dto.PwdChangeDTO;
import lombok.extern.log4j.Log4j2;
import org.springframework.ui.Model;
import com.example.goat.dto.AccountDTO;
import com.example.goat.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;

@Controller
@Log4j2
@RequiredArgsConstructor
@RequestMapping("/account")
public class ModifyProfileController {

    private final AccountService accountService;

   @GetMapping("/modifyprofile") //개인정보 수정 페이지
    public String modifyProfile(Model model, @AuthenticationPrincipal User user) {
        AccountDTO accountDTO = accountService.getAccountDetails(user.getUsername()); //사용자 정보 가져오기
        model.addAttribute("accountDTO", accountDTO);
       return "account/modifyprofile"; //개인정보 수정 페이지
    }

    // 비밀번호 변경 페이지
    @GetMapping("/edit")
    public String editPassword(Model model, @AuthenticationPrincipal User user) {
        AccountDTO accountDTO = accountService.getAccountDetails(user.getUsername());
        model.addAttribute("accountDTO", accountDTO);
        return "account/edit"; // 비밀번호 변경 페이지 (edit.html)
    }

    @PostMapping("/update")
    public String updateProfile(@ModelAttribute AccountDTO accountDTO,
                                @AuthenticationPrincipal User user, Principal principal,
                                RedirectAttributes redirectAttributes) {
       log.info("회원 정보 수정 요청 : " + accountDTO);
       accountDTO.setEmail(user.getUsername());

       // 사용자 정보 업데이트
        accountService.updateAccount(accountDTO);
        redirectAttributes.addFlashAttribute("message", "회원 정보가 수정되었습니다.");

        return "redirect:/mypage";
    }

    @PostMapping ("/change-password")
    public String handlePasswordChange(@ModelAttribute PwdChangeDTO pwdChangeDTO, @AuthenticationPrincipal User user,
                                       Principal principal, RedirectAttributes redirectAttributes) {

       log.info("비밀번호 변경 요청 : " + pwdChangeDTO);
       // 현재 로그인한 사용자의 이메일을 설정
        String email = user.getUsername();

                // 비밀번호 변경
                try {
                    accountService.changePassword(email, pwdChangeDTO.getCurrentPassword(),
                            pwdChangeDTO.getNewPassword(), pwdChangeDTO.getConfirmPassword());

                    // 성공 메시지 설정
                    redirectAttributes.addFlashAttribute("message", "비밀번호가 성공적으로 변경되었습니다.");
                } catch (IllegalArgumentException e) {
                    //실패 시 에러 메시지 설정
                redirectAttributes.addFlashAttribute("error", e.getMessage());
                return "redirect:/account/edit"; // 비밀번호 변경 실패 시 수정 페이지로 돌아감
            }

            return "redirect:/mypage"; //비밀번호 변경 후 마이페이지로 리다이렉트
        }

}
