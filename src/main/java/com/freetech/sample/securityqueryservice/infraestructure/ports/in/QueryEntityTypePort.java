package com.freetech.sample.securityqueryservice.infraestructure.ports.in;

import com.freetech.sample.securityqueryservice.application.queries.GetAllEntityTypeQuery;
import com.freetech.sample.securityqueryservice.domain.EntityType;
import pagination.ResultQuery;

public interface QueryEntityTypePort {
    EntityType getById(Integer id);
    ResultQuery<EntityType> getAll(GetAllEntityTypeQuery getAllEntityTypeQuery);
}
