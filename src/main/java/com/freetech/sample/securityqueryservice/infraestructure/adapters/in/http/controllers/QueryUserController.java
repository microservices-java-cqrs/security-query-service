package com.freetech.sample.securityqueryservice.infraestructure.adapters.in.http.controllers;

import com.freetech.sample.securityqueryservice.application.queries.GetAllUserQuery;
import com.freetech.sample.securityqueryservice.infraestructure.adapters.in.http.dtos.UserDto;
import com.freetech.sample.securityqueryservice.infraestructure.adapters.in.http.mappers.UserMapper;
import com.freetech.sample.securityqueryservice.infraestructure.ports.in.QueryUserPort;
import interfaces.HttpAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pagination.ResultQuery;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "v1/qry/users")
@HttpAdapter
public class QueryUserController {
    private final QueryUserPort queryUserPort;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<UserDto> getUser(@PathVariable("id") Integer id) {
        return new ResponseEntity<>(UserMapper.toDto(queryUserPort.getById(id), UserDto.class), HttpStatus.OK);
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<ResultQuery<UserDto>> getAll(
            @RequestParam(required = false) Integer entityTypeId,
            @RequestParam(required = false) String username,
            @RequestParam(required = false) String entityName,
            @RequestParam(required = false) String entityLastname,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) Integer pageNumber,
            @RequestParam(required = false) Integer pageSize) {
        var getAllUserQuery = new GetAllUserQuery();
        getAllUserQuery.setEntityTypeId(entityTypeId);
        getAllUserQuery.setUsername(username);
        getAllUserQuery.setEntityLastname(entityName);
        getAllUserQuery.setEntityLastname(entityLastname);
        getAllUserQuery.setStatus(status);
        getAllUserQuery.setPageNumber(pageNumber);
        getAllUserQuery.setPageSize(pageSize);
        return new ResponseEntity<>(UserMapper.toDto(queryUserPort.getAll(getAllUserQuery), UserDto.class), HttpStatus.OK);
    }
}
