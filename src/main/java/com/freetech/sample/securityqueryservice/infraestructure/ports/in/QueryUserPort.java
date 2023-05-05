package com.freetech.sample.securityqueryservice.infraestructure.ports.in;

import com.freetech.sample.securityqueryservice.domain.filters.UserFilter;
import com.freetech.sample.securityqueryservice.domain.model.User;
import pagination.ResultQuery;

public interface QueryUserPort {
    User getById(Long id);
    ResultQuery<User> getAll(UserFilter userFilter);
    User getByIdWithRoles(Long id);
}