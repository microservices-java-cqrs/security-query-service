package com.freetech.sample.securityqueryservice.infraestructure.adapters.out;

import com.freetech.sample.securityqueryservice.infraestructure.adapters.out.utils.CustomMapper;
import com.freetech.sample.securityqueryservice.infraestructure.ports.out.EntityRepository;
import interfaces.PersistenceAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import utils.ConvertUtil;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@PersistenceAdapter
public class PostgresRepository implements EntityRepository {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public <T> T save(String tableName, T entity) {
        Field[] entityDeclaredFields = entity.getClass().getDeclaredFields();
        var entityLabelFields =
                Arrays.stream(entityDeclaredFields)
                        .map(x -> ConvertUtil.convertCamelToUnder(x.getName()))
                        .collect(Collectors.toList());
        var fields = String.join(", ", entityLabelFields);

        Object[] fieldValues = new Object[ entityDeclaredFields.length ];

        try {
            for ( int i=0; i<entityDeclaredFields.length; i++ ) {
                fieldValues[i] = entity.getClass()
                        .getMethod( "get"+entityDeclaredFields[i].getName().substring(0,1).toUpperCase()+entityDeclaredFields[i].getName().substring(1) )
                        .invoke( entity );
            }
        } catch(Exception ex) {
            System.out.println(ex.getMessage());
        }

        StringBuilder sql = new StringBuilder();
        sql.append("INSERT INTO ")
                .append( tableName )
                .append( "(" )
                .append( fields )
                .append( ")" )
                .append( " VALUES " )
                .append( "(" )
                .append( String.join( ", ", Collections.nCopies( entityDeclaredFields.length, "?") ) )
                .append( ")" );

        jdbcTemplate.update(sql.toString(), fieldValues);
        return entity;
    }

    @Override
    public <T> T update(String tableName, T entity) {
        Field[] entityDeclaredFields = entity.getClass().getDeclaredFields();

        var fieldId = Arrays.stream(entityDeclaredFields).filter(x -> x.getName() == "id").findFirst().get();
        Object id = new Object();
        try {
            id = entity.getClass()
                    .getMethod( "get"+fieldId.getName().substring(0,1).toUpperCase() + fieldId.getName().substring(1) )
                    .invoke( entity );
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

        var filterNoIdEntityDeclaredFields = Arrays.stream(entityDeclaredFields)
                .filter(x -> x.getName() != "id")
                .filter(x -> {
                    try {
                        var value = entity.getClass()
                                .getMethod( "get"+x.getName().substring(0,1).toUpperCase() + x.getName().substring(1) )
                                .invoke( entity );
                        return value != null;
                    } catch (Exception ex) {
                        return false;
                    }
                })
                .toArray(Field[]::new);

        var entityLabelFields = Arrays.stream(filterNoIdEntityDeclaredFields)
                .map(x -> ConvertUtil.convertCamelToUnder(x.getName()) + " = ?")
                .collect(Collectors.toList());

        var fieldValues = getValueFields(filterNoIdEntityDeclaredFields, entity);
        fieldValues.add(id);

        StringBuilder sql = new StringBuilder();
        sql.append( " UPDATE " )
            .append( tableName )
            .append( " SET " )
            .append( String.join(", ", entityLabelFields) )
            .append( " WHERE " )
            .append( " id = ? " );

        jdbcTemplate.update(sql.toString(), fieldValues.stream().toArray(Object[]::new));
        return entity;
    }

    @Override
    public <T> List<T> getByQuery(String sql, Object[] parameters, Class<T> clazz) {
        return jdbcTemplate.query(sql, new CustomMapper<T>(clazz), parameters);
    }

    @Override
    public Integer countByQuery(String sql, Object[] parameters) {
        return jdbcTemplate.queryForObject(sql, parameters, (rs, rowNum) -> rs.getInt(1));
    }

    private static <T> List<Object> getValueFields(Field[] fields, T entity) {
        var fieldValues = new ArrayList<>();

        try {
            for ( int i=0; i<fields.length; i++ ) {
                fieldValues.add(entity.getClass()
                        .getMethod( "get"+fields[i].getName().substring(0,1).toUpperCase()+fields[i].getName().substring(1) )
                        .invoke( entity ));
            }
        } catch(Exception ex) {
            System.out.println(ex.getMessage());
        }
        return fieldValues;
    }
}
