package com.freetech.sample.securityqueryservice.infraestructure.ports.in;

import com.freetech.sample.securityqueryservice.domain.filters.EntityTypeFilter;
import com.freetech.sample.securityqueryservice.domain.filters.GetAllEntityTypeQuery;
import com.freetech.sample.securityqueryservice.domain.model.EntityType;
import pagination.ResultQuery;

public interface QueryEntityTypePort {
    EntityType getById(Long id);
    ResultQuery<EntityType> getAll(EntityTypeFilter entityTypeFilter);
}
