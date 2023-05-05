package com.freetech.sample.securityqueryservice.infraestructure.adapters.out.repositories;

import com.freetech.sample.securityqueryservice.domain.filters.UserFilter;
import com.freetech.sample.securityqueryservice.infraestructure.adapters.out.documents.UserDocument;

import java.util.List;

public interface UserRepository {
    UserDocument getById(Long id);
    List<UserDocument> getAllPageable(UserFilter userFilter);
    Integer countGetAllPageable(UserFilter userFilter);
}
