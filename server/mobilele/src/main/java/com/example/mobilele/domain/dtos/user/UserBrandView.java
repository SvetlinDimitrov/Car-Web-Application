package com.example.mobilele.domain.dtos.user;

import com.example.mobilele.domain.entity.Brand;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;


@Getter
@Setter
@AllArgsConstructor
@Builder

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
        this.modified = entity.getModified().toString();
    }
}
