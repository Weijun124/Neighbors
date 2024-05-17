package com.weijun.chatApplication.model;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Neighborhoods")
@Entity
public class Neighborhoods {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "nid")
    private Integer nid;
    private String name;

    @OneToMany(mappedBy = "neighborhoods")
    @JsonManagedReference
    private List<Blocks> blocks;
}
