package com.freetech.sample.securityqueryservice.infraestructure.adapters.in.http.mappers;

import com.freetech.sample.securityqueryservice.domain.model.Rol;
import com.freetech.sample.securityqueryservice.infraestructure.adapters.in.http.dtos.RolDto;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class RolMapper {
    public static <T> List<T> toDto(List<Rol> sources, Class<T> clazz) {
        if (sources == null) return null;
        return sources.stream().map(toDto(clazz)).collect(Collectors.toList());
    }

    private static <T> Function<Rol, T> toDto(Class<T> clazz) {
        return rol -> {
            try {
                var dto = clazz.getDeclaredConstructor().newInstance();
                if (dto instanceof RolDto) {
                    ((RolDto) dto).setId(rol.getId());
                    ((RolDto) dto).setName(rol.getName());
                    ((RolDto) dto).setDescription(rol.getDescription());
                }
                return dto;
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        };
    }
}
