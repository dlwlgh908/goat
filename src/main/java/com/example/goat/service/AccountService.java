package com.example.goat.service;

import com.example.goat.constant.Role;
import com.example.goat.dto.AccountDTO;
import com.example.goat.entity.Account;
import com.example.goat.repository.AccountRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.lang.reflect.Member;

@Service
@Transactional
@RequiredArgsConstructor
@Log4j2
public class AccountService implements UserDetailsService {

    private  final AccountRepository accountRepository;
    private ModelMapper mapper = new ModelMapper();

    private final PasswordEncoder passwordEncoder;

    public AccountDTO findbyEmail(String email) {
        Account account = accountRepository.findByEmail(email);
        if(account == null) {
            log.warn("이메일로 찾은 계정이 없습니다:" + email);
            return null;
        }

        //dto변환

        return mapper.map(account, AccountDTO.class);
    }

    //회원가입
    public void  register(AccountDTO accountDTO) {
        //회원가입 시 dto로 넘겨준 값을 받는다.
        log.info(accountDTO);
        //회원이 있는지 확인
        Account account = accountRepository.findByEmail(accountDTO.getEmail());

        if (account !=null){
            log.info("이미 가입된 회원 : " + accountDTO.getEmail());
            throw new IllegalStateException("이메일이 이미 사용 중입니다");
        }

        log.info("가입 가능한 회원입니다");

        // 비밀번호 확인 로직 추가
        if (!accountDTO.isPasswordMatching()) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }


        //새로운 회원 정보 저장
        Account entity = mapper.map(accountDTO, Account.class);
        entity.setPassword(passwordEncoder.encode(accountDTO.getPassword())); //비밀번호를 password 필드에 저장

        //사용자 선택에 따른 권한 설정
        if (accountDTO.getRole() == Role.ADMIN) {
            entity.setRole(Role.ADMIN);
        } else {
            entity.setRole(Role.USER);
        }
        accountRepository.save(entity); //저장 가입

    }

    //로그인
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        //repository에서 이메일로 값을 검색해서 있는 없는지 확인합니다.
        //왜 이메일은 유니크이니까 유일해야하니까 그리고 이미 가입된 회원인지를 확인해야 하니까

        log.info("회원가입서비스로 들어온 :" + email);
        Account account = accountRepository.findByEmail(email);

        if (account == null) {
            throw new UsernameNotFoundException("사용자를 찾을 수 없습니다" + email);
        }

        log.info(" 현재 로그인 한 사람 권한 " + account.getRole());

        String role = account.getRole() != null ? account.getRole().name() : Role.USER.name();

        return User.builder()
                .username(account.getEmail())
                .password(account.getPassword())
                .roles(role)
                .build();
    }

    //사용자 정보 가져오기
    public AccountDTO getAccountDetails(String username) {
        log.info("사용자 정보를 가져오는 중: " + username);

        Account account = accountRepository.findByEmail(username);
        if (account == null) {
            log.warn("사용자를 찾을 수 없습니다: " + username);
            throw new UsernameNotFoundException(("사용자를 찾을 수 없습니다 : " + username));
        }

        log.info("사용자 정보 찾음:" + account.getEmail());
        return mapToDTO(account);
    }

    //사용자 정보 업데이트 및 비밀번호 변경

    public void updateAccount(AccountDTO accountDTO) {
        log.info("사용자 정보를 업데이트 중 : " + accountDTO.getEmail());

        Account account = accountRepository.findByEmail(accountDTO.getEmail());
        if (account == null) {
            log.warn("사용자를 찾을 수 없습니다 : " + accountDTO.getEmail());
            throw new UsernameNotFoundException("사용자를 찾을 수 없습니다 : " + accountDTO.getEmail());
        }

        //사용자 정보 업데이트
        account.setName(accountDTO.getName());
        account.setPhone(accountDTO.getPhone());

        // 비밀번호 변경 로직
        if (accountDTO.getCurrentPassword() != null && accountDTO.getNewPassword() != null) {
            // 현재 비밀번호 확인
            if (!passwordEncoder.matches(accountDTO.getCurrentPassword(), account.getPassword())) {
                throw new IllegalArgumentException("현재 비밀번호가 일치하지 않습니다.");
            }
            // 새 비밀번호 설정
            account.setPassword(passwordEncoder.encode(accountDTO.getNewPassword()));
        }

        log.info("DB에 사용자 정보 저장 중: " + account.getEmail());
        accountRepository.save(account);


    }
    private AccountDTO mapToDTO(Account account) {
        AccountDTO dto = new AccountDTO();
        dto.setAccountNum(account.getAno());
        dto.setEmail(account.getEmail());
        dto.setName(account.getName());
        dto.setPhone(account.getPhone());
        dto.setRole(account.getRole());
        return dto;
    }


}