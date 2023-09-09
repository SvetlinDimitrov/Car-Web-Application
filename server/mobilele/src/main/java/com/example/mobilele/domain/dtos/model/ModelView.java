package com.example.mobilele.domain.dtos.model;
import com.example.mobilele.domain.entity.Model;
import lombok.Data;

import java.util.List;


@Data
public class ModelView {
    private String id;
    private String name;
    private String category;
    private String imageUrl;
    private String created;
    private String generation;
    private ModelBrandView brand;
    private List<ModelOfferView> offerList;
    private List<ModelUserView> userList;

    public ModelView(Model entity){
        this.id = entity.getId().toString();
        this.name = entity.getName();
        this.category = entity.getCategory().name();
        this.imageUrl = entity.getImageUrl();
        this.created = entity.getCreated().toString();
        this.generation = entity.getGeneration().toString();
        this.brand = new ModelBrandView(entity.getBrand());
        this.offerList = entity.getOfferList().stream().map(ModelOfferView::new).toList();
        this.userList = entity.getUserList().stream().map(ModelUserView::new).toList();
    }
}
