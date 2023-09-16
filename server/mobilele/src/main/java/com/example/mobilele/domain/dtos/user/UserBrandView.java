package com.example.mobilele.domain.dtos.user;

import com.example.mobilele.domain.entity.Brand;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserBrandView {

    private Long id;
    private String name;
    private String created;
    private String modified;

    public UserBrandView(Brand entity) {
        this.id = entity.getId();
        this.name = entity.getName();
        this.created = entity.getCreated().toString();
        this.modified = entity.getModified() == null ? "" : entity.getModified().toString();
    }
}
