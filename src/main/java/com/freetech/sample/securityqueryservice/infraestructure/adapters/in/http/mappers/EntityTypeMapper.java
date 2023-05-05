package com.freetech.sample.securityqueryservice.infraestructure.adapters.in.http.mappers;

import com.freetech.sample.securityqueryservice.domain.model.EntityType;
import com.freetech.sample.securityqueryservice.infraestructure.adapters.in.http.dtos.EntityTypeDto;
import pagination.ResultQuery;

import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class EntityTypeMapper {

    public static <T> T toDto(EntityType entityType, Class<T> clazz) {
        if (entityType == null) return null;
        return (T) Stream.of(entityType).map(toDto(clazz)).findFirst().get();
    }

    public static <T> ResultQuery<T> toDto(ResultQuery<EntityType> results, Class<T> clazz) {
        if (results == null) return null;
        return new ResultQuery<>(results.getTotal(), results.getPageNumber(), results.getPageSize(),
                results.getItems().stream().map(toDto(clazz)).collect(Collectors.toList()));
    }

    private static <T> Function<EntityType, T> toDto(Class<T> clazz) {
        return entityType -> {
            try {
                var dto = clazz.getDeclaredConstructor().newInstance();
                if (dto instanceof EntityTypeDto) {
                    var objDto = ((EntityTypeDto) dto);
                    objDto.setId(entityType.getId());
                    objDto.setName(entityType.getName());
                    objDto.setDescription(entityType.getDescription());
                }

                return dto;
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        };
    }

}
