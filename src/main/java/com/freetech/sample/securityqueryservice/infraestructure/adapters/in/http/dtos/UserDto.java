package com.freetech.sample.securityqueryservice.infraestructure.adapters.in.http.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@Setter
public class UserDto {
    private Long id;
    private EntityDto entity;
    private String username;
    private String status;
}
