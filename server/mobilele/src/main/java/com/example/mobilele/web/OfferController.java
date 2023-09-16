package com.example.mobilele.web;


import com.example.mobilele.config.security.UserPrincipal;
import com.example.mobilele.domain.dtos.offer.OfferCreateDto;
import com.example.mobilele.domain.dtos.offer.OfferEditDto;
import com.example.mobilele.domain.dtos.offer.OfferView;
import com.example.mobilele.domain.entity.User;
import com.example.mobilele.exceptions.NotFoundException;
import com.example.mobilele.exceptions.WrongCredentialsException;
import com.example.mobilele.services.OfferServiceImp;
import com.example.mobilele.services.UserServiceImp;
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
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.base-url}" + "/offer")
@AllArgsConstructor
public class OfferController {

    private final OfferServiceImp offerServiceImp;
    private final UserServiceImp userServiceImp;

    @Operation(
            summary = "Get offer/offers",
            description = "Retrieve an OfferView object using 'id' or 'name' parameters, or retrieve all existing offers by using the 'all' parameter." +
                    "If you don't pass any parameters, you will retrieve all offers that the current user has.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "If you don't pass a parameter, it will provide all models",
                            content = @Content(
                                    array = @ArraySchema(schema = @Schema(implementation = OfferView.class)),
                                    mediaType = "application/json")
                    )
            },
            parameters = {
                    @Parameter(
                            name = "offerId",
                            description = "The ID of the offer to retrieve.",
                            example = "1"
                    ),
                    @Parameter(
                            name = "userId",
                            description = "The user's ID for receiving all of their offers.",
                            example = "1"
                    ),
                    @Parameter(
                            name = "all",
                            description = "A boolean parameter to retrieve all offers from the database.",
                            example = "true"
                    )
            }
    )
    @GetMapping
    public ResponseEntity<List<OfferView>> getAllOffers(@RequestParam(value = "offerId", required = false) String offerId,
                                                        @RequestParam(value = "userId", required = false) String userId,
                                                        @RequestParam(value = "all", required = false) Boolean allOffers,
                                                        @AuthenticationPrincipal UserPrincipal principal)
            throws NotFoundException, WrongCredentialsException {

        if (offerId != null) {
            OfferView offerView = offerServiceImp.getOfferViewById(offerId);
            return new ResponseEntity<>(List.of(offerView), HttpStatus.OK);
        } else if (userId != null) {
            return new ResponseEntity<>(offerServiceImp.getAllOfferViews(userId), HttpStatus.OK);
        } else if (allOffers != null && allOffers) {
            List<OfferView> offerView = offerServiceImp.getAllOfferViews();
            return new ResponseEntity<>(offerView, HttpStatus.OK);
        }

        return new ResponseEntity<>(offerServiceImp.getAllOfferViews(principal.getId()), HttpStatus.OK);
    }

    @Operation(
            summary = "Create offer",
            description = "The created offer will be associated with the logged-in user and the provided model.",
            security = {@SecurityRequirement(name = "bearerAuth")},
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Creates a offer using OfferCreateDto object",
                            content = @Content(mediaType = "application/json")
                    )
            }

    )
    @PostMapping
    public ResponseEntity<HttpStatus> createOffer(@Valid @RequestBody OfferCreateDto offerCreateDto,
                                                  BindingResult result,
                                                  @AuthenticationPrincipal UserPrincipal userPrincipal) throws WrongCredentialsException, NotFoundException {
        if (result.hasErrors()) {
            throw new WrongCredentialsException(result.getAllErrors());
        }
        offerServiceImp.save(offerCreateDto, userPrincipal.getId());
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Operation(
            summary = "Edit offer",
            description = "The same validation for creating a brand is applied here.",
            security = {@SecurityRequirement(name = "bearerAuth")},
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "If you send an empty body the request will be successful",
                            content = @Content(
                                    schema = @Schema(implementation = OfferView.class),
                                    mediaType = "application/json")
                    )
            },
            parameters = {
                    @Parameter(
                            name = "id",
                            description = "The ID of the offer to change.",
                            example = "1"
                    )
            }
    )
    @PatchMapping
    public ResponseEntity<OfferView> editOffer(@RequestBody OfferEditDto modelEditDto,
                                               @RequestParam("id") String id,
                                               @AuthenticationPrincipal UserPrincipal principal) throws NotFoundException, WrongCredentialsException {
        User user = userServiceImp.getById(principal.getId());
        offerServiceImp.getOfferViewById(id);
        if (userServiceImp.isAdmin(user) || offerServiceImp.userContainOffer(user.getId().toString(), id)) {
            OfferView modelView = offerServiceImp.edit(modelEditDto, id);
            return new ResponseEntity<>(modelView, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }

    @Operation(
            summary = "Delete offer",
            description = "Clients with the 'user' role can only delete their own offers, while the 'admin' role can delete any offer.",
            security = {@SecurityRequirement(name = "bearerAuth")},
            responses = {
                    @ApiResponse(
                            responseCode = "204",
                            description = "Delete a offer using its ID",
                            content = @Content(mediaType = "application/json")
                    )
            },
            parameters = {
                    @Parameter(
                            name = "id",
                            description = "The ID of the offer to change.",
                            example = "1"
                    )
            }
    )
    @DeleteMapping
    public ResponseEntity<HttpStatus> deleteOffer(@RequestParam("id") String id,
                                                  @AuthenticationPrincipal UserPrincipal principal) throws NotFoundException, WrongCredentialsException {

        User user = userServiceImp.getById(principal.getId());

        if (userServiceImp.isAdmin(user) || offerServiceImp.userContainOffer(user.getId().toString(), id)) {
            offerServiceImp.deleteOffer(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }

}
