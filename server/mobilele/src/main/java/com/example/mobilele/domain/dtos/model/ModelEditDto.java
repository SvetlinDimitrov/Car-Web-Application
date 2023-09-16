package com.example.mobilele.domain.dtos.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ModelEditDto {

    @Schema(description = "must be unique",
            example = "X250")
    private String name;
    @Schema(allowableValues = {"Car", "Bus", "Truck", "Motorcycle"},
            example = "Bus")
    private String category;
    @Schema(description = "url image of the model")
    private String imageUrl;
    @Schema(example = "2024", minimum = "1")
    private Integer created;
    @Schema(example = "2", minimum = "0")
    private Integer generation;

    @Schema(description = "must be a valid brandName", example = "Audi")
    private String brandName;

}
