package com.freetech.sample.securityqueryservice.infraestructure.adapters.in.event;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.freetech.sample.securityqueryservice.domain.EntityType;
import com.freetech.sample.securityqueryservice.domain.User;
import com.freetech.sample.securityqueryservice.infraestructure.ports.in.MessageBrokerPort;
import enums.OperationEnum;
import enums.TableEnum;
import interfaces.EventAdapter;
import lombok.RequiredArgsConstructor;
import messages.MessagePersistence;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;
import utils.JsonUtil;

@RequiredArgsConstructor
@EventAdapter
public class KafkaMessage {
    private final MessageBrokerPort messageBrokerPort;

    @KafkaListener(topicPattern = "${security.kafka.event.message.topics}", groupId = "group1")
    public void consumeEvent(@Payload(required = false) String json) throws JsonProcessingException {
        var messagePersistence = JsonUtil.convertToObject(json, MessagePersistence.class);
        Object persistenceObject = null;
        if (messagePersistence.getTableName().equals(TableEnum.USERS.getValue())) {
            persistenceObject = JsonUtil.convertToObject(JsonUtil.convertoToJson(messagePersistence.getMessage()), User.class);
        } else if (messagePersistence.getTableName().equals(TableEnum.ENTITY_TYPES.getValue())) {
            persistenceObject = JsonUtil.convertToObject(JsonUtil.convertoToJson(messagePersistence.getMessage()), EntityType.class);
        }

        if (messagePersistence.getOperation() == OperationEnum.CREATE.getValue()) {
            messageBrokerPort.create(messagePersistence.getTableName(), persistenceObject);
        } else if (messagePersistence.getOperation() == OperationEnum.UPDATE.getValue()) {
            messageBrokerPort.update(messagePersistence.getTableName(), persistenceObject);
        } else if (messagePersistence.getOperation() == OperationEnum.DELETE.getValue()) {
            messageBrokerPort.delete(messagePersistence.getTableName(), persistenceObject);
        }
    }
}