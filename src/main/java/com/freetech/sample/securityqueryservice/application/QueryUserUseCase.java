package com.freetech.sample.securityqueryservice.application;

import com.freetech.sample.securityqueryservice.application.enums.ExceptionEnum;
import com.freetech.sample.securityqueryservice.application.exceptions.BussinessException;
import com.freetech.sample.securityqueryservice.application.queries.GetAllUserQuery;
import com.freetech.sample.securityqueryservice.domain.User;
import com.freetech.sample.securityqueryservice.infraestructure.ports.in.QueryUserPort;
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
public class QueryUserUseCase implements QueryUserPort {
    private final EntityRepository entityRepository;

    @Override
    public User getById(Integer id) {
        List<Object> parameters = new ArrayList<>();
        parameters.add(StateEnum.ACTIVE.getValue());
        parameters.add(StateEnum.ACTIVE.getValue());
        parameters.add(id);

        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT u.id, u.username, u.entity_name, ");
        sql.append(" u.entity_number_document, u.entity_bussiness_name, ");
        sql.append(" u.entity_lastname, u.status ");
        sql.append(" FROM users u ");
        sql.append(" LEFT JOIN entity_types et ");
        sql.append(" ON et.id = u.entity_type_id ");
        sql.append(" AND et.log_state = ? ");
        sql.append(" WHERE u.id IS NOT NULL ");
        sql.append(" AND u.log_state = ?");
        sql.append(" AND u.id = ? ");

        try {
            var result = entityRepository.getByQuery(sql.toString(), parameters.toArray(Object[]::new), User.class);
            return result.size() > 0 ? result.get(0) : null;
        } catch (Exception ex) {
            throw new BussinessException(
                    ExceptionEnum.ERROR_GET_BY_ID_USER.getCode(),
                    ExceptionEnum.ERROR_GET_BY_ID_USER.getMessage(),
                    ex.getMessage() + " --> " + ex.getCause().getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResultQuery<User> getAll(GetAllUserQuery getAllUserQuery) {
        List<Object> parameters = new ArrayList<>();

        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT u.id, u.username, u.entity_name, ");
        sql.append(" u.entity_number_document, u.entity_bussiness_name, ");
        sql.append(" u.entity_lastname, u.status ");
        buildSqlGetAll(sql, getAllUserQuery, parameters);

        if (getAllUserQuery.getPageSize() != null
                && getAllUserQuery.getPageNumber() != null) {
            sql.append(" LIMIT ").append(""+getAllUserQuery.getPageSize());
            sql.append(" OFFSET ").append(""+(getAllUserQuery.getPageNumber()-1));
        }

        try {
            var total = countGetAll(getAllUserQuery);
            var results = entityRepository.getByQuery(sql.toString(), parameters.toArray(Object[]::new), User.class);
            return new ResultQuery<>(total, getAllUserQuery.getPageNumber(), getAllUserQuery.getPageSize(), results);
        } catch (Exception ex) {
            throw new BussinessException(
                    ExceptionEnum.ERROR_GET_ALL_USER.getCode(),
                    ExceptionEnum.ERROR_GET_ALL_USER.getMessage(),
                    ex.getMessage() + " --> " + ex.getCause().getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private int countGetAll(GetAllUserQuery getAllUserQuery) {
        List<Object> parameters = new ArrayList<>();

        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT COUNT(u.id) ");
        buildSqlGetAll(sql, getAllUserQuery, parameters);

        return entityRepository.countByQuery(sql.toString(), parameters.toArray(Object[]::new));
    }

    private void buildSqlGetAll(StringBuilder sql, GetAllUserQuery getAllUserQuery, List<Object> parameters) {
        parameters.add(StateEnum.ACTIVE.getValue());
        parameters.add(StateEnum.ACTIVE.getValue());

        sql.append(" FROM users u ");
        sql.append(" LEFT JOIN entity_types et ");
        sql.append(" ON et.id = u.entity_type_id ");
        sql.append(" AND et.log_state = ? ");
        sql.append(" WHERE u.id IS NOT NULL ");
        sql.append(" AND u.log_state = ?");

        if (getAllUserQuery.getEntityTypeId() != null) {
            sql.append(" AND u.entity_type_id = ?");
            parameters.add(getAllUserQuery.getEntityTypeId());
        }

        if (getAllUserQuery.getUsername() != null) {
            sql.append(" AND u.username = ?");
            parameters.add(getAllUserQuery.getUsername());
        }

        if (getAllUserQuery.getEntityName() != null) {
            sql.append(" AND u.entity_name = ?");
            parameters.add(getAllUserQuery.getEntityName());
        }

        if (getAllUserQuery.getEntityLastname() != null) {
            sql.append(" AND u.entity_lastname = ?");
            parameters.add(getAllUserQuery.getEntityLastname());
        }

        if (getAllUserQuery.getStatus() != null) {
            sql.append(" AND u.status = ?");
            parameters.add(getAllUserQuery.getStatus());
        }
    }
}
