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
public class Brand {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column
    private String name;

    @Column
    private LocalDate created;

    @Column
    private LocalDate modified;

    @OneToMany(mappedBy = "brand")
    private List<Model> modelList;

}
