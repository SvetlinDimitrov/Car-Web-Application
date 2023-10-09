package com.example.mobilele.services;

import com.example.mobilele.domain.dtos.model.ModelCreateDto;
import com.example.mobilele.domain.dtos.model.ModelEditDto;
import com.example.mobilele.domain.dtos.model.ModelView;
import com.example.mobilele.domain.entity.Model;
import com.example.mobilele.exceptions.NotFoundException;
import com.example.mobilele.exceptions.WrongCredentialsException;
import com.example.mobilele.repos.ModelRepository;
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

        if (modelEditDto.getName() != null && !modelEditDto.getName().equals(model.getName())) {
            if(modelEditDto.getName().isBlank()){
                throw new WrongCredentialsException("Name cannot be blank");
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
            if(modelEditDto.getCreated() < 1){
               throw new WrongCredentialsException("Year of creation must be at least 1");
            }
            model.setCreated(modelEditDto.getCreated());
        }
        if (modelEditDto.getGeneration() != null) {
            if(modelEditDto.getGeneration() < 0){
                throw new WrongCredentialsException("Generation must be at least 0");
            }
            model.setGeneration(modelEditDto.getGeneration());
        }
        if (modelEditDto.getBrandName() != null) {
            model.setBrand(entityHelper.findBrandByName(modelEditDto.getBrandName()));
        }

        modelRepository.save(model);

        return new ModelView(model);
    }

    @Modifying
    public void deleteModel(String modelId) throws NotFoundException, WrongCredentialsException {
        modelRepository.delete(entityHelper.findModelById(modelId));
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
