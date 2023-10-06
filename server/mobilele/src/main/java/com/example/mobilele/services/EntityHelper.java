package com.example.mobilele.services;

import com.example.mobilele.domain.entity.Brand;
import com.example.mobilele.domain.entity.Model;
import com.example.mobilele.domain.entity.Offer;
import com.example.mobilele.domain.entity.User;
import com.example.mobilele.exceptions.NotFoundException;
import com.example.mobilele.exceptions.WrongCredentialsException;
import com.example.mobilele.repos.BrandRepository;
import com.example.mobilele.repos.ModelRepository;
import com.example.mobilele.repos.OfferRepository;
import com.example.mobilele.repos.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Component
@AllArgsConstructor
public class EntityHelper {
    private final ModelRepository modelRepository;
    private final OfferRepository offerRepository;
    private final UserRepository userRepository;
    private final BrandRepository brandRepository;

    @Transactional
    public  User findUserById(String id) throws NotFoundException, WrongCredentialsException {
        return userRepository.findById(convertToUUID(id))
                .orElseThrow(() -> new NotFoundException("User with id :"+  id +" does not exists"));
    }
    @Transactional
    public Offer findOfferById(String id) throws NotFoundException, WrongCredentialsException {
        return offerRepository.findById(convertToUUID(id))
                .orElseThrow(() -> new NotFoundException("Offer with id :" + id + " does not exists"));
    }
    @Transactional
    public Brand findBrandById(String id) throws NotFoundException, WrongCredentialsException {
        return brandRepository.findById(convertToUUID(id))
                .orElseThrow(() -> new NotFoundException("Brand with id :" + id + " does not exists"));
    }
    @Transactional
    public Model findModelById(String id) throws WrongCredentialsException, NotFoundException {
        return modelRepository.findById(convertToUUID(id))
                .orElseThrow(() -> new NotFoundException("Model with id :" + id + " does not exists"));
    }


    @Transactional
    public Model findModelByName(String name) throws NotFoundException {
        return modelRepository.findByName(name)
                .orElseThrow(() -> new NotFoundException("Model "+ name + " does not exists"));
    }
    @Transactional
    public Brand findBrandByName(String name) throws NotFoundException {
        return brandRepository.findByName(name)
                .orElseThrow(() -> new NotFoundException("Brand "+ name + " does not exists"));
    }

    private Long convertToUUID(String id) throws WrongCredentialsException {
        try{
            return Long.parseLong(id);
        }catch (IllegalArgumentException e){
            throw new WrongCredentialsException("Wrong format of the id");
        }
    }

}
