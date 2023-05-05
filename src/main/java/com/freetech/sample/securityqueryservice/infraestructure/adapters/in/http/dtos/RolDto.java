package com.freetech.sample.securityqueryservice.infraestructure.adapters.in.http.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@Setter
public class RolDto {
    private Long id;
    private String name;
    private String description;
}
