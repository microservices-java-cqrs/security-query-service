package com.freetech.sample.securityqueryservice.application.queries;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@Setter
public class GetAllUserQuery {
    private Integer entityTypeId;
    private String username;
    private String entityName;
    private String entityLastname;
    private String status;
    private Integer pageNumber;
    private Integer pageSize;
}
