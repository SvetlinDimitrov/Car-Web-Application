package com.example.mobilele.web;

import com.example.mobilele.config.swagger.ModelControllerSwagger;
import com.example.mobilele.domain.dtos.model.ModelCreateDto;
import com.example.mobilele.domain.dtos.model.ModelEditDto;
import com.example.mobilele.domain.dtos.model.ModelView;
import com.example.mobilele.exceptions.NotFoundException;
import com.example.mobilele.exceptions.WrongCredentialsException;
import com.example.mobilele.services.ModelServiceImp;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.base-url}" + "/model")
@AllArgsConstructor
public class ModelController {

    private final ModelServiceImp modelServiceImp;
    private final ModelControllerSwagger modelControllerSwagger;


    @GetMapping
    public ResponseEntity<List<ModelView>> getModel(@RequestParam(value = "id", required = false) String modelId,
                                                    @RequestParam(value = "name", required = false)String name) throws NotFoundException, WrongCredentialsException {

        if (modelId != null) {
            ModelView modelView = modelServiceImp.getModelViewById(modelId);
            return new ResponseEntity<>(List.of(modelView), HttpStatus.OK);
        } else if (name != null) {
            ModelView modelView = modelServiceImp.getModelViewByName(name);
            return new ResponseEntity<>(List.of(modelView), HttpStatus.OK);
        }

        List<ModelView> modelViews = modelServiceImp.getAllModelViews();
        return new ResponseEntity<>(modelViews, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<HttpStatus> createModel(@Valid @RequestBody ModelCreateDto modelCreateDto,
                                                  BindingResult result) throws WrongCredentialsException, NotFoundException {
        if (result.hasErrors()) {
            throw new WrongCredentialsException(result.getAllErrors());
        }
        modelServiceImp.save(modelCreateDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PatchMapping
    public ResponseEntity<ModelView> editModel(@RequestBody ModelEditDto modelEditDto,
                                               @RequestParam("id") String id) throws NotFoundException, WrongCredentialsException {

        ModelView modelView = modelServiceImp.edit(modelEditDto, id);
        return new ResponseEntity<>(modelView, HttpStatus.OK);

    }

    @DeleteMapping
    public ResponseEntity<HttpStatus> deleteModel(@RequestParam("id") String modelId) throws NotFoundException, WrongCredentialsException {

        modelServiceImp.deleteModel(modelId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
