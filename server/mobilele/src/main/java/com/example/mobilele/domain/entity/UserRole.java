package com.example.mobilele.domain.entity;

import com.example.mobilele.utils.constants.Role;
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
public class UserRole {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    @Enumerated
    private Role role;

    @ManyToMany(mappedBy = "userRoles")
    private List<User> userList;
}
