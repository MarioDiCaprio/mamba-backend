package com.mariodicaprio.mamba.entities;


import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;


@Entity
@Getter @Setter @NoArgsConstructor
@ToString(onlyExplicitlyIncluded = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Post {

    @Id
    @GeneratedValue
    @EqualsAndHashCode.Include
    @ToString.Include
    private UUID postId;

    /////////////////////////////////////////////////////////

    private String title;

    /////////////////////////////////////////////////////////

    @Lob
    private String description;

    //////////////////////////////////////////////////////////////

    @CreationTimestamp
    @Temporal(value = TemporalType.TIMESTAMP)
    private Date dateCreated;

    //////////////////////////////////////////////////////////////

    @UpdateTimestamp
    @Temporal(value = TemporalType.TIMESTAMP)
    private Date dateUpdated;

    /////////////////////////////////////////////////////////

    @OneToOne(cascade = CascadeType.ALL)
    private Media media;

    /////////////////////////////////////////////////////////

    @ManyToOne
    private User owner;

    /////////////////////////////////////////////////////////

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST, mappedBy = "likes")
    private List<User> likes = new ArrayList<>();

}
