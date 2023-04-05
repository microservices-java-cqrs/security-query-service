package com.freetech.sample.securityqueryservice.infraestructure.adapters.in.http.mappers;

import com.freetech.sample.securityqueryservice.domain.User;
import com.freetech.sample.securityqueryservice.infraestructure.adapters.in.http.dtos.UserDto;
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
                    ((UserDto) dto).setId(user.getId());
                    ((UserDto) dto).setEntityNumberDocument(user.getEntityNumberDocument());
                    ((UserDto) dto).setEntityBussinessName(user.getEntityBussinessName());
                    ((UserDto) dto).setEntityName(user.getEntityName());
                    ((UserDto) dto).setEntityLastname(user.getEntityLastname());
                    ((UserDto) dto).setUsername(user.getUsername());
                    ((UserDto) dto).setStatus(user.getStatus());
                }

                return dto;
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        };
    }
}