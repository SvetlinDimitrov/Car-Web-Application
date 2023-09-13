package com.example.mobilele.domain.dtos.offer;
import com.example.mobilele.domain.entity.Offer;
import com.example.mobilele.validators.yearChecker.MaxCurrentYear;
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

    @NotNull(message = "mileage should not be blank")
    @Min(value = 0 , message = "mileage cannot be negative")
    private Integer mileage;

    @NotNull(message = "price cannot be blank")
    @DecimalMin(value = "0" , message = "price should be positive")
    private BigDecimal price;

    @NotBlank(message = "you need to write an transmission type")
    private String transmission;

    @NotNull(message = "year must not be blank")
    @Min(value = 0 , message = "year should be a positive number")
    @MaxCurrentYear
    private Integer year;

    @NotBlank(message = "model name should not be blank")
    private String modelName;

    public Offer toOffer(){
        return Offer.builder()
                .description(description)
                .price(price)
                .mileage(mileage)
                .year(year)
                .created(LocalDate.now())
                .modified(null)
                .build();
    }

}
