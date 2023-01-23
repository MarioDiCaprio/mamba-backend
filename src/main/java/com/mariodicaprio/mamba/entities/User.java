package com.mariodicaprio.mamba.entities;


import javax.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
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

    /////////////////////////////////////////////////////////

    @Column(unique = true)
    @ToString.Include
    private String username;

    /////////////////////////////////////////////////////////

    private String email;

    /////////////////////////////////////////////////////////

    private String password;

    /////////////////////////////////////////////////////////

    @JoinTable
    @ManyToMany(fetch = FetchType.EAGER)
    private Collection<Role> roles = new ArrayList<>();

    /////////////////////////////////////////////////////////

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE}, mappedBy = "following")
    private List<User> followers = new ArrayList<>();

    /////////////////////////////////////////////////////////

    @JoinTable
    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<User> following = new ArrayList<>();

    /////////////////////////////////////////////////////////

    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE}, mappedBy = "owner")
    private List<Post> posts = new ArrayList<>();

    /////////////////////////////////////////////////////////

    @JoinTable
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private List<Post> likes = new ArrayList<>();

}
