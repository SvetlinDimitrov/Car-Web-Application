package com.example.mobilele.domain.dtos.brand;

import com.example.mobilele.domain.entity.Brand;
import com.example.mobilele.validators.dataFormatChecker.ValidDateString;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Data
@AllArgsConstructor
public class BrandCreateDto {

    @Schema(description = "The names are unique in the data",
            example = "Golf")
    @NotBlank(message = "name can`t be blank")
    @Size(min = 4, message = "name size should be at least 4")
    private String name;

    @Schema(description = "DateTime cannot be in the future",
            type = "string",
            format = "yyyy-MM-dd",
            example = "1999-08-12")
    @NotBlank(message = "created must not be empty")
    @ValidDateString
    private String created;

    public Brand toBrand() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return Brand.builder()
                .name(name)
                .created(LocalDate.parse(created, formatter))
                .build();
    }
}
