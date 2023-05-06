package com.freetech.sample.securityqueryservice.application.mappers;

import com.freetech.sample.securityqueryservice.domain.model.EntityType;
import com.freetech.sample.securityqueryservice.infraestructure.adapters.out.documents.EntityTypeDocument;
import messages.EntityTypeMessage;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class EntityTypeDocumentMapper {
    public static EntityType toDomain(EntityTypeDocument entityTypeDocument) {
        if (entityTypeDocument == null) return null;
        return Stream.of(entityTypeDocument).map(toDomain()).findFirst().get();
    }

    public static EntityTypeDocument toDocument(EntityTypeMessage entityTypeMessage) {
        if (entityTypeMessage == null) return null;
        return Stream.of(entityTypeMessage).map(toDocument()).findFirst().get();
    }

    public static List<EntityType> toDomain(List<EntityTypeDocument> entityTypeDocuments) {
        if (entityTypeDocuments == null) return null;
        return entityTypeDocuments.stream().map(toDomain()).collect(Collectors.toList());
    }

    private static Function<EntityTypeDocument, EntityType> toDomain() {
        return entityTypeDocument -> {
            var entityType = new EntityType();
            entityType.setId(entityTypeDocument.getId());
            entityType.setName(entityTypeDocument.getName());
            entityType.setDescription(entityTypeDocument.getDescription());

            return entityType;
        };
    }

    private static Function<EntityTypeMessage, EntityTypeDocument> toDocument() {
        return entityTypeMessage -> {
            var entityTypeDocument = new EntityTypeDocument();
            entityTypeDocument.setId(entityTypeMessage.getId());
            entityTypeDocument.setName(entityTypeMessage.getName());
            entityTypeDocument.setDescription(entityTypeMessage.getDescription());
            entityTypeDocument.setLogCreationUser(entityTypeMessage.getLogCreationUser());
            entityTypeDocument.setLogUpdateUser(entityTypeMessage.getLogUpdateUser());
            entityTypeDocument.setLogCreationDate(entityTypeMessage.getLogCreationDate());
            entityTypeDocument.setLogUpdateDate(entityTypeMessage.getLogUpdateDate());
            entityTypeDocument.setLogState(entityTypeMessage.getLogState());

            return entityTypeDocument;
        };
    }
}
