package com.freetech.sample.securityqueryservice.domain.filters;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@Setter
public class UserFilter {
    private Long id;
    private Long entityTypeId;
    private String username;
    private String entityName;
    private String entityLastname;
    private String status;
    private Integer pageNumber;
    private Integer pageSize;
}
