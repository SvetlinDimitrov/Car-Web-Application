package com.example.mobilele.services;

import com.example.mobilele.domain.dtos.model.ModelCreateDto;
import com.example.mobilele.domain.dtos.model.ModelEditDto;
import com.example.mobilele.domain.dtos.model.ModelView;
import com.example.mobilele.domain.entity.Model;
import com.example.mobilele.exceptions.NotFoundException;
import com.example.mobilele.exceptions.WrongCredentialsException;
import com.example.mobilele.repos.ModelRepository;
import com.example.mobilele.utils.EntityHelper;
import com.example.mobilele.utils.constants.ModelCategory;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ModelServiceImp {

    private final ModelRepository modelRepository;
    private final EntityHelper entityHelper;

    public ModelView getModelViewById(String modelId) throws NotFoundException, WrongCredentialsException {
        return new ModelView(entityHelper.findModelById(modelId));
    }

    public ModelView getModelViewByName(String name) throws NotFoundException {
        return new ModelView(entityHelper.findModelByName(name));
    }

    public List<ModelView> getAllModelViews() {
        return modelRepository.findAll()
                .stream()
                .map(ModelView::new)
                .toList();
    }

    public void save(ModelCreateDto modelCreateDto) throws NotFoundException, WrongCredentialsException {

        Model model = modelCreateDto.toModel();
        model.setBrand(entityHelper.findBrandByName(modelCreateDto.getBrandName()));
        model.setCategory(getModelCategoryByName(modelCreateDto.getCategory()));
        modelRepository.save(model);
    }

    public boolean findByName(String name) {
        return modelRepository.findByName(name).isPresent();
    }

    @Modifying
    public ModelView edit(ModelEditDto modelEditDto, String id) throws NotFoundException, WrongCredentialsException {
        Model model = entityHelper.findModelById(id);

        if (modelEditDto.getName() != null) {
            if (findByName(modelEditDto.getName())) {
                throw new WrongCredentialsException("This name already exits in the date base.");
            }
            model.setName(modelEditDto.getName());
        }
        if (modelEditDto.getCategory() != null) {
            model.setCategory(getModelCategoryByName(modelEditDto.getCategory()));
        }
        if (modelEditDto.getImageUrl() != null) {
            model.setImageUrl(modelEditDto.getImageUrl());
        }
        if (modelEditDto.getCreated() != null) {
            model.setCreated(modelEditDto.getCreated());
        }
        if (modelEditDto.getGeneration() != null) {
            model.setGeneration(modelEditDto.getGeneration());
        }
        if (modelEditDto.getBrandName() != null) {
            model.setBrand(entityHelper.findBrandByName(modelEditDto.getBrandName()));
        }

        Model saved = modelRepository.save(model);

        return new ModelView(saved);
    }

    @Modifying
    public void deleteModel(String modelId) throws NotFoundException, WrongCredentialsException {
        modelRepository.delete(entityHelper.findModelById(modelId));
    }

    public void seed() throws NotFoundException {
        if (modelRepository.count() == 0) {
            modelRepository.saveAll(List.of(
                    Model.builder()
                            .name("BMW X6")
                            .created(2008)
                            .brand(entityHelper.findBrandByName("BMW"))
                            .category(ModelCategory.Car)
                            .imageUrl("https://s1.cdn.autoevolution.com/images/models/BMW_X6-2023_main.jpg")
                            .generation(5)
                            .build(),
                    Model.builder()
                            .name("BMW M4 Convertible")
                            .created(2014)
                            .brand(entityHelper.findBrandByName("BMW"))
                            .category(ModelCategory.Car)
                            .imageUrl("https://s1.cdn.autoevolution.com/images/models/BMW_M4-Competition-Convertible-M-xDrive-2021_main.jpg")
                            .generation(2)
                            .build(),
                    Model.builder()
                            .name("BMW iX")
                            .created(2021)
                            .brand(entityHelper.findBrandByName("BMW"))
                            .category(ModelCategory.Car)
                            .imageUrl("https://s1.cdn.autoevolution.com/images/models/BMW_iX-2021_main.jpg")
                            .generation(1)
                            .build(),
                    Model.builder()
                            .name("A 180 Progressive")
                            .created(2019)
                            .brand(entityHelper.findBrandByName("Mercedes-Benz"))
                            .category(ModelCategory.Car)
                            .imageUrl("https://upload.wikimedia.org/wikipedia/commons/thumb/c/c2/2019_Mercedes-Benz_A220%2C_front_8.22.19.jpg/1920px-2019_Mercedes-Benz_A220%2C_front_8.22.19.jpg")
                            .generation(1)
                            .build(),
                    Model.builder()
                            .name("W213 Facelift")
                            .created(2021)
                            .brand(entityHelper.findBrandByName("Mercedes-Benz"))
                            .category(ModelCategory.Car)
                            .imageUrl("https://upload.wikimedia.org/wikipedia/commons/thumb/2/2f/Mercedes-Benz_W213_Facelift_IMG_5257.jpg/1920px-Mercedes-Benz_W213_Facelift_IMG_5257.jpg")
                            .generation(1)
                            .build(),
                    Model.builder()
                            .name("X243")
                            .created(2021)
                            .brand(entityHelper.findBrandByName("Mercedes-Benz"))
                            .category(ModelCategory.Car)
                            .imageUrl("https://upload.wikimedia.org/wikipedia/commons/thumb/0/05/Mercedes-Benz_X243_IAA_2021_1X7A0093.jpg/1920px-Mercedes-Benz_X243_IAA_2021_1X7A0093.jpg")
                            .generation(1)
                            .build(),
                    Model.builder()
                            .name("e-tron_GT")
                            .created(2021)
                            .brand(entityHelper.findBrandByName("Audi"))
                            .category(ModelCategory.Car)
                            .imageUrl("https://upload.wikimedia.org/wikipedia/commons/thumb/d/d5/Audi_e-tron_GT_IMG_5689.jpg/1920px-Audi_e-tron_GT_IMG_5689.jpg")
                            .generation(1)
                            .build(),
                    Model.builder()
                            .name("e-tron_50_Quatrain")
                            .created(2021)
                            .brand(entityHelper.findBrandByName("Audi"))
                            .category(ModelCategory.Car)
                            .imageUrl("https://upload.wikimedia.org/wikipedia/commons/thumb/8/83/Audi_Q4_e-tron_50_Quattro_IMG_5532.jpg/1920px-Audi_Q4_e-tron_50_Quattro_IMG_5532.jpg")
                            .generation(1)
                            .build(),
                    Model.builder()
                            .name("A1_Sport-back_GB")
                            .created(2010)
                            .brand(entityHelper.findBrandByName("Audi"))
                            .category(ModelCategory.Car)
                            .imageUrl("https://upload.wikimedia.org/wikipedia/commons/thumb/9/90/Audi_A1_Sportback_GB_IMG_6036.jpg/1920px-Audi_A1_Sportback_GB_IMG_6036.jpg")
                            .generation(1)
                            .build()
            ));
        }

    }

    private ModelCategory getModelCategoryByName(String name) throws WrongCredentialsException {
        return Arrays.stream(ModelCategory.values())
                .filter(e -> e.name().equals(name))
                .findFirst()
                .orElseThrow(() -> new WrongCredentialsException("Does not have that type of model category.\nAvailable types:" +
                        Arrays.stream(ModelCategory.values())
                                .map(Enum::name)
                                .collect(Collectors.joining(","))));
    }

}
