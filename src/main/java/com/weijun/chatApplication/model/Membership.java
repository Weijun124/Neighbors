package com.weijun.chatApplication.model;

import com.weijun.chatApplication.EmbedID.MembershipId;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Membership")
public class Membership {
    @EmbeddedId
    private MembershipId id;

    @ManyToOne
    @MapsId("requesterId") // Maps the requesterId part of the embedded ID to the relationship
    @JoinColumn(name = "requesterid", referencedColumnName = "accountname")
    private Account requester;


    @ManyToOne
    @MapsId("blockId") // Maps the blockId part of the embedded ID to the relationship
    @JoinColumn(name = "blockid", referencedColumnName = "bid")
    private Blocks block;

    private String memberstatus;
    private int count;
    private Boolean requeststatus;

    @Override
    public String toString() {
        return "Membership{" +
                "id=" + id +
                ", requester=" + requester +
                ", block=" + block +
                ", memberstatus='" + memberstatus + '\'' +
                ", count=" + count +
                ", requeststatus='" + requeststatus + '\'' +
                '}';
    }
}
