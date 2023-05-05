package com.freetech.sample.securityqueryservice.domain.model;

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
}
