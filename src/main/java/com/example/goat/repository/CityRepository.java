package com.example.goat.repository;

import com.example.goat.entity.City;
import com.example.goat.entity.Place;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CityRepository extends JpaRepository<City, Long> {

    City findByCname(String cname);

    City findByCno(Long cnum);



}
