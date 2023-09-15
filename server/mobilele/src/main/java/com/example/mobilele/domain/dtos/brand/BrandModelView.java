package com.example.mobilele.domain.dtos.brand;

import com.example.mobilele.domain.entity.Model;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BrandModelView {
    private String id;
    private String name;
    private String category;
    private String imageUrl;

    public BrandModelView(Model entity){
        this.id = entity.getId().toString();
        this.name = entity.getName();
        this.category = entity.getCategory().name();
        this.imageUrl = entity.getImageUrl() == null ? "https://media.istockphoto.com/id/1144092062/vector/car-flat-icon.jpg?s=612x612&w=0&k=20&c=wXcAzfqkR8dMT7oQz89kkMWe44YzrNUODBBAeJd__Fo=" : entity.getImageUrl();
    }

}
