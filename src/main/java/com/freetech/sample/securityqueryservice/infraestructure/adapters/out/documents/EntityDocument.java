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
@Document(value = "entities")
public class EntityDocument {
    @Id
    @Field(name = "_id")
    private Long id;

    @Field(name = "parent_id")
    private Long parentId;

    @DocumentReference(lazy = true)
    @Field(name = "entity_type_id")
    private EntityTypeDocument entityTypeDocument;

    @Field(name = "number_document")
    private String numberDocument;

    @Field(name = "bussiness_name")
    private String bussinessName;

    @Field(name = "name")
    private String name;

    @Field(name = "lastname")
    private String lastname;

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