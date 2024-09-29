package com.example.goat.controller;

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

@Controller
@Log4j2
@RequiredArgsConstructor
@RequestMapping("/account")
public class ModifyProfileController {

    private final AccountService accountService;

   @GetMapping("/modifyprofile")
    public String modifyProfile(Model model, @AuthenticationPrincipal User user) {
        AccountDTO accountDTO = accountService.getAccountDetails(user.getUsername()); //사용자 정보 가져오기
        model.addAttribute("accountDTO", accountDTO);
       return "account/modifyprofile"; //modifyprofile.html 파일의 경로
    }

    @PostMapping("/update") //정보 수정된 페이지?
    public String updateProfile(@ModelAttribute AccountDTO accountDTO,
                                @AuthenticationPrincipal User user,
                                RedirectAttributes redirectAttributes) {
       log.info("업데이트 요청 : " + accountDTO);
       accountDTO.setEmail(user.getUsername());

        // 비밀번호 변경 로직 추가
        if (accountDTO.getCurrentPassword() != null && accountDTO.getNewPassword() != null) {
            // 현재 비밀번호가 비어있지 않은지 확인
            if (accountDTO.getCurrentPassword().isEmpty()) {
                redirectAttributes.addFlashAttribute("error", "현재 비밀번호를 입력하세요.");
                return "redirect:/account/modifyprofile"; // 실패 시 수정 페이지로 돌아감
            }

            // 비밀번호 변경 로직을 updateAccount 메서드로 통합했으므로, 여기서는 호출하지 않음
            try {
                accountService.updateAccount(accountDTO);
            } catch (IllegalArgumentException e) {
                redirectAttributes.addFlashAttribute("error", e.getMessage());
                return "redirect:/account/modifyprofile"; // 실패 시 수정 페이지로 돌아감
            }
        } else {
            // 비밀번호 변경이 없는 경우 사용자 정보만 업데이트
            accountService.updateAccount(accountDTO);
        }

       accountService.updateAccount(accountDTO);

        //  성공 메시지 추가
        redirectAttributes.addFlashAttribute("message", "회원 정보가 수정되었습니다.");
       return "redirect:/mypage";
    }

    //

}
