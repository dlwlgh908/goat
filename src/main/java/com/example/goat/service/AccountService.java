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
import java.util.List;

import java.lang.reflect.Member;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
@Log4j2
public class AccountService implements UserDetailsService {

    private  final AccountRepository accountRepository;
    private ModelMapper mapper = new ModelMapper();
    private final PasswordEncoder passwordEncoder;

    //이메일로 사용자 찾기
    public AccountDTO findbyEmail(String email) {
        Account account = accountRepository.findByEmail(email);
        if(account == null) {
            log.warn("이메일로 찾은 계정이 없습니다:" + email);
            return null;
        }
        return mapper.map(account, AccountDTO.class);
    }

    //이름과 전화번호로 이메일 찾기
    public String findEmailByNameAndPhone(String name, String phone) {
        String email = accountRepository.findEmailByName(name, phone);
        if (email == null) {
            log.warn("이름과 전화번호로 찾은 이메일이 없습니다: 이름 = {}, 전화번호 = {}", name, phone);
            throw new IllegalArgumentException("이름과 전화번호에 해당하는 이메일을 찾을 수 없습니다.");
        }
        return email;
    }

    //회원 목록 가져오기(관리자 권한)
    public List<AccountDTO> getAllUsers() {
        List<Account> accounts = accountRepository.findAll();
        return accounts.stream().map(account -> mapper.map(account, AccountDTO.class)).collect(Collectors.toList());
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

        log.info("로그인 요청 :" + email);
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

    //사용자 정보 업데이트

    public void updateAccount(AccountDTO accountDTO) {
        log.info("사용자 정보를 업데이트 중 : " + accountDTO.getEmail());
        Account account = accountRepository.findByEmail(accountDTO.getEmail());

        //사용자 정보 업데이트 (비밀번호 제외)
        account.setName(accountDTO.getName());
        account.setPhone(accountDTO.getPhone());

        log.info("DB에 사용자 정보 저장 중: " + account.getEmail());
        accountRepository.save(account);
    }

        //비밀번호 변경 메서드
        public void changePassword(String email, String currentPassword, String newPassword, String newPasswordConfirm) {
        log.info("비밀버호 변경 요청 : 이메일 = {}" ,email);

        Account account = accountRepository.findByEmail(email);
            if (account == null) {
                log.warn("사용자를 찾을 수 없습니다: {}", email);
                throw new UsernameNotFoundException("사용자를 찾을 수 없습니다: " + email);
            }

            // 현재 비밀번호 확인
            if (!passwordEncoder.matches(currentPassword, account.getPassword())) {
                log.warn("현재 비밀번호가 일치하지 않습니다: {}", email);
                throw new IllegalArgumentException("현재 비밀번호가 일치하지 않습니다.");
            }
            log.info("현재 비밀번호가 일치합니다: {}", email);

            // 비밀번호 확인 로직 추가
            if (!newPassword.equals(newPasswordConfirm)) {
                throw new IllegalArgumentException("새 비밀번호가 일치하지 않습니다.");
            }
            log.info("새 비밀번호가 일치합니다: {}", email);

            // 비밀번호 변경
            account.setPassword(passwordEncoder.encode(newPassword));
            log.info("비밀번호가 성공적으로 변경되었습니다: " + email );

            accountRepository.save(account);
            log.info("DB에 비밀번호 변경 사항 저장 완료: {}", email);
        }

        //회원 탈퇴
        public boolean deleteAccount(String email) {
            log.info("회원 탈퇴 요청: 이메일 = {}", email); // 탈퇴 요청 로그

            Account account = accountRepository.findByEmail(email);

            if (account == null) {
                log.error("회원 탈퇴 실패: 존재하지 않는 사용자 이메일 = {}", email); // 사용자 존재하지 않음 로그
                throw new IllegalArgumentException("사용자가 존재하지 않습니다 : " + email);
            }

            // 실제 삭제 처리
            accountRepository.delete(account);
            log.info("회원 탈퇴 완료: 이메일 = {}", email); // 탈퇴 완료 로그
            return true; //탈퇴 성공 시 true 반환
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