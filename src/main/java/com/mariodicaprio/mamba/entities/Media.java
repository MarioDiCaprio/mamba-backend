package com.mariodicaprio.mamba.entities;


import lombok.*;

import javax.persistence.*;
import java.util.UUID;


@Entity
@Getter @Setter @NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true) @ToString(onlyExplicitlyIncluded = true)
public class Media {

    @Id
    @GeneratedValue
    @ToString.Include
    @EqualsAndHashCode.Include
    private UUID mediaId;

    /////////////////////////////////////////////////////////

    @Lob
    private byte[] data;

    /////////////////////////////////////////////////////////

    private String type;

}
