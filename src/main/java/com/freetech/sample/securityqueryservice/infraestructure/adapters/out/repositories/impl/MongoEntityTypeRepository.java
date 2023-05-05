package com.freetech.sample.securityqueryservice.infraestructure.adapters.out.repositories.impl;

import com.freetech.sample.securityqueryservice.domain.filters.EntityTypeFilter;
import com.freetech.sample.securityqueryservice.infraestructure.adapters.out.documents.EntityTypeDocument;
import com.freetech.sample.securityqueryservice.infraestructure.adapters.out.repositories.EntityTypeRepository;
import com.freetech.sample.securityqueryservice.infraestructure.ports.out.EntityRepository;
import enums.StateEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor
@Component
public class MongoEntityTypeRepository implements EntityTypeRepository {
    private final EntityRepository entityRepository;

    @Override
    public EntityTypeDocument getById(Long id) {
        return entityRepository.getById(id, EntityTypeDocument.class);
    }

    @Override
    public List<EntityTypeDocument> getAllPageable(EntityTypeFilter entityTypeFilter) {
        return entityRepository.getByQuery(buildQueryGetAllPageable(entityTypeFilter, false), EntityTypeDocument.class);
    }

    @Override
    public Integer countGetAllPageable(EntityTypeFilter entityTypeFilter) {
        return entityRepository.countByQuery(buildQueryGetAllPageable(entityTypeFilter, true));
    }

    private String buildQueryGetAllPageable(EntityTypeFilter entityTypeFilter, boolean isCount) {
        StringBuilder mql = new StringBuilder();

        mql.append("{ 'aggregate': 'entity_types', 'pipeline': [");
        mql.append("{ $addFields: { 'id': '$_id', 'logState': '$log_state'} }, ");
        mql.append("{ $project: { __v: 0, '_id': 0,'_class': 0, 'log_creation_user': 0, 'log_update_user': 0, 'log_creation_date': 0, 'log_update_date': 0, 'log_state': 0} }, ");
        mql.append("{ $match: { 'logState': ").append(StateEnum.ACTIVE.getValue()).append(", ");

        if (entityTypeFilter.getId() != null) {
            mql.append("'id': ").append(entityTypeFilter.getId());
        }

        if (entityTypeFilter.getName() != null) {
            mql.append("'name': ").append("/").append(entityTypeFilter.getName()).append("/i, ");
        }

        mql.append("} },");

        if (isCount) {
            mql.append("{ $count: 'total'}");
        } else {
            if (entityTypeFilter.getPageNumber() != null && entityTypeFilter.getPageSize() != null) {
                mql.append("{ $skip: ").append((entityTypeFilter.getPageNumber()-1)*entityTypeFilter.getPageSize()).append(" }");
                mql.append("{ $limit: ").append(entityTypeFilter.getPageSize()).append(" }");
            }
        }

        mql.append("], 'cursor': { } }");
        return mql.toString();
    }
}
