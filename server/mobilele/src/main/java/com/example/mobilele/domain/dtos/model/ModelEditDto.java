package com.example.mobilele.domain.dtos.model;

import lombok.Data;

@Data
public class ModelEditDto {

    private String name;
    private String category;
    private String imageUrl;
    private Integer created;
    private Integer generation;
    private String brandName;

}
