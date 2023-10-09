package com.example.mobilele.services;

import com.example.mobilele.domain.dtos.offer.OfferCreateDto;
import com.example.mobilele.domain.dtos.offer.OfferEditDto;
import com.example.mobilele.domain.dtos.offer.OfferView;
import com.example.mobilele.domain.entity.Model;
import com.example.mobilele.domain.entity.Offer;
import com.example.mobilele.domain.entity.User;
import com.example.mobilele.exceptions.NotFoundException;
import com.example.mobilele.exceptions.WrongCredentialsException;
import com.example.mobilele.repos.OfferRepository;
import com.example.mobilele.utils.constants.EngineType;
import com.example.mobilele.utils.constants.TransmissionType;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Year;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class OfferServiceImp {

    private OfferRepository offerRepository;
    private EntityHelper entityHelper;

    @Transactional
    public OfferView getOfferViewById(String id) throws WrongCredentialsException, NotFoundException {
        return new OfferView(entityHelper.findOfferById(id));
    }
    @Transactional
    public List<OfferView> getAllOfferViews() {
        return offerRepository.findAll()
                .stream()
                .map(OfferView::new)
                .toList();
    }
    @Transactional
    public List<OfferView> getAllOfferViews(String userId) throws NotFoundException, WrongCredentialsException {
        return entityHelper.findUserById(userId)
                .getOfferList()
                .stream()
                .map(OfferView::new)
                .toList();
    }

    public void save(OfferCreateDto offerCreateDto, String userId) throws WrongCredentialsException, NotFoundException {
        Offer offer = offerCreateDto.toOffer();

        User user = entityHelper.findUserById(userId);
        Model model = entityHelper.findModelByName(offerCreateDto.getModelName());

        offer.setImageUrl(model.getImageUrl());
        offer.setEngine(getEngineByName(offerCreateDto.getEngine()));
        offer.setTransmission(getTransmissionByName(offerCreateDto.getTransmission()));
        offer.setSeller(user);
        offer.setModel(model);

        offerRepository.save(offer);
    }
    @Modifying
    public OfferView edit(OfferEditDto modelEditDto, String id) throws NotFoundException, WrongCredentialsException {
        Offer offer = entityHelper.findOfferById(id);

        if (modelEditDto.getDescription() != null) {
            if(modelEditDto.getDescription().length() >= 10){
                offer.setDescription(modelEditDto.getDescription());
            }else{
                throw new WrongCredentialsException("description must be at least 10 chars long");
            }
        }
        if (modelEditDto.getEngine() != null) {
            offer.setEngine(getEngineByName(modelEditDto.getEngine()));
        }
        if (modelEditDto.getImageUrl() != null) {
            offer.setImageUrl(modelEditDto.getImageUrl());
        }
        if (modelEditDto.getMileage() != null) {
            if(modelEditDto.getMileage() < 0){
                throw new WrongCredentialsException("mileage cannot be negative");
            }
            offer.setMileage(modelEditDto.getMileage());
        }
        if (modelEditDto.getPrice() != null) {
            try {
                BigDecimal number = new BigDecimal(modelEditDto.getPrice());
                if(number.compareTo(BigDecimal.ZERO) < 0){
                    throw new WrongCredentialsException("price should be positive");
                }
                offer.setPrice(number);
            } catch (NumberFormatException e) {
               throw new WrongCredentialsException("Invalid format of the price");
            }
        }
        if (modelEditDto.getTransmission() != null) {
            offer.setTransmission(getTransmissionByName(modelEditDto.getTransmission()));
        }
        if (modelEditDto.getYear() != null) {
            if(modelEditDto.getYear() >= 0){
                if(modelEditDto.getYear() <= Year.now().getValue()){
                    offer.setYear(modelEditDto.getYear());
                }else{
                    throw new WrongCredentialsException("year cannot be in the future");
                }
            }else{
                throw new WrongCredentialsException("year must be a positive number");
            }

        }
        if (modelEditDto.getModelName() != null) {
            Model model = entityHelper.findModelByName(modelEditDto.getModelName());
            offer.setModel(model);
        }

        offer.setModified(LocalDate.now());
        offerRepository.save(offer);
        return new OfferView(offer);
    }
    @Modifying
    public void deleteOffer(String id) throws NotFoundException, WrongCredentialsException {
        Offer offer = entityHelper.findOfferById(id);
        offerRepository.delete(offer);
    }
    public boolean userContainOffer(String userId, String offerId) throws NotFoundException, WrongCredentialsException {
        return entityHelper.findUserById(userId)
                .getOfferList()
                .stream()
                .anyMatch(o->o.getId().toString().equals(offerId));
    }
    private TransmissionType getTransmissionByName(String name) throws WrongCredentialsException {
        return Arrays.stream(TransmissionType.values())
                .filter(e -> e.name().equals(name))
                .findFirst()
                .orElseThrow(() -> new WrongCredentialsException("Does not have that type of transmission.\nAvailable types:" +
                        Arrays.stream(TransmissionType.values())
                                .map(Enum::name)
                                .collect(Collectors.joining(","))));
    }
    private EngineType getEngineByName(String name) throws WrongCredentialsException {
        return Arrays.stream(EngineType.values())
                .filter(e -> e.name().equals(name))
                .findFirst()
                .orElseThrow(() -> new WrongCredentialsException("Does not have that type of engine.\nAvailable types:" +
                        Arrays.stream(EngineType.values())
                                .map(Enum::name)
                                .collect(Collectors.joining(","))));
    }



}
