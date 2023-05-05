package com.freetech.sample.securityqueryservice.application.mappers;

import com.freetech.sample.securityqueryservice.domain.model.Rol;
import com.freetech.sample.securityqueryservice.infraestructure.adapters.out.documents.RolDocument;
import messages.RolMessage;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class RolDocumentMapper {
    /*public static RolDocument toDocument(Rol rol) {
        if (rol == null) return null;
        return Stream.of(rol).map(toDocument()).findFirst().get();
    }*/
    public static RolDocument toDocument(RolMessage rol) {
        if (rol == null) return null;
        return Stream.of(rol).map(toDocument()).findFirst().get();
    }

    public static Rol toDomain(RolDocument rolDocument) {
        if (rolDocument == null) return null;
        return Stream.of(rolDocument).map(toDomain()).findFirst().get();
    }

    public static List<Rol> toDomain(List<RolDocument> list) {
        if (list == null) return null;
        return list.stream().map(toDomain()).collect(Collectors.toList());
    }

    private static Function<RolMessage, RolDocument> toDocument() {
        return rol -> {
            var rolDocument = new RolDocument();
            rolDocument.setId(rol.getId());
            rolDocument.setName(rol.getName());
            rolDocument.setDescription(rol.getDescription());
            rolDocument.setLogCreationUser(rol.getLogCreationUser());
            rolDocument.setLogUpdateUser(rol.getLogUpdateUser());
            rolDocument.setLogCreationDate(rol.getLogCreationDate());
            rolDocument.setLogUpdateDate(rol.getLogUpdateDate());
            rolDocument.setLogState(rol.getLogState());

            return rolDocument;
        };
    }

    private static Function<RolDocument, Rol> toDomain() {
        return rolDocument -> {
            var rol = new Rol();
            rol.setId(rolDocument.getId());
            rol.setName(rolDocument.getName());
            rol.setDescription(rolDocument.getDescription());
            return rol;
        };
    }
}
