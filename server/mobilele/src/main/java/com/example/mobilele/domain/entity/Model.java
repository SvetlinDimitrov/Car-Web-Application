package com.example.mobilele.domain.entity;

import com.example.mobilele.utils.constants.ModelCategory;
import jakarta.persistence.*;
import lombok.*;

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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column
    private ModelCategory category;

    @Column(columnDefinition = "varchar(512)")
    private String imageUrl;

    @Column
    private Integer created;

    @Column
    private Integer generation;

    @ManyToOne
    private Brand brand;

    @OneToMany(mappedBy = "model" , cascade = CascadeType.REMOVE)
    private List<Offer> offerList;

    @ManyToMany(mappedBy = "modelList")
    private List<User> userList;
}
