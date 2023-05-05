package com.freetech.sample.securityqueryservice.infraestructure.adapters.out.repositories;

import com.freetech.sample.securityqueryservice.domain.filters.EntityTypeFilter;
import com.freetech.sample.securityqueryservice.domain.filters.UserFilter;
import com.freetech.sample.securityqueryservice.infraestructure.adapters.out.documents.EntityTypeDocument;
import com.freetech.sample.securityqueryservice.infraestructure.adapters.out.documents.UserDocument;

import java.util.List;

public interface EntityTypeRepository {
    EntityTypeDocument getById(Long id);
    List<EntityTypeDocument> getAllPageable(EntityTypeFilter entityTypeFilter);
    Integer countGetAllPageable(EntityTypeFilter entityTypeFilter);
}
