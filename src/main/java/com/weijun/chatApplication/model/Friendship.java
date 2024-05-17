package com.weijun.chatApplication.model;

import jakarta.persistence.*;
import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Entity
@Table
public class Friendship {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String username;
    private String friendname;
    @Column(name = "Status", length = 10)
    private String status;

    @Override
    public String toString() {
        return "Friendship{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", friendname='" + friendname + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}

//    @Id
//    @GeneratedValue(strategy = GenerationType.SEQUENCE)
//    private Long id;
//    private String username;
//    private String friendname;
//    @Column(name = "Status", length = 10)
//    private String status;