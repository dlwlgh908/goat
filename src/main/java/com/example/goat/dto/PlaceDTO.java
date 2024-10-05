package com.example.goat.dto;

import com.example.goat.entity.Blog;
import com.example.goat.entity.City;
import com.example.goat.entity.Place;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDate;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PlaceDTO {

    private Long rank; // row_num을 위해?
    private Long total;
    private Long num;
    private String cname;

    @NotBlank
    private String place_name;

    private Long api_id;

    private String category_name;

    private Long blog_num;
    private BlogDTO blogDTO;
    private CityDTO cityDTO;



    private String address_name;


    public PlaceDTO setCityDTO(CityDTO cityDTO){
        this.cityDTO = cityDTO;
        return this;
    }

    public PlaceDTO setBlogDTO(BlogDTO blogDTO) {
        this.blogDTO = blogDTO;
        return this;
    }


}
