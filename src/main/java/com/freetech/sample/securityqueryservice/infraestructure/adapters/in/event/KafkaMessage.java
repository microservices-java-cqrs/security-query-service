package com.freetech.sample.securityqueryservice.infraestructure.adapters.in.event;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.freetech.sample.securityqueryservice.domain.model.*;
import com.freetech.sample.securityqueryservice.infraestructure.ports.in.*;
import enums.OperationEnum;
import enums.TableEnum;
import interfaces.EventAdapter;
import lombok.RequiredArgsConstructor;
import messages.*;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import utils.JsonUtil;

import java.util.LinkedHashMap;
import java.util.List;

@RequiredArgsConstructor
@EventAdapter
public class KafkaMessage {
    private final MessageBrokerPort messageBrokerPort;

    @KafkaListener(topicPattern = "${security.kafka.event.message.topics}", groupId = "group1")
    public void consumeEvent(@Payload(required = false) String json) throws JsonProcessingException {
        List<LinkedHashMap> list = JsonUtil.convertToObject(json, List.class);
        for (var item: list) {
            var messagePersistence = JsonUtil.convertToObject(JsonUtil.convertoToJson(item), PersistenceMessage.class);
            Object persistenceObject = null;
            if (messagePersistence.getTableName().equals(TableEnum.ENTITY_TYPES.getValue())) {
                persistenceObject = JsonUtil.convertToObject(JsonUtil.convertoToJson(messagePersistence.getMessage()), EntityTypeMessage.class);
            } else if (messagePersistence.getTableName().equals(TableEnum.ENTITIES.getValue())) {
                persistenceObject = JsonUtil.convertToObject(JsonUtil.convertoToJson(messagePersistence.getMessage()), EntityMessage.class);
            } else if (messagePersistence.getTableName().equals(TableEnum.USERS.getValue())) {
                persistenceObject = JsonUtil.convertToObject(JsonUtil.convertoToJson(messagePersistence.getMessage()), UserMessage.class);
            } else if (messagePersistence.getTableName().equals(TableEnum.ROLES.getValue())) {
                persistenceObject = JsonUtil.convertToObject(JsonUtil.convertoToJson(messagePersistence.getMessage()), RolMessage.class);
            } else if (messagePersistence.getTableName().equals(TableEnum.USERS_ROLES.getValue())) {
                persistenceObject = JsonUtil.convertToObject(JsonUtil.convertoToJson(messagePersistence.getMessage()), UserRol.class);
            }

            if (messagePersistence.getOperation() == OperationEnum.CREATE.getValue()) {
                messageBrokerPort.create(messagePersistence.getTableName(), persistenceObject);
            }
        }
    }
}