package com.freetech.sample.securityqueryservice.infraestructure.adapters.out.repositories.impl;

import com.freetech.sample.securityqueryservice.infraestructure.adapters.out.repositories.RolRepository;
import com.freetech.sample.securityqueryservice.infraestructure.adapters.out.documents.RolDocument;
import com.freetech.sample.securityqueryservice.infraestructure.adapters.out.documents.UserRolDocument;
import com.freetech.sample.securityqueryservice.infraestructure.ports.out.EntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
public class MongoRolRepository implements RolRepository {
    private final EntityRepository entityRepository;

    @Override
    public RolDocument getById(Long id) {
        return entityRepository.getById(id, RolDocument.class);
    }

    @Override
    public List<RolDocument> getAllRolesByUser(Long id) {
        StringBuilder mql = new StringBuilder();

        mql.append("{ 'aggregate': 'users_roles', 'pipeline': [");
        mql.append("{ $lookup: { from: 'roles', localField: 'rol_id', foreignField: '_id', as: 'rolDocument' } },");
        mql.append("{ $lookup: { from: 'users', localField: 'user_id', foreignField: '_id', as: 'userDocument' } },");
        mql.append("{ $unwind: '$rolDocument'}, {$unwind: '$userDocument'},");
        mql.append("{ $addFields: {'id': '$_id', 'rolDocument.id': '$rolDocument._id', 'userDocument.id': '$userDocument._id'}}");
        mql.append("{ $project: {__v: 0,'_id': 0,'_class': 0,'rol_id': 0,'user_id': 0,'rolDocument._id': 0,'rolDocument._class': 0,");
        mql.append("'rolDocument.log_creation_user': 0,'rolDocument.log_update_user': 0,'rolDocument.log_creation_date': 0,");
        mql.append("'rolDocument.log_update_date': 0,'rolDocument.log_state': 0,'userDocument._id': 0,'userDocument._class': 0,");
        mql.append("'userDocument.entity_id': 0,'userDocument.log_creation_user': 0,'userDocument.log_update_user': 0,");
        mql.append("'userDocument.log_creation_date': 0,'userDocument.log_update_date': 0,'userDocument.log_state': 0}},");
        mql.append("{ $match: { 'userDocument.id': ").append(id).append(" } }");
        mql.append("], 'cursor': { } }");

        var userRolDocuments = entityRepository.getByQuery(mql.toString(), UserRolDocument.class);
        return userRolDocuments.stream().map(x -> x.getRolDocument()).collect(Collectors.toList());
    }
}