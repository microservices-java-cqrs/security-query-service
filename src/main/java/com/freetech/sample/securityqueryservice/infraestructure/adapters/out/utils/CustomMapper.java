package com.freetech.sample.securityqueryservice.infraestructure.adapters.out.utils;

import org.springframework.jdbc.core.RowMapper;
import utils.ConvertUtil;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CustomMapper<T> implements RowMapper<T> {
    private Class<?> clazz = null;

    public CustomMapper( Class<?> clazz ) {
        this.clazz = clazz;
    }

    @Override
    public T mapRow(ResultSet rs, int rowNum) throws SQLException {

        try {
            Method builderMethod = clazz.getMethod("builder");
            if ( builderMethod == null ) return null;

            Object row = builderMethod.invoke(null);
            Method[] m = row.getClass().getDeclaredMethods();

            for ( int i=0; i<m.length; i++ ) {
                int pos = -1;
                try {
                    pos = rs.findColumn(ConvertUtil.convertCamelToUnder(m[i].getName()));
                } catch ( SQLException ex ) {  }

                if ( pos != -1 ) {
                    Object fieldValue = rs.getObject( pos );

                    m[i].invoke( row, fieldValue );
                }
            }

            return (T) row.getClass().getMethod("build").invoke(row);
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException
                 | NoSuchMethodException | SecurityException e) {
            e.printStackTrace();
        }

        return null;
    }

}