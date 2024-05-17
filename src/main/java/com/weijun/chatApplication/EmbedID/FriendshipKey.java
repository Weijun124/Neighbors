package com.weijun.chatApplication.EmbedID;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Embeddable
public class FriendshipKey implements Serializable {
    @Column(name="FriendrequestID")
    String FriendrequestID;
    @Column(name="FriendapproveID")
    String FriendapproveID;
}
