package com.example.mobilele.domain.dtos.user;

import com.example.mobilele.domain.entity.Brand;
import lombok.Data;

import java.util.UUID;


@Data
public class UserBrandView {

    private UUID id;
    private String name;
    private String created;
    private String modified;

    public UserBrandView() {}
    public UserBrandView(Brand entity) {
        this.id = entity.getId();
        this.name = entity.getName();
        this.created = entity.getCreated().toString();
        this.modified = entity.getModified() == null ? "" : entity.getModified().toString();
    }
}
