package com.freetech.sample.securityqueryservice.application;

import com.freetech.sample.securityqueryservice.application.enums.ExceptionEnum;
import com.freetech.sample.securityqueryservice.application.exceptions.BussinessException;
import com.freetech.sample.securityqueryservice.infraestructure.ports.in.MessageBrokerPort;
import com.freetech.sample.securityqueryservice.infraestructure.ports.out.EntityRepository;
import interfaces.UseCase;
//import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
@UseCase
public class MessageBrokerUseCase implements MessageBrokerPort {
    private final EntityRepository entityRepository;

    @Override
    public <T> T create(String tableName, T entity) {
        try {
            return entityRepository.save(tableName, entity);
        } catch (Exception ex) {
            throw new BussinessException(
                    ExceptionEnum.ERROR_CREATE.getCode(),
                    ExceptionEnum.ERROR_CREATE.getMessage() + tableName,
                    ex.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }

    @Override
    public <T> T update(String tableName, T entity) {
        try {
            return entityRepository.update(tableName, entity);
        } catch(Exception ex) {
            throw new BussinessException(
                    ExceptionEnum.ERROR_UPDATE.getCode(),
                    ExceptionEnum.ERROR_UPDATE.getMessage() + tableName,
                    ex.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }

    @Override
    public <T> T delete(String tableName, T entity) {
        try {
            return entityRepository.update(tableName, entity);
        } catch(Exception ex) {
            throw new BussinessException(
                    ExceptionEnum.ERROR_DELETE.getCode(),
                    ExceptionEnum.ERROR_DELETE.getMessage() + tableName,
                    ex.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }
}
