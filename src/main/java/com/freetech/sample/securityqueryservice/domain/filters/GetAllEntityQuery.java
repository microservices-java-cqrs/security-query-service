package com.freetech.sample.securityqueryservice.domain.filters;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@Setter
public class GetAllEntityQuery {
    private Long id;
    private Long entityTypeId;
    private String numberDocument;
    private String bussinessName;
    private String name;
    private String lastName;
    private Integer pageNumber;
    private Integer pageSize;
}
