package com.example.mobilele.web;

import com.example.mobilele.domain.dtos.brand.BrandCreateDto;
import com.example.mobilele.domain.dtos.brand.BrandEditDto;
import com.example.mobilele.domain.dtos.brand.BrandView;
import com.example.mobilele.exceptions.AlreadyExistException;
import com.example.mobilele.exceptions.NotFoundException;
import com.example.mobilele.exceptions.WrongCredentialsException;
import com.example.mobilele.services.BrandServiceImp;
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

    @GetMapping
    public ResponseEntity<List<BrandView>> getAllBrands(@RequestParam(value = "brandId",required = false) String brandId,
                                                        @RequestParam(value = "name" , required = false) String name) throws NotFoundException {

        if(brandId != null){
            BrandView brandView = brandServiceImp.getViewBrandById(brandId);
            return new ResponseEntity<>(List.of(brandView), HttpStatus.OK);
        }else if (name != null){
            BrandView brandView = brandServiceImp.getViewBrandByName(name);
            return new ResponseEntity<>(List.of(brandView), HttpStatus.OK);
        }

        List<BrandView> brandViews = brandServiceImp.getAllBrandsView();
        return new ResponseEntity<>(brandViews, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<HttpStatus> createBrand(@Valid @RequestBody BrandCreateDto brandCreateDto,
                                                  BindingResult result) throws WrongCredentialsException, AlreadyExistException {
        if (result.hasErrors()) {
            throw new WrongCredentialsException(result.getAllErrors());
        }
        brandServiceImp.save(brandCreateDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PatchMapping
    public ResponseEntity<BrandView> editBrand(@RequestBody BrandEditDto brandEditDto, @RequestParam("id") String id) throws AlreadyExistException, NotFoundException, WrongCredentialsException {

        BrandView brandView = brandServiceImp.edit(brandEditDto , id);
        return new ResponseEntity<>(brandView, HttpStatus.OK);

    }

    @DeleteMapping
    public ResponseEntity<HttpStatus> deleteBrand(@RequestParam("id") String brandId) throws NotFoundException {

        brandServiceImp.deleteBrand(brandId );
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
