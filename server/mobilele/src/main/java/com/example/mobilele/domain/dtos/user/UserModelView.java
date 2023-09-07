package com.example.mobilele.domain.dtos.user;

import com.example.mobilele.domain.constants.ModelCategory;
import com.example.mobilele.domain.entity.Model;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@Builder

public class UserModelView {

    private UUID id;
    private String name;
    private ModelCategory category;
    private String imageUrl;
    private Integer created;
    private Integer generation;
    private UserBrandView brand;

    public UserModelView() {}
    public UserModelView(Model entity) {

        this.id = entity.getId();
        this.name = entity.getName();
        this.category = entity.getCategory();
        this.imageUrl = entity.getImageUrl();
        this.created = entity.getCreated();
        this.generation = entity.getGeneration();
        this.brand = new UserBrandView(entity.getBrand());

    }

}