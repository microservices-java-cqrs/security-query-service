package com.freetech.sample.securityqueryservice.infraestructure.adapters.out.repositories.impl;

import com.freetech.sample.securityqueryservice.domain.filters.UserFilter;
import com.freetech.sample.securityqueryservice.infraestructure.adapters.out.documents.UserDocument;
import com.freetech.sample.securityqueryservice.infraestructure.adapters.out.repositories.UserRepository;
import com.freetech.sample.securityqueryservice.infraestructure.ports.out.EntityRepository;
import enums.StateEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor
@Component
public class MongoUserRepository implements UserRepository {
    private final EntityRepository entityRepository;

    @Override
    public UserDocument getById(Long id) {
        return entityRepository.getById(id, UserDocument.class);
    }

    @Override
    public List<UserDocument> getAllPageable(UserFilter userFilter) {
        return entityRepository.getByQuery(buildQueryGetAllPageable(userFilter, false), UserDocument.class);
    }

    @Override
    public Integer countGetAllPageable(UserFilter userFilter) {
        return entityRepository.countByQuery(buildQueryGetAllPageable(userFilter, true));
    }

    private String buildQueryGetAllPageable(UserFilter userFilter, boolean isCount) {
        StringBuilder mql = new StringBuilder();

        mql.append("{ 'aggregate': 'users', 'pipeline': [");
        mql.append("{ $lookup: { from: 'entities', localField: 'entity_id', foreignField: '_id', as: 'entityDocument' } },");
        mql.append("{ $lookup: { from: 'entity_types', localField: 'entityDocument.entity_type_id', foreignField: '_id', as: 'entityTypeDocument' } },");
        mql.append("{ $unwind: '$entityDocument' },");
        mql.append("{ $unwind: '$entityTypeDocument' },");
        mql.append("{ $addFields: { 'id': '$_id', 'logState': '$log_state', 'entityDocument.id': '$entityDocument._id', 'entityDocument.entityTypeDocument.id': '$entityDocument.entity_type_id',");
        mql.append("'entityDocument.entityTypeDocument.name': '$entityTypeDocument.name', 'entityDocument.numberDocument': '$entityDocument.number_document', 'entityDocument.bussinessName': '$entityDocument.bussiness_name',} },");
        mql.append("{ $project: { __v: 0, '_id': 0,'_class': 0, 'entity_id': 0, 'log_creation_user': 0, 'log_update_user': 0, 'log_creation_date': 0,");
        mql.append("'log_update_date': 0, 'log_state': 0, 'entityDocument._id': 0, 'entityDocument.entity_type_id': 0, 'entityDocument.number_document': 0, 'entityDocument.bussiness_name': 0, 'entityDocument.log_creation_user': 0, ");
        mql.append("'entityDocument.log_update_user': 0, 'entityDocument.log_creation_date': 0, 'entityDocument.log_update_date': 0,");
        mql.append("'entityDocument.log_state': 0, 'entityDocument._class': 0, 'entityTypeDocument': 0 } },");
        mql.append("{ $match: { 'logState': ").append(StateEnum.ACTIVE.getValue()).append(", ");

        if (userFilter.getId() != null) {
            mql.append("'id': ").append(userFilter.getId());
        }

        if (userFilter.getUsername() != null) {
            mql.append("'username': '").append(userFilter.getUsername()).append("', ");
        }

        if (userFilter.getStatus() != null) {
            mql.append("'status': '").append(userFilter.getStatus()).append("', ");
        }

        if (userFilter.getEntityTypeId() != null) {
            mql.append("'entityDocument.entityTypeDocument.id': ").append(userFilter.getEntityTypeId()).append(", ");
        }

        if (userFilter.getEntityName() != null) {
            mql.append("'entityDocument.name': ").append("/").append(userFilter.getEntityName()).append("/i, ");
        }

        if (userFilter.getEntityLastname() != null) {
            mql.append("'entityDocument.lastname': ").append("/").append(userFilter.getEntityLastname()).append("/i, ");
        }

        mql.append("} },");

        if (isCount) {
            mql.append("{ $count: 'total'}");
        } else {
            if (userFilter.getPageNumber() != null && userFilter.getPageSize() != null) {
                mql.append("{ $skip: ").append((userFilter.getPageNumber()-1)*userFilter.getPageSize()).append(" }, ");
                mql.append("{ $limit: ").append(userFilter.getPageSize()).append(" }");
            }
        }

        mql.append("], 'cursor': { } }");
        return mql.toString();
    }
}
