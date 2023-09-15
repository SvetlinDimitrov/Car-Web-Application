package com.example.mobilele.domain.dtos.model;


import com.example.mobilele.domain.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ModelUserView {

    private String id;
    private String username;

    public ModelUserView (User entity){
        this.id = entity.getId().toString();
        this.username = entity.getUsername();
    }

}
