package com.example.mobilele.domain.entity;

import com.example.mobilele.domain.constants.Role;
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
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column
    @Enumerated
    private Role role;

    @ManyToMany(mappedBy = "userRoles")
    private List<User> userList;
}
