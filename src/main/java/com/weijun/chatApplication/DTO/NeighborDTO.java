package com.weijun.chatApplication.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class NeighborDTO {
    private String username;
    private String neighborNames;
    private Set<String> neighborNameset;
}

