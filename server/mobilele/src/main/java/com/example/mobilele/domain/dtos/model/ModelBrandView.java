package com.example.mobilele.domain.dtos.model;

import com.example.mobilele.domain.entity.Brand;
import lombok.Data;


@Data
public class ModelBrandView {

    private String id;
    private String name;
    private String created;

    public ModelBrandView (Brand entity){
        this.id = entity.getId().toString();
        this.name = entity.getName();
        this.created = entity.getCreated().toString();
    }
}
