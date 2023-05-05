package com.freetech.sample.securityqueryservice.domain.filters;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@Setter
public class GetAllEntityTypeQuery {
    private String name;
    private Integer pageNumber;
    private Integer pageSize;
}
