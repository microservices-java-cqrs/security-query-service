package com.freetech.sample.securityqueryservice.domain.model;

import lombok.*;

@AllArgsConstructor
@Builder
@Getter
@NoArgsConstructor
@Setter
public class UserRol {
    private Long id;
    private Long userId;
    private Long rolId;
}
