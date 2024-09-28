package com.example.goat.repository;

import com.example.goat.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface AccountRepository extends JpaRepository<Account, Long> {

    Account findByEmail(String email);

    @Query("select a from Account a where a.email=:email")
    Account ddd(String email);






    //이름과 전화번호로 가입한 이메일 찾기
    //@Query("select a.email from Account a where a.name=:name and a.phone=:phone")
    //String findEmailByName(String name, String phone);

    //가입한 이메일로 비밀번호 찾기
    //@Query("select a from Account a join PasswordResetToken p on a.email = p.account.email where p.token = : token")
    //Account findByPasswordResetToken(String token);

    //비밀번호 변경
    //@Modifying
   //@Query("update Account a set a.password = :newPassword where a.email = accountEmail")
   //void updatePassword(Long accountEmail, String newPassword);
}
