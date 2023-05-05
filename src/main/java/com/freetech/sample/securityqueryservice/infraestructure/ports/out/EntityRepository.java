package com.freetech.sample.securityqueryservice.infraestructure.ports.out;

import org.springframework.data.mongodb.core.query.Query;

import java.util.List;

public interface EntityRepository {
    <T> T save(String tableName, T entity);

    <T> T update(String tableName, T entity);

    <K, T> T getById(K id, Class<T> clazz);

    <T> List<T> getByQuery(Query query, Class<T> clazz);

    <T> Long countByQuery(Query query, Class<T> clazz);

    <T> List<T> getByQuery(String strQuery, Class<T> clazz);
    Integer countByQuery(String strQuery);
}