package com.freetech.sample.securityqueryservice.infraestructure.adapters.in.http.controllers;

import com.freetech.sample.securityqueryservice.domain.filters.UserFilter;
import com.freetech.sample.securityqueryservice.infraestructure.adapters.in.http.dtos.UserDto;
import com.freetech.sample.securityqueryservice.infraestructure.adapters.in.http.dtos.UserWithRolesDto;
import com.freetech.sample.securityqueryservice.infraestructure.adapters.in.http.mappers.UserMapper;
import com.freetech.sample.securityqueryservice.infraestructure.ports.in.QueryUserPort;
import interfaces.HttpAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pagination.ResultQuery;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "v1/qry/users")
@HttpAdapter
public class QueryUserController {
    private final QueryUserPort queryUserPort;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<UserDto> getById(@PathVariable("id") Long id) {
        return new ResponseEntity<>(UserMapper.toDto(queryUserPort.getById(id), UserDto.class), HttpStatus.OK);
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<ResultQuery<UserDto>> getAll(
            @RequestParam(required = false) Long id,
            @RequestParam(required = false) Long entityTypeId,
            @RequestParam(required = false) String username,
            @RequestParam(required = false) String entityName,
            @RequestParam(required = false) String entityLastname,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) Integer pageNumber,
            @RequestParam(required = false) Integer pageSize) {
        var userFilter = new UserFilter();
        userFilter.setId(id);
        userFilter.setEntityTypeId(entityTypeId);
        userFilter.setUsername(username);
        userFilter.setEntityName(entityName);
        userFilter.setEntityLastname(entityLastname);
        userFilter.setStatus(status);
        userFilter.setPageNumber(pageNumber);
        userFilter.setPageSize(pageSize);
        return new ResponseEntity<>(UserMapper.toDto(queryUserPort.getAll(userFilter), UserDto.class), HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}/roles", method = RequestMethod.GET)
    public ResponseEntity<UserWithRolesDto> getByIdWithRoles(
            @PathVariable(required = false) Long id) {
        return new ResponseEntity<>(UserMapper.toDto(queryUserPort.getByIdWithRoles(id), UserWithRolesDto.class), HttpStatus.OK);
    }
}
