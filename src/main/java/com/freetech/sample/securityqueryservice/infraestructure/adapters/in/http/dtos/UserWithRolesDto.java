package com.freetech.sample.securityqueryservice.infraestructure.adapters.in.http.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@NoArgsConstructor
@Setter
public class UserWithRolesDto {
    private Long id;
    private String username;
    private List<RolDto> roles;
}
