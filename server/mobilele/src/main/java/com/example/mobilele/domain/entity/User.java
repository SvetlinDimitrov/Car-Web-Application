package com.example.mobilele.domain.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column
    private String username;

    @Column
    private String password;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "is_active")
    private Boolean isActive;

    @Column
    private String imageUrl;

    @Column
    private LocalDate created;

    @Column
    private LocalDate modified;

    @OneToMany(mappedBy = "seller")
    private List<Offer> offerList;

    @ManyToMany
    @JoinTable(
            name = "user_models",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "model_id")
    )
    private List<Model> modelList;

    @ManyToMany
    @JoinTable(
            name = "user_roles_users",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "user_role_id")
    )
    private List<UserRole> userRoles;

}
