package com.example.goat.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;

@Setter
@Getter
@ToString
public class RestrauntDTO {

    private Long restraunt_num;
    private Long restraunt_key;
    private Long recommendCount;


}
