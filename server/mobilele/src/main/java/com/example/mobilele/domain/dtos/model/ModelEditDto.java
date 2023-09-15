package com.example.mobilele.domain.dtos.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ModelEditDto {

    private String name;
    private String category;
    private String imageUrl;
    private Integer created;
    private Integer generation;
    private String brandName;

}
