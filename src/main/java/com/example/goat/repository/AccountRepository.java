package com.example.goat.repository;

import com.example.goat.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AccountRepository extends JpaRepository<Account, Long> {

    Account findByEmail(String email);

    @Query("select a from Account a where a.email=:email")
    Account ddd(String email);


    //이름과 전화번호로 가입한 이메일 찾기
    @Query("select a.email from Account a where a.name=:name and a.Phone=:phone")
    String findEmailByName(@Param("name")String name, @Param("phone")String phone);

}
