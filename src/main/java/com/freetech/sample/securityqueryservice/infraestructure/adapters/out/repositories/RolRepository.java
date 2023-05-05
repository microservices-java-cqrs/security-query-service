package com.freetech.sample.securityqueryservice.infraestructure.adapters.out.repositories;

import com.freetech.sample.securityqueryservice.infraestructure.adapters.out.documents.RolDocument;

import java.util.List;

public interface RolRepository {
    RolDocument getById(Long id);
    List<RolDocument> getAllRolesByUser(Long id);
}
