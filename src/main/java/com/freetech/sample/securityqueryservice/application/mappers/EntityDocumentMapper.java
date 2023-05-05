package com.freetech.sample.securityqueryservice.application.mappers;

import com.freetech.sample.securityqueryservice.domain.model.Entity;
import com.freetech.sample.securityqueryservice.infraestructure.adapters.out.documents.EntityDocument;
import messages.EntityMessage;

import java.util.function.Function;
import java.util.stream.Stream;

public class EntityDocumentMapper {
    /*public static EntityDocument toDocument(Entity entity) {
        if (entity == null) return null;
        return Stream.of(entity).map(toDocument()).findFirst().get();
    }*/

    public static EntityDocument toDocument(EntityMessage entity) {
        if (entity == null) return null;
        return Stream.of(entity).map(toDocument()).findFirst().get();
    }

    public static Entity toDomain(EntityDocument entityDocument) {
        if (entityDocument == null) return null;
        return Stream.of(entityDocument).map(toDomain()).findFirst().get();
    }

    private static Function<EntityMessage, EntityDocument> toDocument() {
        return entityMessage -> {
            var entityDocument = new EntityDocument();
            entityDocument.setId(entityMessage.getId());
            entityDocument.setParentId(entityMessage.getParentId());
            entityDocument.setNumberDocument(entityMessage.getNumberDocument());
            entityDocument.setBussinessName(entityMessage.getBussinessName());
            entityDocument.setName(entityMessage.getName());
            entityDocument.setLastname(entityMessage.getLastname());
            entityDocument.setLogCreationUser(entityMessage.getLogCreationUser());
            entityDocument.setLogUpdateUser(entityMessage.getLogUpdateUser());
            entityDocument.setLogCreationDate(entityMessage.getLogCreationDate());
            entityDocument.setLogUpdateDate(entityMessage.getLogUpdateDate());
            entityDocument.setLogState(entityMessage.getLogState());
            return entityDocument;
        };
    }

    private static Function<EntityDocument, Entity> toDomain() {
        return entityDocument -> {
            var entity = new Entity();
            return entity;
        };
    }
}
