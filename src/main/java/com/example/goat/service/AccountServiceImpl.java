package com.example.goat.service;

import com.example.goat.dto.AccountDTO;
import com.example.goat.entity.Account;
import com.example.goat.repository.AccountRepository;
import jakarta.persistence.EntityExistsException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Log4j2
@Transactional

public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;

    private ModelMapper mapper = new ModelMapper();

    //회원등록
    @Override
    public void register(AccountDTO accountDTO) {
        log.info("서비스로 들어온 dto : " + accountDTO);
        Account account = mapper.map(accountDTO, Account.class); //DTO를 엔티티로 변환
        log.info("서비스에서 변환된 dto > entity : " + account);
        accountRepository.save(account); //엔티티 저장
    }

    //모든 회원 조회
    @Override
    public List<AccountDTO> selectAll() {
        List<Account> accountList = accountRepository.findAll(); //모든 계정 조회

        List<AccountDTO> accountDTOList =
                accountList.stream().map(abc -> mapper.map(abc, AccountDTO.class)).collect(Collectors.toList());

        return accountDTOList;
    }

    //회원정보 업데이트
    @Override
    public void update(AccountDTO accountDTO) {
        Account account = accountRepository
                .findById(accountDTO.getAccount_num())
                .orElseThrow(EntityExistsException::new);

        //필요한 필드 업데이트
        account.setEmail(account.getEmail());
        account.setPassword1(account.getPassword1());
        account.setPassword2(account.getPassword2());
        account.setName(account.getName());
        account.setPhone(account.getPhone());
    }
    //회원 삭제
    @Override
    public void delete(Long accountNum) {
        accountRepository.deleteById(accountNum);
    }


}
