package com.example.mobilele.domain.dtos.brand;

import lombok.Data;

import java.time.LocalDate;

@Data
public class BrandEditDto {

    private String name;
    private LocalDate created;

}
