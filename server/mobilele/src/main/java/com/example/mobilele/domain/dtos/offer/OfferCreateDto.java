package com.example.mobilele.domain.dtos.offer;
import com.example.mobilele.domain.entity.Offer;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class OfferCreateDto {

    @NotBlank(message = "description cannot be blank")
    @Size(min= 10 , message = "description must be at least 10 chars long")
    private String description;

    @NotBlank(message = "you need to write an engine type")
    private String engine;

    private String imageUrl;

    @NotNull(message = "mileage should not be blank")
    @Min(value = 0 , message = "mileage cannot be negative")
    private Integer mileage;

    @NotNull(message = "price cannot be blank")
    @DecimalMin(value = "0" , message = "price should be positive")
    private BigDecimal price;

    @NotBlank(message = "you need to write an transmission type")
    private String transmission;

    @NotNull(message = "year must be filed")
    private Integer year;

    @NotBlank(message = "model name should not be blank")
    private String modelName;

    public Offer toOffer(){
        return Offer.builder()
                .description(description)
                .imageUrl(imageUrl == null ? "https://w7.pngwing.com/pngs/67/521/png-transparent-computer-icons-offers-text-logo-discount-thumbnail.png" : imageUrl)
                .price(price)
                .year(year)
                .created(LocalDate.now())
                .modified(null)
                .build();
    }

}
