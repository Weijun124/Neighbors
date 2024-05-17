package com.weijun.chatApplication.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Entity //for heibernate
@Table(name = "Accounts")  //for our database
public class Account implements UserDetails {
    @Id
    @JsonProperty("accountname")
    private String accountname;
    private String passwords;
    private String email;
    private String firstname;
    private String lastname;
    private String address;
    private String introduction;
    private float latitude;
    private float longitude;
    @ManyToOne
    @JoinColumn(name = "bid")
    @JsonBackReference
    private Blocks block;

    @ManyToMany(cascade = {CascadeType.ALL})
    @JoinTable(
            name = "Neighbors",
            joinColumns = {@JoinColumn(name = "Userid", referencedColumnName = "accountname")},
            inverseJoinColumns = {@JoinColumn(name = "Neighborid", referencedColumnName = "accountname")}
    )
    private Set<Account> neighbors = new HashSet<>();

    @OneToMany(mappedBy = "account")
    @JsonBackReference
    private Set<PostInformation> postInformations = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "blockfollow",
            joinColumns = {@JoinColumn(name = "accountname", referencedColumnName = "accountname")},
            inverseJoinColumns = {@JoinColumn(name = "bid", referencedColumnName = "bid")}
    )
    private Set<Blocks> blocks = new HashSet<>();


//
//    @OneToMany(mappedBy = "requester")
//    Set<Friendship> friendshiprequests;
//
//    @OneToMany(mappedBy = "approver")
//    Set<Friendship> friendshipapprovals;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }


    @Override
    public String getPassword() {
        return passwords;
    }

    @Override
    public String getUsername() {
        return accountname;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
