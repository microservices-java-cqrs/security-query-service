package com.freetech.sample.securityqueryservice.application;

import com.freetech.sample.securityqueryservice.application.enums.ExceptionEnum;
import com.freetech.sample.securityqueryservice.application.exceptions.BussinessException;
import com.freetech.sample.securityqueryservice.application.queries.GetAllEntityTypeQuery;
import com.freetech.sample.securityqueryservice.domain.EntityType;
import com.freetech.sample.securityqueryservice.infraestructure.ports.in.QueryEntityTypePort;
import com.freetech.sample.securityqueryservice.infraestructure.ports.out.EntityRepository;
import enums.StateEnum;
import interfaces.UseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import pagination.ResultQuery;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@UseCase
public class QueryEntityTypeUseCase implements QueryEntityTypePort {
    private final EntityRepository entityRepository;

    @Override
    public EntityType getById(Integer id) {
        List<Object> parameters = new ArrayList<>();
        parameters.add(StateEnum.ACTIVE.getValue());
        parameters.add(id);

        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT et.id, et.name ");
        sql.append(" FROM entity_types et ");
        sql.append(" WHERE et.id IS NOT NULL ");
        sql.append(" AND et.log_state = ?");
        sql.append(" AND et.id = ? ");

        try {
            var result = entityRepository.getByQuery(sql.toString(), parameters.toArray(Object[]::new), EntityType.class);
            return result.size() > 0 ? result.get(0) : null;
        } catch (Exception ex) {
            throw new BussinessException(
                    ExceptionEnum.ERROR_GET_BY_ID_ENTITY_TYPE.getCode(),
                    ExceptionEnum.ERROR_GET_BY_ID_ENTITY_TYPE.getMessage(),
                    ex.getMessage() + " --> " + ex.getCause().getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResultQuery<EntityType> getAll(GetAllEntityTypeQuery getAllEntityTypeQuery) {
        List<Object> parameters = new ArrayList<>();

        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT et.id, et.name ");
        buildSqlGetAll(sql, getAllEntityTypeQuery, parameters);

        if (getAllEntityTypeQuery.getPageSize() != null
                && getAllEntityTypeQuery.getPageNumber() != null) {
            sql.append(" LIMIT ").append(""+getAllEntityTypeQuery.getPageSize());
            sql.append(" OFFSET ").append(""+(getAllEntityTypeQuery.getPageNumber()-1));
        }

        try {
            var total = countGetAll(getAllEntityTypeQuery);
            var results = entityRepository.getByQuery(sql.toString(), parameters.toArray(Object[]::new), EntityType.class);
            return new ResultQuery<>(total, getAllEntityTypeQuery.getPageNumber(), getAllEntityTypeQuery.getPageSize(), results);
        } catch (Exception ex) {
            throw new BussinessException(
                    ExceptionEnum.ERROR_GET_ALL_ENTITY_TYPE.getCode(),
                    ExceptionEnum.ERROR_GET_ALL_ENTITY_TYPE.getMessage(),
                    ex.getMessage() + " --> " + ex.getCause().getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private int countGetAll(GetAllEntityTypeQuery getAllEntityTypeQuery) {
        List<Object> parameters = new ArrayList<>();

        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT COUNT(et.id) ");
        buildSqlGetAll(sql, getAllEntityTypeQuery, parameters);

        return entityRepository.countByQuery(sql.toString(), parameters.toArray(Object[]::new));
    }

    private void buildSqlGetAll(StringBuilder sql, GetAllEntityTypeQuery getAllEntityTypeQuery, List<Object> parameters) {
        parameters.add(StateEnum.ACTIVE.getValue());

        sql.append(" FROM entity_types et ");
        sql.append(" WHERE et.id IS NOT NULL ");
        sql.append(" AND et.log_state = ? ");

        if (getAllEntityTypeQuery.getName() != null) {
            sql.append(" AND et.name = ?");
            parameters.add(getAllEntityTypeQuery.getName());
        }
    }
}
