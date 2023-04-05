package com.freetech.sample.securityqueryservice.infraestructure.ports.in;

import com.freetech.sample.securityqueryservice.application.queries.GetAllUserQuery;
import com.freetech.sample.securityqueryservice.domain.User;
import pagination.ResultQuery;

public interface QueryUserPort {
    User getById(Integer id);
    ResultQuery<User> getAll(GetAllUserQuery getAllUserQuery);
}