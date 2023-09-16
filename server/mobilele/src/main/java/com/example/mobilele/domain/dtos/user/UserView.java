package com.example.mobilele.domain.dtos.user;

import com.example.mobilele.domain.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserView {

    private Long id;
    private String username;
    private String firstName;
    private String lastName;
    private Boolean isActive;
    private String imageUrl;
    private String created;
    private String modified;
    private List<UserOfferView> createdOffers;
    private List<UserModelView> ownedModels;
    private List<String> userRoles;

    public UserView(User entity){
        this.id = entity.getId();
        this.username = entity.getUsername();
        this.firstName = entity.getFirstName();
        this.lastName = entity.getLastName();
        this.isActive = entity.getIsActive();
        this.imageUrl = entity.getImageUrl();
        this.created = entity.getCreated().toString();
        this.modified = entity.getModified() == null ? "" : entity.getModified().toString();
        this.createdOffers = entity.getOfferList().stream().map(UserOfferView::new).toList();
        this.ownedModels = entity.getModelList().stream().map(UserModelView::new).toList();
        this.userRoles = entity.getUserRoles().stream().map(r -> r.getRole().name()).toList();

    }
}
