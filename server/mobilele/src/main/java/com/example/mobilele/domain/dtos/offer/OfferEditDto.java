package com.example.mobilele.domain.dtos.offer;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class OfferEditDto {

    @Schema(minLength = 10,
            example = "Some example with more then 10 characters here")
    private String description;
    @Schema(allowableValues = {"GASOLINE", "DIESEL", "ELECTRIC", "HYBRID"},
            example = "DIESEL")
    private String engine;

    private String imageUrl;
    @Schema(minimum = "0",
            example = "1222")
    private Integer mileage;
    @Schema(minimum = "0",
            example = "120005")
    private String price;
    @Schema(allowableValues = {"MANUAL", "AUTOMATIC"},
            example = "AUTOMATIC")
    private String transmission;
    @Schema(description = "Must not be a future year",
            minimum = "0",
            example = "1250")
    private Integer year;
    @Schema(description = "The provided model name must match an existing model." ,
            example = "BMW M4 Convertible")
    private String modelName;

}
