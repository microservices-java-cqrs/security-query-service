package com.freetech.sample.securityqueryservice.application.mappers;

import com.freetech.sample.securityqueryservice.infraestructure.adapters.out.documents.RolDocument;
import com.freetech.sample.securityqueryservice.infraestructure.adapters.out.documents.UserDocument;
import com.freetech.sample.securityqueryservice.infraestructure.adapters.out.documents.UserRolDocument;
import messages.UserRolMessage;

import java.util.function.Function;
import java.util.stream.Stream;

public class UserRolDocumentMapper {
    /*public static UserRolDocument toDocument(UserRol userRol) {
        if (userRol == null) return null;
        return Stream.of(userRol).map(toDocument()).findFirst().get();
    }*/

    public static UserRolDocument toDocument(UserRolMessage userRolMessage) {
        if (userRolMessage == null) return null;
        return Stream.of(userRolMessage).map(toDocument()).findFirst().get();
    }

    private static Function<UserRolMessage, UserRolDocument> toDocument() {
        return userRolMessage -> {
            var userRolDocument = new UserRolDocument();
            userRolDocument.setId(userRolMessage.getId());
            userRolDocument.setUserDocument(new UserDocument());
            userRolDocument.getUserDocument().setId(userRolMessage.getUserId());
            userRolDocument.setRolDocument(new RolDocument());
            userRolDocument.getRolDocument().setId(userRolMessage.getRolId());
            return userRolDocument;
        };
    }}
