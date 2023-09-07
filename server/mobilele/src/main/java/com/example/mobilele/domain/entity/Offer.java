package com.example.mobilele.domain.entity;

import com.example.mobilele.domain.constants.EngineType;
import com.example.mobilele.domain.constants.TransmissionType;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Offer {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column
    private String description;

    @Column
    @Enumerated
    private EngineType engine;

    @Column(name = "image_url")
    private String imageUrl;

    @Column
    private Integer mileage;

    @Column
    private BigDecimal price;

    @Column
    @Enumerated
    private TransmissionType transmission;

    @Column
    private Integer year;

    @Column
    private LocalDate created;

    @Column
    private LocalDate modified;

    @ManyToOne
    private Model model;

    @ManyToOne
    private User seller;
}