package com.example.mobilele.domain.dtos.model;

import com.example.mobilele.domain.entity.Model;
import com.example.mobilele.validators.modelNameChecker.ValidModelName;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ModelCreateDto {

    @NotBlank(message = "name cannot be blank")
    @ValidModelName
    private String name;

    @NotBlank(message = "category cannot be null. It can be : Car, Buss, Truck, Motorcycle")
    private String category;

    private String imageUrl;

    @NotNull(message = "year of creation cannot be empty")
    private Integer created;

    @NotNull(message = "number of generation cannot be empty")
    private Integer generation;

    @NotBlank(message = "brand name cannot be empty")
    private String brandName;

    public Model toModel(){
        return Model.builder()
                .name(name)
                .imageUrl(imageUrl == null || imageUrl.isBlank() ? "https://www.topgear.com/sites/default/files/news/image/2015/04/Large%20Image_10372.jpg" : imageUrl)
                .created(created)
                .generation(generation)
                .build();
    }

}
