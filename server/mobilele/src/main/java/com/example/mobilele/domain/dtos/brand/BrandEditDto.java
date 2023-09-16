package com.example.mobilele.domain.dtos.brand;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BrandEditDto {

    @Schema(description = "The names are unique in the data",
            minLength = 4)
    private String name;

    @Schema(description = "DateTime cannot be in the future")
    private LocalDate created;

}
