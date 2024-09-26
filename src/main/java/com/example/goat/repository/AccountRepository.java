package com.example.goat.repository;

import com.example.goat.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AccountRepository extends JpaRepository<Account, Long> {
    boolean existsByEmail(String email); //이메일 중복 체크를 위한 메서드 추가



    @Query("select a from Account a where a.email=:email and a.password1 =:password")

    Account findByEmailPassword(String email, String password);

    //이름과 전화번호로 가입한 이메일 찾기
    @Query("select a.email from Account a where a.name=:name and a.Phone=:phone")
    String findEmailByName(String name, String phone);

}
