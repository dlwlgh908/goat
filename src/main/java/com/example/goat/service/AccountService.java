package com.example.goat.service;

import com.example.goat.dto.AccountDTO;
import jakarta.validation.Valid;
import org.springframework.validation.BindingResult;

import java.util.List;

public interface AccountService {

    //회원등록
    void register(@Valid AccountDTO accountDTO, BindingResult bindingResult);

    // 모든 회원 조회
    List<AccountDTO> selectAll();

    // 회원 정보 업데이트
    void update(AccountDTO accountDTO);

    // 회원 삭제
    void delete(Long accountNum);


    boolean authenticate(String email, String password);

    AccountDTO findEmailByName(String name, String phone);
}
