package com.freetech.sample.securityqueryservice.domain.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@NoArgsConstructor
@Setter
public class Entity {
    private Long id;
    private Long parentId;
    private EntityType entityType;
    private String numberDocument;
    private String bussinessName;
    private String name;
    private String lastname;
}
