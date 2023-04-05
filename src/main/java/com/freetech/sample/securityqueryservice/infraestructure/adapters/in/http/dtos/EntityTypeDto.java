package com.freetech.sample.securityqueryservice.infraestructure.adapters.in.http.dtos;

import lombok.*;

@AllArgsConstructor
@Builder
@Getter
@NoArgsConstructor
@Setter
public class EntityTypeDto {
    private Long id;
    private String name;
}
