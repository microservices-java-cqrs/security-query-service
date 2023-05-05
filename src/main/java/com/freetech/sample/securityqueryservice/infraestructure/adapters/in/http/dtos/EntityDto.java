package com.freetech.sample.securityqueryservice.infraestructure.adapters.in.http.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@Setter
public class EntityDto {
    private Long id;
    private Long parentId;
    private EntityTypeDto entityType;
    private String numberDocument;
    private String bussinessName;
    private String name;
    private String lastname;
}
