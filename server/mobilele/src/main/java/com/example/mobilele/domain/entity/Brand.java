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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String name;

    @Column
    private LocalDate created;

    @Column
    private LocalDate modified;

    @OneToMany(mappedBy = "brand" , cascade = CascadeType.REMOVE)
    private List<Model> modelList;

}
