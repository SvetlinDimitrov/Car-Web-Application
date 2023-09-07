package com.example.mobilele.domain.entity;

import com.example.mobilele.domain.constants.ModelCategory;
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
public class Model {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column
    private String name;

    @Enumerated
    @Column
    private ModelCategory category;

    @Column(name = "image_url",columnDefinition = "varchar(512)")
    private String imageUrl;

    @Column
    private Integer created;

    @Column
    private Integer generation;

    @ManyToOne
    private Brand brand;

    @OneToMany(mappedBy = "model")
    private List<Offer> offerList;

    @ManyToMany(mappedBy = "modelList")
    private List<User> userList;
}