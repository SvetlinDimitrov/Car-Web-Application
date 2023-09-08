package com.example.mobilele.services;

import com.example.mobilele.domain.constants.ModelCategory;
import com.example.mobilele.domain.entity.Model;
import com.example.mobilele.repos.BrandRepository;
import com.example.mobilele.repos.ModelRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@AllArgsConstructor
public class ModelServiceImp extends SeedService{

    private ModelRepository modelRepository;
    private BrandRepository brandRepository;

    @Override
    protected Boolean isEmpty() {
        return modelRepository.count() == 0;
    }

    @Override
    protected void seed() {
        modelRepository.saveAll(List.of(
                Model.builder()
                        .name("BMW X6")
                        .created(2008)
                        .brand(brandRepository.findByName("BMW").orElseThrow(() -> new NoSuchElementException("No Brand Found")))
                        .category(ModelCategory.Car)
                        .imageUrl("https://s1.cdn.autoevolution.com/images/models/BMW_X6-2023_main.jpg")
                        .generation(5)
                        .build(),
                Model.builder()
                        .name("BMW M4 Convertible")
                        .created(2014)
                        .brand(brandRepository.findByName("BMW").orElseThrow(() -> new NoSuchElementException("No Brand Found")))
                        .category(ModelCategory.Car)
                        .imageUrl("https://s1.cdn.autoevolution.com/images/models/BMW_M4-Competition-Convertible-M-xDrive-2021_main.jpg")
                        .generation(2)
                        .build(),
                Model.builder()
                        .name("BMW iX")
                        .created(2021)
                        .brand(brandRepository.findByName("BMW").orElseThrow(() -> new NoSuchElementException("No Brand Found")))
                        .category(ModelCategory.Car)
                        .imageUrl("https://s1.cdn.autoevolution.com/images/models/BMW_iX-2021_main.jpg")
                        .generation(1)
                        .build(),
                Model.builder()
                        .name("A 180 Progressive")
                        .created(2019)
                        .brand(brandRepository.findByName("Mercedes-Benz").orElseThrow(() -> new NoSuchElementException("No Brand Found")))
                        .category(ModelCategory.Car)
                        .imageUrl("https://upload.wikimedia.org/wikipedia/commons/thumb/c/c2/2019_Mercedes-Benz_A220%2C_front_8.22.19.jpg/1920px-2019_Mercedes-Benz_A220%2C_front_8.22.19.jpg")
                        .generation(1)
                        .build(),
                Model.builder()
                        .name("W213 Facelift")
                        .created(2021)
                        .brand(brandRepository.findByName("Mercedes-Benz").orElseThrow(() -> new NoSuchElementException("No Brand Found")))
                        .category(ModelCategory.Car)
                        .imageUrl("https://upload.wikimedia.org/wikipedia/commons/thumb/2/2f/Mercedes-Benz_W213_Facelift_IMG_5257.jpg/1920px-Mercedes-Benz_W213_Facelift_IMG_5257.jpg")
                        .generation(1)
                        .build(),
                Model.builder()
                        .name("X243")
                        .created(2021)
                        .brand(brandRepository.findByName("Mercedes-Benz").orElseThrow(() -> new NoSuchElementException("No Brand Found")))
                        .category(ModelCategory.Car)
                        .imageUrl("https://upload.wikimedia.org/wikipedia/commons/thumb/0/05/Mercedes-Benz_X243_IAA_2021_1X7A0093.jpg/1920px-Mercedes-Benz_X243_IAA_2021_1X7A0093.jpg")
                        .generation(1)
                        .build(),
                Model.builder()
                        .name("e-tron_GT")
                        .created(2021)
                        .brand(brandRepository.findByName("Audi").orElseThrow(() -> new NoSuchElementException("No Brand Found")))
                        .category(ModelCategory.Car)
                        .imageUrl("https://upload.wikimedia.org/wikipedia/commons/thumb/d/d5/Audi_e-tron_GT_IMG_5689.jpg/1920px-Audi_e-tron_GT_IMG_5689.jpg")
                        .generation(1)
                        .build(),
                Model.builder()
                        .name("e-tron_50_Quattro")
                        .created(2021)
                        .brand(brandRepository.findByName("Audi").orElseThrow(() -> new NoSuchElementException("No Brand Found")))
                        .category(ModelCategory.Car)
                        .imageUrl("https://upload.wikimedia.org/wikipedia/commons/thumb/8/83/Audi_Q4_e-tron_50_Quattro_IMG_5532.jpg/1920px-Audi_Q4_e-tron_50_Quattro_IMG_5532.jpg")
                        .generation(1)
                        .build(),
                Model.builder()
                        .name("A1_Sportback_GB")
                        .created(2010)
                        .brand(brandRepository.findByName("Audi").orElseThrow(() -> new NoSuchElementException("No Brand Found")))
                        .category(ModelCategory.Car)
                        .imageUrl("https://upload.wikimedia.org/wikipedia/commons/thumb/9/90/Audi_A1_Sportback_GB_IMG_6036.jpg/1920px-Audi_A1_Sportback_GB_IMG_6036.jpg")
                        .generation(1)
                        .build()
        ));
    }


}
