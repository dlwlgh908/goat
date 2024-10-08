package com.example.goat.repository;

import com.example.goat.dto.PlaceDTO;
import com.example.goat.entity.Account;
import com.example.goat.entity.Blog;
import com.example.goat.entity.City;
import com.example.goat.entity.Place;
import com.example.goat.repository.search.BlogSearch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;
import java.util.Optional;

public interface PlaceRepository extends JpaRepository<Place, Long> {


    public List<PlaceDTO> findByBlog_num(Long num);

    @Query("select new com.example.goat.dto.PlaceDTO(p.category_name as category_name, p.API_id as api_id, p.place_name as place_name, c.cname as cname, count(*) as total) from Place p join City c on p.city.cno = c.cno where substring(p.category_name,1,3) != '음식점'  group by p.place_name order by count(*) desc limit 5")
            public List<PlaceDTO> mainPlaceRank(PlaceDTO placeDTO);

    @Query("select new com.example.goat.dto.PlaceDTO(p.category_name as category_name, p.API_id as api_id, p.place_name as place_name, c.cname as cname, count(*) as total) from Place p join City c on p.city.cno = c.cno where substring(p.category_name,1,3) = '음식점'  group by p.place_name order by count(*) desc limit 5")
    public List<PlaceDTO> mainPlaceRankRestaurant(PlaceDTO placeDTO);

    @Query("select new com.example.goat.dto.PlaceDTO(p.place_name as place_name,p.category_name as category_name, c.cname as cname, p.API_id as api_id, count(*) as total) from Place p join City c on p.city.cno = c.cno group by p.place_name order by count(*) desc limit 10")
    public List<PlaceDTO> listPlace(PlaceDTO placeDTO);


    public List<Place> findByBlogNum (Long num);



}
