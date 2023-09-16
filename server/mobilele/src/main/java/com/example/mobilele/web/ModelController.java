package com.example.mobilele.web;

import com.example.mobilele.domain.dtos.model.ModelCreateDto;
import com.example.mobilele.domain.dtos.model.ModelEditDto;
import com.example.mobilele.domain.dtos.model.ModelView;
import com.example.mobilele.exceptions.NotFoundException;
import com.example.mobilele.exceptions.WrongCredentialsException;
import com.example.mobilele.services.ModelServiceImp;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.base-url}" + "/model")
@AllArgsConstructor
public class ModelController {

    private final ModelServiceImp modelServiceImp;


    @Operation(
            summary = "Get mode/models",
            description = "Retrieve a ModelView object with 'id' or 'name' as parameters. Without passing any parameters, you will retrieve all models",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "If you don't pass a parameter, it will provide all models",
                            content = @Content(
                                    array = @ArraySchema(schema = @Schema(implementation = ModelView.class)),
                                    mediaType = "application/json")
                    )
            },
            parameters = {
                    @Parameter(
                            name = "id",
                            description = "The ID of the model to retrieve.",
                            example = "1"
                    ),
                    @Parameter(
                            name = "name",
                            description = "The name of the model to retrieve.",
                            example = "e-tron_50_Quatrain"
                    )
            }
    )
    @GetMapping
    public ResponseEntity<List<ModelView>> getModel(@RequestParam(value = "id", required = false) String modelId,
                                                    @RequestParam(value = "name", required = false) String name) throws NotFoundException, WrongCredentialsException {

        if (modelId != null) {
            ModelView modelView = modelServiceImp.getModelViewById(modelId);
            return new ResponseEntity<>(List.of(modelView), HttpStatus.OK);
        } else if (name != null) {
            ModelView modelView = modelServiceImp.getModelViewByName(name);
            return new ResponseEntity<>(List.of(modelView), HttpStatus.OK);
        }

        List<ModelView> modelViews = modelServiceImp.getAllModelViews();
        return new ResponseEntity<>(modelViews, HttpStatus.OK);
    }

    @Operation(
            summary = "Create model",
            description = "The created model will be associated with the logged-in user and with the provided brand.",
            security = {@SecurityRequirement(name = "bearerAuth")},
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Create a model using ModelCreateDto object",
                            content = @Content(mediaType = "application/json")
                    ),
                    @ApiResponse(
                            responseCode = "403",
                            description = "Only for Admins",
                            content = @Content(mediaType = "application/json")
                    )
            }
    )
    @PostMapping
    public ResponseEntity<HttpStatus> createModel(@Valid @RequestBody ModelCreateDto modelCreateDto,
                                                  BindingResult result) throws WrongCredentialsException, NotFoundException {
        if (result.hasErrors()) {
            throw new WrongCredentialsException(result.getAllErrors());
        }
        modelServiceImp.save(modelCreateDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Operation(
            summary = "Edit model",
            security = {@SecurityRequirement(name = "bearerAuth")},
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "If you send an empty body the request will be successful",
                            content = @Content(
                                    schema = @Schema(implementation = ModelView.class),
                                    mediaType = "application/json")

                    ),
                    @ApiResponse(
                            responseCode = "403",
                            description = "Only for Admins",
                            content = @Content(mediaType = "application/json")
                    )
            },
            parameters = {
                    @Parameter(
                            name = "id",
                            description = "The ID of the model to change.",
                            example = "1"
                    )
            }
    )
    @PatchMapping
    public ResponseEntity<ModelView> editModel(@RequestBody ModelEditDto modelEditDto,
                                               @RequestParam("id") String id) throws NotFoundException, WrongCredentialsException {

        ModelView modelView = modelServiceImp.edit(modelEditDto, id);
        return new ResponseEntity<>(modelView, HttpStatus.OK);

    }

    @Operation(
            summary = "Delete model",
            security = {@SecurityRequirement(name = "bearerAuth")},
            responses = {
                    @ApiResponse(
                            responseCode = "204",
                            description = "Delete a model using its ID",
                            content = @Content(mediaType = "application/json")
                    ),
                    @ApiResponse(
                            responseCode = "403",
                            description = "Only for Admins",
                            content = @Content(mediaType = "application/json")
                    )
            },
            parameters = {
                    @Parameter(
                            name = "id",
                            description = "The ID of the model to delete.",
                            example = "1"
                    )
            }
    )
    @DeleteMapping
    public ResponseEntity<HttpStatus> deleteModel(@RequestParam("id") String modelId) throws NotFoundException, WrongCredentialsException {

        modelServiceImp.deleteModel(modelId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
