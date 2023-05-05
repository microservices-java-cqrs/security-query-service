package com.freetech.sample.securityqueryservice.application.mappers;

import com.freetech.sample.securityqueryservice.domain.model.Entity;
import com.freetech.sample.securityqueryservice.domain.model.EntityType;
import com.freetech.sample.securityqueryservice.domain.model.User;
import com.freetech.sample.securityqueryservice.infraestructure.adapters.out.documents.EntityTypeDocument;
import com.freetech.sample.securityqueryservice.infraestructure.adapters.out.documents.UserDocument;
import messages.UserMessage;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class UserDocumentMapper {
    public static UserDocument toDocument(UserMessage user) {
        if (user == null) return null;
        return Stream.of(user).map(toDocument()).findFirst().get();
    }

    public static User toDomain(UserDocument userDocument) {
        if (userDocument == null) return null;
        return Stream.of(userDocument).map(toDomain()).findFirst().get();
    }

    public static List<User> toDomain(List<UserDocument> users) {
        if (users == null) return null;
        return users.stream().map(toDomain()).collect(Collectors.toList());
    }

    private static Function<UserMessage, UserDocument> toDocument() {
        return user -> {
            var userDocument = new UserDocument();
            userDocument.setId( user.getId() );
            userDocument.setUsername(user.getUsername());
            userDocument.setPassword(user.getPassword());
            userDocument.setStatus(user.getStatus());
            userDocument.setLogCreationUser(user.getLogCreationUser());
            userDocument.setLogUpdateUser(user.getLogUpdateUser());
            userDocument.setLogCreationDate(user.getLogCreationDate());
            userDocument.setLogUpdateDate(user.getLogUpdateDate());
            userDocument.setLogState(user.getLogState());

            return userDocument;
        };
    }

    private static Function<UserDocument, User> toDomain() {
        return userDocument -> {
            var user = new User();
            user.setId(userDocument.getId());
            user.setUsername(userDocument.getUsername());
            user.setPassword(userDocument.getPassword());
            user.setStatus(userDocument.getStatus());

            if (userDocument.getEntityDocument() != null) {
                user.setEntity(new Entity());
                user.getEntity().setId(userDocument.getEntityDocument().getId());
                user.getEntity().setNumberDocument(userDocument.getEntityDocument().getNumberDocument());
                user.getEntity().setBussinessName(userDocument.getEntityDocument().getBussinessName());
                user.getEntity().setName(userDocument.getEntityDocument().getName());
                user.getEntity().setLastname(userDocument.getEntityDocument().getLastname());

                try {
                    if (userDocument.getEntityDocument().getEntityTypeDocument() != null) {
                        user.getEntity().setEntityType(new EntityType());
                        user.getEntity().getEntityType().setId(userDocument.getEntityDocument().getEntityTypeDocument().getId());
                        user.getEntity().getEntityType().setName(userDocument.getEntityDocument().getEntityTypeDocument().getName());
                    }
                } catch(Exception ex) {
                    user.getEntity().setEntityType(null);
                }

            }

            return user;
        };
    }
}
