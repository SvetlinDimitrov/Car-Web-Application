package com.example.mobilele.domain.dtos.model;

import com.example.mobilele.utils.constants.ModelCategory;
import lombok.Data;

@Data
public class ModelEditDto {

    private String name;
    private ModelCategory category;
    private String imageUrl;
    private Integer created;
    private Integer generation;
    private String brandName;

}
