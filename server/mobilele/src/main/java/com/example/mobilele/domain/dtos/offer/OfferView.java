package com.example.mobilele.domain.dtos.offer;

import com.example.mobilele.domain.entity.Offer;
import lombok.Data;


@Data
public class OfferView {

    private String id;
    private String description;
    private String engine;
    private String imageUrl;
    private Integer mileage;
    private String price;
    private String transmission;
    private Integer year;
    private String created;
    private String modified;
    private OfferModelView model;
    private OfferUserView seller;

    public OfferView(Offer offer) {
        this.id = offer.getId().toString();
        this.description = offer.getDescription();
        this.engine = offer.getEngine().name();
        this.imageUrl = offer.getImageUrl();
        this.mileage = offer.getMileage();
        this.price = offer.getPrice().toString();
        this.transmission = offer.getTransmission().name();
        this.year = offer.getYear();
        this.created = offer.getCreated().toString();
        this.modified = offer.getModified() == null ? "-" : offer.getModified().toString();
        this.model = new OfferModelView(offer.getModel());
        this.seller = new OfferUserView(offer.getSeller());

    }
}
