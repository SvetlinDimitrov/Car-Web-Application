package com.example.mobilele.web;


import com.example.mobilele.config.security.UserPrincipal;
import com.example.mobilele.domain.dtos.offer.OfferCreateDto;
import com.example.mobilele.domain.dtos.offer.OfferEditDto;
import com.example.mobilele.domain.dtos.offer.OfferView;
import com.example.mobilele.exceptions.NotFoundException;
import com.example.mobilele.exceptions.WrongCredentialsException;
import com.example.mobilele.services.OfferServiceImp;
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

    @GetMapping
    public ResponseEntity<List<OfferView>> getAllOffers(@RequestParam(value = "id",required = false) String id)
            throws NotFoundException, WrongCredentialsException {

        if(id != null){
            OfferView offerView = offerServiceImp.getOfferViewById(id);
            return new ResponseEntity<>(List.of(offerView), HttpStatus.OK);
        }

        List<OfferView> offerView = offerServiceImp.getAllOfferViews();
        return new ResponseEntity<>(offerView, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<HttpStatus> createOffer(@Valid @RequestBody OfferCreateDto offerCreateDto,
                                                  BindingResult result,
                                                  @AuthenticationPrincipal UserPrincipal userPrincipal) throws WrongCredentialsException, NotFoundException {
        if (result.hasErrors()) {
            throw new WrongCredentialsException(result.getAllErrors());
        }
        offerServiceImp.save(offerCreateDto , userPrincipal.getId());
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
    @PatchMapping
    public ResponseEntity<OfferView> editModel(@RequestBody OfferEditDto modelEditDto,
                                               @RequestParam("id") String id) throws NotFoundException, WrongCredentialsException {

        OfferView modelView = offerServiceImp.edit(modelEditDto , id);
        return new ResponseEntity<>(modelView, HttpStatus.OK);

    }

}
