package com.mariodicaprio.mamba.entities;


import javax.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;


@Entity(name = "users")
@Getter @Setter @NoArgsConstructor
@ToString(onlyExplicitlyIncluded = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class User {

    @Id
    @GeneratedValue
    @EqualsAndHashCode.Include
    private UUID userId;

    @Column(unique = true)
    @ToString.Include
    private String username;

    private String email;

    private String password;

    @JoinTable
    @ManyToMany(fetch = FetchType.EAGER)
    private Collection<Role> roles = new ArrayList<>();

}
