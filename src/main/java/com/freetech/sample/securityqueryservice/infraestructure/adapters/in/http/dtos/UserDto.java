package com.freetech.sample.securityqueryservice.infraestructure.adapters.in.http.dtos;

import lombok.*;

@AllArgsConstructor
@Builder
@Getter
@NoArgsConstructor
@Setter
public class UserDto {
    private Long id;
    private String username;
    private String entityNumberDocument;
    private String entityBussinessName;
    private String entityName;
    private String entityLastname;
    private String status;
}
