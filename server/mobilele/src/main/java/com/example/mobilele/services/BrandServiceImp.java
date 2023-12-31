package com.example.mobilele.services;

import com.example.mobilele.domain.dtos.brand.BrandCreateDto;
import com.example.mobilele.domain.dtos.brand.BrandEditDto;
import com.example.mobilele.domain.dtos.brand.BrandView;
import com.example.mobilele.domain.entity.Brand;
import com.example.mobilele.exceptions.AlreadyExistException;
import com.example.mobilele.exceptions.NotFoundException;
import com.example.mobilele.exceptions.WrongCredentialsException;
import com.example.mobilele.repos.BrandRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@CrossOrigin
public class BrandServiceImp{

    private final BrandRepository brandRepository;
    private final EntityHelper entityHelper;

    public List<BrandView> getAllBrandsView() {
        return brandRepository.findAll()
                .stream()
                .map(BrandView::new)
                .collect(Collectors.toList());
    }
    public BrandView getViewBrandById(String brandId) throws NotFoundException, WrongCredentialsException {
        return new BrandView(entityHelper.findBrandById(brandId));
    }
    public BrandView getViewBrandByName(String name) throws NotFoundException {
        return new BrandView(entityHelper.findBrandByName(name));
    }

    public void save(BrandCreateDto brandCreateDto) throws AlreadyExistException {
        validBrandUsername(brandCreateDto.getName());
        Brand brand = brandCreateDto.toBrand();
        brandRepository.save(brand);

    }

    @Modifying
    public BrandView edit(BrandEditDto brandEditDto, String id)
            throws NotFoundException, WrongCredentialsException {

        Brand brand = entityHelper.findBrandById(id);

        if (brandEditDto.getName() != null && !brand.getName().equals(brandEditDto.getName())) {
            if (brandEditDto.getName().trim().length() >= 4) {
                brand.setName(brandEditDto.getName());
            } else {
                throw new WrongCredentialsException("username should be at last 4 chars long");
            }
        }
        if(brandEditDto.getCreated() != null ){
            brand.setCreated(brandEditDto.getCreated());
        }
        brand.setModified(LocalDate.now());
        brandRepository.save(brand);

        return new BrandView(brand);


    }

    @Modifying
    public void deleteBrand(String brandId) throws NotFoundException, WrongCredentialsException {

        Brand brand = entityHelper.findBrandById(brandId);

        brandRepository.delete(brand);
    }

    private void validBrandUsername(String name) throws AlreadyExistException {

        if (name != null && brandRepository.findByName(name).isPresent()) {
            throw new AlreadyExistException("Brand with the current name already exists in the date");
        }
    }

}
