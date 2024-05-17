package com.weijun.chatApplication.EmbedID;

import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;


@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class MembershipId implements Serializable {
    private String requesterId;
    private Integer blockId;
}
