package com.example.goat.repository;

import com.example.goat.entity.Account;
import com.example.goat.entity.City;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CityRepository extends JpaRepository<City, Long> {
}
