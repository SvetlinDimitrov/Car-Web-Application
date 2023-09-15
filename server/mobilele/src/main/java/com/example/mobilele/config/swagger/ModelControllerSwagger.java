package com.example.mobilele.config.swagger;
import com.example.mobilele.domain.dtos.model.ModelView;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.stereotype.Component;

@Component
public class ModelControllerSwagger {

    @Operation(summary = "Get model/models")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "If you don't pass a parameter, it will provide all models; otherwise, you will receive a specific model",
                    content = @Content(
                            array = @ArraySchema(schema = @Schema(implementation = ModelView.class)),
                            mediaType = "application/json")
            )
    })
    public void getModel() {}
    @Operation(summary = "Get a specific model by ID")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Found the model",
                    content = @Content(
                            schema = @Schema(implementation = ModelView.class),
                            mediaType = "application/json")
            ),
    })
    public void getModelById(
            @Parameter(
                    name = "id",
                    description = "The ID of the model to retrieve.",
                    example = "26f8200e-4dbf-4ae0-8441-30ea40370ba4",
                    schema = @Schema(implementation = String.class)
            ) String modelId) {
    }
}
