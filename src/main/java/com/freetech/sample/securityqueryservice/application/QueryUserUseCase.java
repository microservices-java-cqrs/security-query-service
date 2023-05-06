package com.freetech.sample.securityqueryservice.application;

import com.freetech.sample.securityqueryservice.application.enums.ExceptionEnum;
import com.freetech.sample.securityqueryservice.application.exceptions.BussinessException;
import com.freetech.sample.securityqueryservice.application.mappers.RolDocumentMapper;
import com.freetech.sample.securityqueryservice.application.mappers.UserDocumentMapper;
import com.freetech.sample.securityqueryservice.domain.filters.UserFilter;
import com.freetech.sample.securityqueryservice.domain.model.User;
import com.freetech.sample.securityqueryservice.infraestructure.adapters.out.repositories.RolRepository;
import com.freetech.sample.securityqueryservice.infraestructure.adapters.out.repositories.UserRepository;
import com.freetech.sample.securityqueryservice.infraestructure.ports.in.QueryUserPort;
import interfaces.UseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import pagination.ResultQuery;

@RequiredArgsConstructor
@UseCase
public class QueryUserUseCase implements QueryUserPort {
    private final RolRepository rolRepository;
    private final UserRepository userRepository;

    @Override
    public User getById(Long id) {
        var userDocument = userRepository.getById(id);
        if (userDocument == null)
            throw new BussinessException(
                    ExceptionEnum.ERROR_NOT_FOUND_USER.getCode(),
                    ExceptionEnum.ERROR_NOT_FOUND_USER.getMessage(),
                    ExceptionEnum.ERROR_NOT_FOUND_USER.getMessage(),
                    HttpStatus.NOT_FOUND);

        try {
            return UserDocumentMapper.toDomain(userDocument);
        } catch (Exception ex) {
            throw new BussinessException(
                    ExceptionEnum.ERROR_GET_BY_ID_USER.getCode(),
                    ExceptionEnum.ERROR_GET_BY_ID_USER.getMessage(),
                    ex.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResultQuery<User> getAll(UserFilter userFilter) {
        try {
            var users = userRepository.getAllPageable(userFilter);
            var total = userRepository.countGetAllPageable(userFilter);
            return new ResultQuery<>(
                    total,
                    userFilter.getPageNumber(),
                    userFilter.getPageSize(),
                    UserDocumentMapper.toDomain(users));
        } catch (Exception ex) {
            throw new BussinessException(
                    ExceptionEnum.ERROR_GET_ALL_USER.getCode(),
                    ExceptionEnum.ERROR_GET_ALL_USER.getMessage(),
                    ex.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public User getByIdWithRoles(Long id) {
        var userDocument = userRepository.getById(id);
        if (userDocument == null)
            throw new BussinessException(
                    ExceptionEnum.ERROR_NOT_FOUND_USER.getCode(),
                    ExceptionEnum.ERROR_NOT_FOUND_USER.getMessage(),
                    ExceptionEnum.ERROR_NOT_FOUND_USER.getMessage(),
                    HttpStatus.NOT_FOUND);
        try {
            var user = UserDocumentMapper.toDomain(userDocument);
            user.setRoles(RolDocumentMapper.toDomain(rolRepository.getAllRolesByUser(id)));
            return user;
        } catch (Exception ex) {
            throw new BussinessException(
                    ExceptionEnum.ERROR_GET_BY_ID_USER_WITH_ROLES.getCode(),
                    ExceptionEnum.ERROR_GET_BY_ID_USER_WITH_ROLES.getMessage(),
                    ex.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
