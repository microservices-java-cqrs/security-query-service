package com.freetech.sample.securityqueryservice.domain;

import lombok.*;

import java.util.Date;

@AllArgsConstructor
@Builder
@Getter
@NoArgsConstructor
@Setter
public class EntityType {
    private Long id;
    private String name;
    private String description;
    private String logCreationUser;
    private String logUpdateUser;
    private Date logCreationDate;
    private Date logUpdateDate;
    private Integer logState;
}
