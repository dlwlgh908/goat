package com.example.goat.service;

import com.example.goat.dto.AccountDTO;

import java.util.List;

public interface AccountService {

    // 회원 등록
    public void register(AccountDTO accountDTO);

    // 모든 회원 조회
    public List<AccountDTO> selectAll();

    // 회원 정보 업데이트
    public void update(AccountDTO accountDTO);

    // 회원 삭제
    public void delete(Long accountNum);


}
