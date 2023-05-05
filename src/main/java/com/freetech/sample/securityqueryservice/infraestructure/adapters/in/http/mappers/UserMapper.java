package com.freetech.sample.securityqueryservice.infraestructure.adapters.in.http.mappers;

import com.freetech.sample.securityqueryservice.domain.model.User;
import com.freetech.sample.securityqueryservice.infraestructure.adapters.in.http.dtos.*;
import pagination.ResultQuery;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class UserMapper {

    public static <T> T toDto(User user, Class<T> clazz) {
        if (user == null) return null;
        return (T) Stream.of(user).map(toDto(clazz)).findFirst().get();
    }

    public static <T> List<T> toDto(List<User> sources, Class<T> clazz) {
        if (sources == null) return null;
        return sources.stream().map(toDto(clazz)).collect(Collectors.toList());
    }

    public static <T> ResultQuery<T> toDto(ResultQuery<User> results, Class<T> clazz) {
        if (results == null) return null;
        return new ResultQuery<>(results.getTotal(), results.getPageNumber(), results.getPageSize(),
                        results.getItems().stream().map(toDto(clazz)).collect(Collectors.toList()));
    }

    private static <T> Function<User, T> toDto(Class<T> clazz) {
        return user -> {
            try {
                var dto = clazz.getDeclaredConstructor().newInstance();
                if (dto instanceof UserDto) {
                    var objDto = ((UserDto) dto);
                    objDto.setId(user.getId());
                    objDto.setUsername(user.getUsername());
                    objDto.setStatus(user.getStatus());
                    if (user.getEntity() != null) {
                        objDto.setEntity(new EntityDto());
                        objDto.getEntity().setId(user.getEntity().getId());
                        objDto.getEntity().setNumberDocument(user.getEntity().getNumberDocument());
                        objDto.getEntity().setBussinessName(user.getEntity().getBussinessName());
                        objDto.getEntity().setName(user.getEntity().getName());
                        objDto.getEntity().setLastname(user.getEntity().getLastname());

                        if (user.getEntity().getEntityType() != null) {
                            objDto.getEntity().setEntityType(new EntityTypeDto());
                            objDto.getEntity().getEntityType().setId(user.getEntity().getEntityType().getId());
                            objDto.getEntity().getEntityType().setName(user.getEntity().getEntityType().getName());
                        }
                    }
                } else if (dto instanceof UserWithRolesDto) {
                    ((UserWithRolesDto) dto).setId(user.getId());
                    ((UserWithRolesDto) dto).setUsername(user.getUsername());
                    if (user.getRoles() != null) {
                        ((UserWithRolesDto) dto).setRoles(RolMapper.toDto(user.getRoles(), RolDto.class));
                    }
                }

                return dto;
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        };
    }
}
