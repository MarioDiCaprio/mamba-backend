package com.mariodicaprio.mamba.entities;


import lombok.*;


import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;


@Entity
@Getter @Setter @NoArgsConstructor
@ToString(onlyExplicitlyIncluded = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Role {

    @Id
    @GeneratedValue
    @EqualsAndHashCode.Include
    private UUID roleId;

    private String name;

    @ManyToMany(mappedBy = "roles")
    private Collection<User> users = new ArrayList<>();

    @JoinTable
    @ManyToMany(fetch = FetchType.EAGER)
    private Collection<Privilege> privileges = new ArrayList<>();

}
