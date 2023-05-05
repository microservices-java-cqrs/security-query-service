package com.freetech.sample.securityqueryservice.infraestructure.adapters.out.documents;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;

@Getter
@NoArgsConstructor
@Setter
@Document(value = "users")
public class UserDocument {
    @Id
    @Field(name = "_id")
    private Long id;

    @DocumentReference(lazy = true)
    @Field(name = "entity_id")
    private EntityDocument entityDocument;

    @Field(name = "username")
    private String username;

    @Field(name = "password")
    private String password;

    @Field(name = "status")
    private String status;

    @Field(name = "log_creation_user")
    private String logCreationUser;

    @Field(name = "log_update_user")
    private String logUpdateUser;

    @Field(name = "log_creation_date")
    private Date logCreationDate;

    @Field(name = "log_update_date")
    private Date logUpdateDate;

    @Field(name = "log_state")
    private Integer logState;
}
