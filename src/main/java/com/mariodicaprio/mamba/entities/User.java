package com.mariodicaprio.mamba.entities;


import javax.persistence.*;
import lombok.*;

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

    @Column
    private String email;

    @Column
    private String password;

}
