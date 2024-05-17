package com.weijun.chatApplication.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MembershipDTO {
    private String requesterid;
    private Integer blockId;
    private String memberstatus;
    private int count;
    private Boolean requeststatus;

    @Override
    public String toString() {
        return "MembershipDTO{" +
                "requesterid='" + requesterid + '\'' +
                ", blockId=" + blockId +
                ", memberstatus='" + memberstatus + '\'' +
                ", count=" + count +
                ", requeststatus=" + requeststatus +
                '}';
    }
}
