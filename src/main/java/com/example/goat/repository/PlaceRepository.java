package com.example.goat.repository;

import com.example.goat.entity.Blog;
import com.example.goat.entity.Place;
import com.example.goat.repository.search.BlogSearch;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlaceRepository extends JpaRepository<Place, Long> {




}
