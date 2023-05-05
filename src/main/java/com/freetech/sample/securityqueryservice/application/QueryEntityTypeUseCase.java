package com.freetech.sample.securityqueryservice.application;

import com.freetech.sample.securityqueryservice.application.enums.ExceptionEnum;
import com.freetech.sample.securityqueryservice.application.exceptions.BussinessException;
import com.freetech.sample.securityqueryservice.application.mappers.EntityTypeDocumentMapper;
import com.freetech.sample.securityqueryservice.domain.filters.EntityTypeFilter;
import com.freetech.sample.securityqueryservice.domain.model.EntityType;
import com.freetech.sample.securityqueryservice.infraestructure.adapters.out.repositories.EntityTypeRepository;
import com.freetech.sample.securityqueryservice.infraestructure.ports.in.QueryEntityTypePort;
import interfaces.UseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import pagination.ResultQuery;

@RequiredArgsConstructor
@UseCase
public class QueryEntityTypeUseCase implements QueryEntityTypePort {
    private final EntityTypeRepository entityTypeRepository;

    @Override
    public EntityType getById(Long id) {
        try {
            var entityTypeDocument = entityTypeRepository.getById(id);
            return EntityTypeDocumentMapper.toDomain(entityTypeDocument);
        } catch (Exception ex) {
            throw new BussinessException(
                    ExceptionEnum.ERROR_GET_BY_ID_ENTITY_TYPE.getCode(),
                    ExceptionEnum.ERROR_GET_BY_ID_ENTITY_TYPE.getMessage(),
                    ex.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResultQuery<EntityType> getAll(EntityTypeFilter entityTypeFilter) {
        try {
            var entityTypes = entityTypeRepository.getAllPageable(entityTypeFilter);
            var total = entityTypeRepository.countGetAllPageable(entityTypeFilter);
            return new ResultQuery<>(
                    total,
                    entityTypeFilter.getPageNumber(),
                    entityTypeFilter.getPageSize(),
                    EntityTypeDocumentMapper.toDomain(entityTypes));
        } catch (Exception ex) {
            throw new BussinessException(
                    ExceptionEnum.ERROR_GET_ALL_ENTITY_TYPE.getCode(),
                    ExceptionEnum.ERROR_GET_ALL_ENTITY_TYPE.getMessage(),
                    ex.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
