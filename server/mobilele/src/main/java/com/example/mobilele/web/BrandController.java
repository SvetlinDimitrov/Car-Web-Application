package com.example.mobilele.web;

import com.example.mobilele.domain.dtos.brand.BrandCreateDto;
import com.example.mobilele.domain.dtos.brand.BrandEditDto;
import com.example.mobilele.domain.dtos.brand.BrandView;
import com.example.mobilele.exceptions.AlreadyExistException;
import com.example.mobilele.exceptions.NotFoundException;
import com.example.mobilele.exceptions.WrongCredentialsException;
import com.example.mobilele.services.BrandServiceImp;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.base-url}" + "/brand")
public class BrandController {

    private final BrandServiceImp brandServiceImp;

    public BrandController(BrandServiceImp brandServiceImp) {
        this.brandServiceImp = brandServiceImp;
    }


    @Operation(
            summary = "Get brand/brands",
            description = "Retrieve a BrandView object with 'id' or 'name' as parameters. Without passing any parameters, you will retrieve all brands",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "If you don't pass a parameter, it will provide all brands",
                            content = @Content(
                                    array = @ArraySchema(schema = @Schema(implementation = BrandView.class)),
                                    mediaType = "application/json")
                    )
            },
            parameters = {
                    @Parameter(
                            name = "brandId",
                            description = "The ID of the brand to retrieve.",
                            example = "1"
                    ),
                    @Parameter(
                            name = "name",
                            description = "The name of the brand to retrieve.",
                            example = "Mercedes-Benz"
                    )
            }
    )
    @GetMapping
    public ResponseEntity<List<BrandView>> getAllBrands(@RequestParam(value = "brandId", required = false) String brandId,
                                                        @RequestParam(value = "name", required = false) String name) throws NotFoundException, WrongCredentialsException {

        if (brandId != null) {
            BrandView brandView = brandServiceImp.getViewBrandById(brandId);
            return new ResponseEntity<>(List.of(brandView), HttpStatus.OK);
        } else if (name != null) {
            BrandView brandView = brandServiceImp.getViewBrandByName(name);
            return new ResponseEntity<>(List.of(brandView), HttpStatus.OK);
        }

        List<BrandView> brandViews = brandServiceImp.getAllBrandsView();
        return new ResponseEntity<>(brandViews, HttpStatus.OK);
    }

    @Operation(summary = "Create brand",
            description = "The created brand will be associated with the logged-in user.",
            security = {@SecurityRequirement(name = "bearerAuth")},
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Creates a brand using BrandCreateDto object",
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
    public ResponseEntity<HttpStatus> createBrand(@Valid @RequestBody BrandCreateDto brandCreateDto,
                                                  BindingResult result) throws WrongCredentialsException, AlreadyExistException {
        if (result.hasErrors()) {
            throw new WrongCredentialsException(result.getAllErrors());
        }
        brandServiceImp.save(brandCreateDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Operation(
            summary = "Edit brand",
            security = {@SecurityRequirement(name = "bearerAuth")},
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "If you send an empty body the request will be successful",
                            content = @Content(
                                    schema = @Schema(implementation = BrandView.class),
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
                            description = "The ID of the brand to edit.",
                            example = "1"
                    )
            }
    )
    @PatchMapping
    public ResponseEntity<BrandView> editBrand(@RequestBody BrandEditDto brandEditDto,
                                               @RequestParam("id") String id) throws NotFoundException, WrongCredentialsException {

        BrandView brandView = brandServiceImp.edit(brandEditDto, id);
        return new ResponseEntity<>(brandView, HttpStatus.OK);

    }

    @Operation(
            summary = "Delete brand",
            security = {@SecurityRequirement(name = "bearerAuth")},
            responses = {
                    @ApiResponse(
                            responseCode = "204",
                            description = "Delete a brand using its ID",
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
                            description = "The ID of the brand to delete.",
                            example = "1")
            }
    )
    @DeleteMapping
    public ResponseEntity<HttpStatus> deleteBrand(@RequestParam("id") String brandId) throws NotFoundException, WrongCredentialsException {

        brandServiceImp.deleteBrand(brandId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
