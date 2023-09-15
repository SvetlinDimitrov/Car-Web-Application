package com.example.mobilele.domain.dtos.offer;

import com.example.mobilele.domain.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OfferUserView {

    private String id;
    private String username;
    private String firstName;
    private String lastName;

    public OfferUserView(User user){
        this.id = user.getId().toString();
        this.username = user.getUsername();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
    }
}
