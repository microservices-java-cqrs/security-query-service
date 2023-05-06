package com.freetech.sample.securityqueryservice.infraestructure.adapters.out;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.freetech.sample.securityqueryservice.application.mappers.*;
import com.freetech.sample.securityqueryservice.infraestructure.adapters.out.documents.EntityDocument;
import com.freetech.sample.securityqueryservice.infraestructure.adapters.out.documents.EntityTypeDocument;
import com.freetech.sample.securityqueryservice.infraestructure.adapters.out.documents.UserDocument;
import com.freetech.sample.securityqueryservice.infraestructure.ports.out.EntityRepository;
import enums.StateEnum;
import interfaces.PersistenceAdapter;
import lombok.RequiredArgsConstructor;
import messages.*;
import org.bson.Document;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import utils.JsonUtil;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor
@PersistenceAdapter
public class MongoRepository implements EntityRepository {
    private final MongoTemplate mongoTemplate;

    @Override
    public <T> T save(String tableName, T entity) {
        return mongoTemplate.save(transformToDocument(entity), tableName);
    }

    @Override
    public <T> T update(String tableName, T entity) {
        return mongoTemplate.save(transformToDocument(entity), tableName);
    }

    public <K, T> T getById(K id, Class<T> clazz) {
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(id));
        query.addCriteria(Criteria.where("logState").is(StateEnum.ACTIVE.getValue()));
        var results = mongoTemplate.find(query, clazz);
        return results.size() > 0 ? results.get(0) : null;
    }

    @Override
    public <T> List<T> getByQuery(Query query, Class<T> clazz) {
        return mongoTemplate.find(query, clazz);
    }

    @Override
    public <T> Long countByQuery(Query query, Class<T> clazz) {
        return mongoTemplate.count(query, clazz);
    }

    @Override
    public <T> List<T> getByQuery(String strQuery, Class<T> clazz) {
        Document bsonCmd = Document.parse(strQuery);
        var result = mongoTemplate.getDb().runCommand(bsonCmd);
        Document cursor = (Document) result.get("cursor");
        var docs = (List<Document>) cursor.get("firstBatch");
        List<T> results = new ArrayList<>();
        docs.forEach( doc -> {
            T item = null;
            try {
                item = JsonUtil.convertToObject(JsonUtil.convertoToJson(doc), clazz);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
            results.add(item);
        });
        return results;
    }

    @Override
    public Integer countByQuery(String strQuery) {
        Document bsonCmd = Document.parse(strQuery);
        var result = mongoTemplate.getDb().runCommand(bsonCmd);
        Document cursor = (Document) result.get("cursor");
        var docs = (List<Document>) cursor.get("firstBatch");
        return docs.size() > 0 ? (Integer) docs.get(0).get("total") : 0;
    }

    private <K, T> K transformToDocument(T entity) {
        Object document = null;
        if (entity instanceof EntityTypeMessage) {
            document = EntityTypeDocumentMapper.toDocument((EntityTypeMessage) entity);
        } else if(entity instanceof EntityMessage) {
            var ent = (EntityMessage) entity;
            document = EntityDocumentMapper.toDocument((EntityMessage) entity);
            ((EntityDocument) document).setEntityTypeDocument(new EntityTypeDocument());
            ((EntityDocument) document).getEntityTypeDocument().setId(ent.getEntityTypeId());
        } else if(entity instanceof UserMessage) {
            var user = (UserMessage) entity;
            document = UserDocumentMapper.toDocument((UserMessage) entity);
            ((UserDocument) document).setEntityDocument(new EntityDocument());
            ((UserDocument) document).getEntityDocument().setId(user.getEntityId());
        } else if(entity instanceof RolMessage){
            document = RolDocumentMapper.toDocument((RolMessage) entity);
        } else if(entity instanceof UserRolMessage) {
            document = UserRolDocumentMapper.toDocument((UserRolMessage) entity);
        }
        return (K) document;
    }
}