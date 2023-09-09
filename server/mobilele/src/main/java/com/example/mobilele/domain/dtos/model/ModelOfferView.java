package com.example.mobilele.domain.dtos.model;
import com.example.mobilele.domain.entity.Offer;
import lombok.Data;


@Data
public class ModelOfferView {

    private String id;
    private String description;
    private String price;
    private String created;
    private ModelUserView seller;

    public ModelOfferView(Offer entity){
        this.id = entity.getId().toString();
        this.description = entity.getDescription();
        this.price = entity.getPrice().toString();
        this.created = entity.getCreated().toString();
        this.seller = new ModelUserView(entity.getSeller());
    }
}
