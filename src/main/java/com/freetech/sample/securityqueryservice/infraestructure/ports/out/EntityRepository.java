package com.freetech.sample.securityqueryservice.infraestructure.ports.out;

import java.util.List;

public interface EntityRepository {
    <T> T save(String tableName, T entity);

    <T> T update(String tableName, T entity);

    <T> List<T> getByQuery(String sql, Object[] parameters, Class<T> clazz);

    Integer countByQuery(String sql, Object[] parameters);
}