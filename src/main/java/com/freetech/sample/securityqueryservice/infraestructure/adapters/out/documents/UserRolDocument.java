package com.freetech.sample.securityqueryservice.infraestructure.adapters.out.documents;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;
import org.springframework.data.mongodb.core.mapping.Field;

@Getter
@NoArgsConstructor
@Setter
@Document(value = "users_roles")
public class UserRolDocument {
    @Id
    @Field(name = "_id")
    private Long id;

    @DocumentReference(lazy = true)
    @Field(name = "user_id")
    private UserDocument userDocument;

    @DocumentReference(lazy = true)
    @Field(name = "rol_id")
    private RolDocument rolDocument;
}