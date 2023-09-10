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

    @GetMapping
    public ResponseEntity<List<OfferView>> getAllOffers(@RequestParam(value = "offerId",required = false) String offerId,
                                                        @RequestParam(value = "userId",required = false) String userId,
                                                        @RequestParam(value = "all" ,required = false) Boolean allOffers,
                                                        @AuthenticationPrincipal UserPrincipal principal)
            throws NotFoundException, WrongCredentialsException {

        if(offerId != null){
            OfferView offerView = offerServiceImp.getOfferViewById(offerId);
            return new ResponseEntity<>(List.of(offerView), HttpStatus.OK);
        }else if (userId != null){
            return new ResponseEntity<>(offerServiceImp.getAllOfferViews(userId), HttpStatus.OK);
        }else if (allOffers != null && allOffers){
            List<OfferView> offerView = offerServiceImp.getAllOfferViews();
            return new ResponseEntity<>(offerView, HttpStatus.OK);
        }

        return new ResponseEntity<>(offerServiceImp.getAllOfferViews(principal.getId()), HttpStatus.OK);
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
    public ResponseEntity<OfferView> editOffer(@RequestBody OfferEditDto modelEditDto,
                                               @RequestParam("id") String id,
                                               @AuthenticationPrincipal UserPrincipal principal) throws NotFoundException, WrongCredentialsException {
        User user = userServiceImp.getById(principal.getId());
        offerServiceImp.getOfferViewById(id);
        if(userServiceImp.isAdmin(user) || offerServiceImp.userContainOffer(user.getId().toString(), id)){
            OfferView modelView = offerServiceImp.edit(modelEditDto , id);
            return new ResponseEntity<>(modelView, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }

    @DeleteMapping
    public ResponseEntity<HttpStatus> deleteOffer(@RequestParam("id") String id,
                                                  @AuthenticationPrincipal UserPrincipal principal) throws NotFoundException, WrongCredentialsException {

        User user = userServiceImp.getById(principal.getId());

        if(userServiceImp.isAdmin(user) || offerServiceImp.userContainOffer(user.getId().toString() , id)){
            offerServiceImp.deleteOffer(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }

}
