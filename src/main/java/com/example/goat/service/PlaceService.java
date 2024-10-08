package com.example.goat.service;

import com.example.goat.dto.*;
import com.example.goat.entity.*;
import com.example.goat.repository.*;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Log4j2
@Transactional
public class PlaceService {

    private final PlaceRepository placeRepository;
    private final BlogRepository blogRepository;
    private final CityRepository cityRepository;
    private  ModelMapper modelMapper = new ModelMapper();

    public List<PlaceDTO> mainRank(PlaceDTO placeDTO){

        Place place = modelMapper.map(placeDTO, Place.class);
         return placeRepository.mainPlaceRank(placeDTO);
    };
    public List<PlaceDTO> mainRankRestaurant(PlaceDTO placeDTO){

        Place place = modelMapper.map(placeDTO, Place.class);
        return placeRepository.mainPlaceRankRestaurant(placeDTO);
    };

    public List<PlaceDTO> list(PlaceDTO placeDTO){

        Place place = modelMapper.map(placeDTO, Place.class);
        return placeRepository.listPlace(placeDTO);

    }

    public Long saveplace(PlaceDTO dto){


        Place place = modelMapper.map(dto, Place.class);

        City city = cityRepository.findByCname(dto.getAddress_name());
        place.setCity(city);

        Place result = placeRepository.save(place);

        log.info("저장했을때 장소 : " + result);
        return result.getNum();
    }


    public Place readOne(Long nnum, Long blognum){

        Blog blog = blogRepository.findById(blognum).get();

        Place result = placeRepository.findById(nnum).orElseThrow(EntityNotFoundException::new);
        result.setBlog(blog);
        return result;


    }

//    public List<PlaceDTO> blogDetale(Long num) {
//        Place place = modelMapper.map(PlaceDTO, Place.class);
//
//        List<Place> result = placeRepository.findByBlogNum(num);
//        return result;
//
//    }



}
