package com.example.mobilele.domain.dtos.offer;

import com.example.mobilele.domain.entity.Model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OfferModelView {

    private String id;
    private String name;

    public OfferModelView(Model model){

        this.id = model.getId().toString();
        this.name = model.getName();

    }
}
