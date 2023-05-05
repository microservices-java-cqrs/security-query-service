package com.freetech.sample.securityqueryservice.domain.model;

import lombok.*;

import java.util.Date;
import java.util.List;

@AllArgsConstructor
@Builder
@Getter
@NoArgsConstructor
@Setter
public class User {
    private Long id;
    private Entity entity;
    private String username;
    private String password;
    private String status;
    private List<Rol> roles;
}
