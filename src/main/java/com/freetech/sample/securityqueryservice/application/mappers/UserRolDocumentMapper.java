package com.freetech.sample.securityqueryservice.application.mappers;

import com.freetech.sample.securityqueryservice.domain.model.UserRol;
import com.freetech.sample.securityqueryservice.infraestructure.adapters.out.documents.RolDocument;
import com.freetech.sample.securityqueryservice.infraestructure.adapters.out.documents.UserDocument;
import com.freetech.sample.securityqueryservice.infraestructure.adapters.out.documents.UserRolDocument;

import java.util.function.Function;
import java.util.stream.Stream;

public class UserRolDocumentMapper {
    public static UserRolDocument toDocument(UserRol userRol) {
        if (userRol == null) return null;
        return Stream.of(userRol).map(toDocument()).findFirst().get();
    }

    private static Function<UserRol, UserRolDocument> toDocument() {
        return userRol -> {
            var userRolDocument = new UserRolDocument();
            userRolDocument.setId(userRol.getId());
            userRolDocument.setUserDocument(new UserDocument());
            userRolDocument.getUserDocument().setId(userRol.getUserId());
            userRolDocument.setRolDocument(new RolDocument());
            userRolDocument.getRolDocument().setId(userRol.getRolId());
            return userRolDocument;
        };
    }}
