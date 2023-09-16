package com.example.mobilele.domain.dtos.offer;

import com.example.mobilele.domain.entity.Offer;
import com.example.mobilele.validators.yearChecker.MaxCurrentYear;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OfferCreateDto {

    @Schema(minLength = 10,
            example = "The perfect car for every need")
    @NotBlank(message = "description cannot be blank")
    @Size(min = 10, message = "description must be at least 10 chars long")
    private String description;

    @Schema(allowableValues = {"GASOLINE", "DIESEL", "ELECTRIC", "HYBRID"},
            example = "GASOLINE")
    @NotBlank(message = "you need to write an engine type")
    private String engine;

    @Schema(minimum = "0",
            example = "1200")
    @NotNull(message = "mileage should not be blank")
    @Min(value = 0, message = "mileage cannot be negative")
    private Integer mileage;

    @Schema(minimum = "0",
            example = "170000.25")
    @NotNull(message = "price cannot be blank")
    @DecimalMin(value = "0", message = "price should be positive")
    private BigDecimal price;

    @Schema(allowableValues = {"MANUAL", "AUTOMATIC"},
            example = "MANUAL")
    @NotBlank(message = "you need to write an transmission type")
    private String transmission;

    @Schema(description = "Must not be a future year",
            minimum = "0",
            example = "2000")
    @NotNull(message = "year must not be blank")
    @Min(value = 0, message = "year should be a positive number")
    @MaxCurrentYear
    private Integer year;

    @Schema(description = "The provided model name must match an existing model." ,
    example = "BMW X6")
    @NotBlank(message = "model name should not be blank")
    private String modelName;

    public Offer toOffer() {
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
