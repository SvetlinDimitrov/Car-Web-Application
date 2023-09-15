package com.example.mobilele.domain.dtos.brand;

import com.example.mobilele.domain.entity.Brand;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class BrandView {
    private String id;
    private String name;
    private String created;
    private String modified;
    private List<BrandModelView> modelList;

    public BrandView(Brand entity) {
        this.id = entity.getId().toString();
        this.name = entity.getName();
        this.created = entity.getCreated().toString();
        this.modified = entity.getModified() == null ? "" : entity.getModified().toString();
        this.modelList = entity.getModelList().stream().map(BrandModelView::new).toList();
    }

}
