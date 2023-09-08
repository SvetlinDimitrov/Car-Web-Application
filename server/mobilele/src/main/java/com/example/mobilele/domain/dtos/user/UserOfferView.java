package com.example.mobilele.domain.dtos.user;

import com.example.mobilele.domain.constants.EngineType;
import com.example.mobilele.domain.constants.TransmissionType;
import com.example.mobilele.domain.entity.Offer;
import lombok.Data;


import java.math.BigDecimal;
import java.util.UUID;

@Data
public class UserOfferView {

    private UUID id;
    private String description;
    private EngineType engine;
    private String imageUrl;
    private Integer mileage;
    private BigDecimal price;
    private TransmissionType transmission;
    private Integer year;
    private String created;
    private String modified;
    private UserModelView model;

    public UserOfferView() {}
    public UserOfferView(Offer entity){
        this.id = entity.getId();
        this.description = entity.getDescription();
        this.engine = entity.getEngine();
        this.imageUrl = entity.getImageUrl();
        this.mileage = entity.getMileage();
        this.price = entity.getPrice();
        this.transmission = entity.getTransmission();
        this.year = entity.getYear();
        this.created = entity.getCreated().toString();
        this.modified = entity.getModified().toString();
        this.model = new UserModelView(entity.getModel());
    }
}
