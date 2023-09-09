package com.example.mobilele.domain.dtos.model;


import com.example.mobilele.domain.entity.User;
import lombok.Data;

@Data
public class ModelUserView {

    private String id;
    private String username;

    public ModelUserView (User entity){
        this.id = entity.getId().toString();
        this.username = entity.getUsername();
    }

}
