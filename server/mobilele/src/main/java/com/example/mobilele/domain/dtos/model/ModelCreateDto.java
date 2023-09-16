package com.example.mobilele.domain.dtos.model;

import com.example.mobilele.domain.entity.Model;
import com.example.mobilele.validators.modelNameChecker.ValidModelName;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ModelCreateDto {

    @Schema(description = "The names are unique in the data",
            example = "EQB SUV" ,
            minLength = 1)
    @NotBlank(message = "name cannot be blank")
    @ValidModelName
    private String name;

    @Schema(allowableValues = {"Car", "Bus", "Truck", "Motorcycle"},
            example = "Car")
    @NotBlank(message = "category cannot be null. It can be : Car, Buss, Truck, Motorcycle")
    private String category;

    @Schema(description = "url of the model image")
    private String imageUrl;

    @Schema(example = "2024" , minimum = "1")
    @NotNull(message = "year of creation cannot be empty")
    private Integer created;

    @Schema(example = "2" , minimum = "0")
    @NotNull(message = "number of generation cannot be empty")
    private Integer generation;


    @Schema(description = "Should be an existing brand name", example = "Mercedes-Benz")
    @NotBlank(message = "brand name cannot be empty")
    private String brandName;

    public Model toModel() {
        return Model.builder()
                .name(name)
                .imageUrl(imageUrl == null || imageUrl.isBlank() ? "https://www.topgear.com/sites/default/files/news/image/2015/04/Large%20Image_10372.jpg" : imageUrl)
                .created(created)
                .generation(generation)
                .build();
    }

}
