package com.example.mobilele.domain.dtos.offer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
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
