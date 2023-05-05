package com.freetech.sample.securityqueryservice.application.enums;

import lombok.Getter;

@Getter
public enum ExceptionEnum {
    ERROR_CREATE("50100", "Error creating "),
    ERROR_UPDATE("50101", "Error updating "),
    ERROR_DELETE("50102", "Error deleting "),

    ERROR_GET_BY_ID_USER("50120", "Error getting user by id"),
    ERROR_GET_ALL_USER("50121", "Error getting users"),
    ERROR_GET_BY_ID_USER_WITH_ROLES("50122", "Error getting user by id with roles"),
    ERROR_NOT_FOUND_USER("50122", "User not found"),

    ERROR_GET_BY_ID_ENTITY_TYPE("50140", "Error getting entity type by id"),
    ERROR_GET_ALL_ENTITY_TYPE("50141", "Error getting entity types");

    private String code;
    private String message;

    ExceptionEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
