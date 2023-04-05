package com.freetech.sample.securityqueryservice.infraestructure.adapters.in.http.controllers;

import com.freetech.sample.securityqueryservice.application.queries.GetAllEntityTypeQuery;
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
    public ResponseEntity<EntityTypeDto> getEntityType(@PathVariable("id") Integer id) {
        return new ResponseEntity<>(EntityTypeMapper.toDto(queryEntityTypePort.getById(id), EntityTypeDto.class), HttpStatus.OK);
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<ResultQuery<EntityTypeDto>> getAll(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Integer pageNumber,
            @RequestParam(required = false) Integer pageSize) {
        var getAllEntityTypeQuery = new GetAllEntityTypeQuery();
        getAllEntityTypeQuery.setName(name);
        getAllEntityTypeQuery.setPageNumber(pageNumber);
        getAllEntityTypeQuery.setPageSize(pageSize);
        return new ResponseEntity<>(EntityTypeMapper.toDto(queryEntityTypePort.getAll(getAllEntityTypeQuery), EntityTypeDto.class), HttpStatus.OK);
    }
}
