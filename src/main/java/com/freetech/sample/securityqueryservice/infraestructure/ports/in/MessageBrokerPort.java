package com.freetech.sample.securityqueryservice.infraestructure.ports.in;

import com.freetech.sample.securityqueryservice.domain.EntityType;

public interface MessageBrokerPort {
    <T> T create(String tableName, T entity);
    <T> T update(String tableName, T entity);
    <T> T delete(String tableName, T entity);
}
