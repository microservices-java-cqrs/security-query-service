package com.freetech.sample.securityqueryservice.infraestructure.adapters.in.http.controllers;

import com.freetech.sample.securityqueryservice.domain.filters.EntityTypeFilter;
import com.freetech.sample.securityqueryservice.domain.filters.GetAllEntityTypeQuery;
import com.freetech.sample.securityqueryservice.infraestructure.adapters.in.http.dtos.EntityTypeDto;
import com.freetech.sample.securityqueryservice.infraestructure.adapters.in.http.mappers.EntityTypeMapper;
import com.freetech.sample.securityqueryservice.infraestructure.ports.in.QueryEntityTypePort;
import interfaces.HttpAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pagination.ResultQuery;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "v1/qry/entity-types")
@HttpAdapter
public class QueryEntityTypeController {
    private final QueryEntityTypePort queryEntityTypePort;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<EntityTypeDto> getEntityType(@PathVariable("id") Long id) {
        return new ResponseEntity<>(EntityTypeMapper.toDto(queryEntityTypePort.getById(id), EntityTypeDto.class), HttpStatus.OK);
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<ResultQuery<EntityTypeDto>> getAll(
            @RequestParam(required = false) Long id,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Integer pageNumber,
            @RequestParam(required = false) Integer pageSize) {
        var entityTypeFilter = new EntityTypeFilter();
        entityTypeFilter.setId(id);
        entityTypeFilter.setName(name);
        entityTypeFilter.setPageNumber(pageNumber);
        entityTypeFilter.setPageSize(pageSize);
        return new ResponseEntity<>(EntityTypeMapper.toDto(queryEntityTypePort.getAll(entityTypeFilter), EntityTypeDto.class), HttpStatus.OK);
    }
}
