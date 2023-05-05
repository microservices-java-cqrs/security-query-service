package com.freetech.sample.securityqueryservice.infraestructure.adapters.out.documents;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;

@Getter
@NoArgsConstructor
@Setter
@Document(value = "entity_types")
public class EntityTypeDocument {
    @Id
    @Field(name = "_id")
    private Long id;

    @Field(name = "name")
    private String name;

    @Field(name = "description")
    private String description;

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
