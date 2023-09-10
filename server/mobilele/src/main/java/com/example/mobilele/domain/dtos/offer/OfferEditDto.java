package com.example.mobilele.domain.dtos.offer;
import lombok.Data;


@Data
public class OfferEditDto {

    private String description;
    private String engine;
    private String imageUrl;
    private Integer mileage;
    private String price;
    private String transmission;
    private Integer year;
    private String modelName;

}
