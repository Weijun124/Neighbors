package com.weijun.chatApplication.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table
public class Blocks {
    @Id
    @Column(name = "bid")
    private Integer bid;
    private String name;
    private float latitude;
    private float longitude;
    private float radius;

    @ManyToOne
    @JoinColumn(name = "nid")
    private Neighborhoods neighborhoods;

    @JsonManagedReference
    @OneToMany(mappedBy = "block")
    private List<Account> accounts;

    @OneToMany(mappedBy = "block")
    private List<Membership> memberships;

    @ManyToMany(mappedBy = "blocks", fetch = FetchType.LAZY)
    private Set<Account> accountfollow = new HashSet<>();

    @Override
    public String toString() {
        return "Blocks{" +
                "bid=" + bid +
                '}';
    }
}
