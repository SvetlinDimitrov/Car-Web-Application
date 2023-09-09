package com.example.mobilele.services;

import com.example.mobilele.domain.dtos.brand.BrandCreateDto;
import com.example.mobilele.domain.dtos.brand.BrandEditDto;
import com.example.mobilele.domain.dtos.brand.BrandView;
import com.example.mobilele.domain.entity.Brand;
import com.example.mobilele.exceptions.AlreadyExistException;
import com.example.mobilele.exceptions.NotFoundException;
import com.example.mobilele.exceptions.WrongCredentialsException;
import com.example.mobilele.repos.BrandRepository;
import com.example.mobilele.utils.EntityHelper;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class BrandServiceImp extends SeedService {

    private final BrandRepository brandRepository;
    private final EntityHelper entityHelper;


    @Override
    protected Boolean isEmpty() {
        return brandRepository.count() == 0;
    }

    @Override
    protected void seed() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        brandRepository.saveAll(List.of(
                Brand.builder()
                        .name("BMW")
                        .created(LocalDate.parse("1916-03-07", formatter))
                        .modified(LocalDate.parse("1924-07-11", formatter))
                        .build(),
                Brand.builder()
                        .name("Mercedes-Benz")
                        .created(LocalDate.parse("1926-02-18", formatter))
                        .modified(LocalDate.parse("1956-02-13", formatter))
                        .build(),
                Brand.builder()
                        .name("Audi")
                        .created(LocalDate.parse("1909-06-16", formatter))
                        .modified(null)
                        .build()
        ));
    }

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
            throws NotFoundException, AlreadyExistException, WrongCredentialsException {

        Brand brand = entityHelper.findBrandById(id);

        validBrandUsername(brandEditDto.getName());

        if (brandEditDto.getName() != null) {
            if (brandEditDto.getName().trim().length() >= 4) {
                brand.setName(brandEditDto.getName());
            } else {
                throw new WrongCredentialsException("username should be at last 4 chars long");
            }
        }
        brand.setModified(LocalDate.now());
        Brand saved = brandRepository.save(brand);

        return new BrandView(saved);


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
