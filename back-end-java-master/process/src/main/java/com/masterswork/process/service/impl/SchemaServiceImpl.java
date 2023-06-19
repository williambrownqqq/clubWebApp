package com.masterswork.process.service.impl;

import com.masterswork.process.api.dto.schema.SchemaCreateDTO;
import com.masterswork.process.api.dto.schema.SchemaDTO;
import com.masterswork.process.api.dto.schema.stage.StageDTO;
import com.masterswork.process.service.SchemaService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class SchemaServiceImpl implements SchemaService {

    private final static String SCHEMA_COLLECTION_NAME = "process_schema";

    private final MongoOperations mongoOperations;


    @Override
    public SchemaDTO saveProcess(SchemaCreateDTO schemaCreateDTO) {
        return mongoOperations.insert(SchemaDTO.from(schemaCreateDTO), SCHEMA_COLLECTION_NAME);
    }

    @Override
    public SchemaDTO updateProcess(String id, SchemaCreateDTO schemaCreateDTO) {
        SchemaDTO schema = findById(id);
        schema.setName(schemaCreateDTO.getName());
        schema.setSchema(schemaCreateDTO.getSchema());
        return mongoOperations.save(schema, SCHEMA_COLLECTION_NAME);
    }

    @Override
    public List<SchemaDTO> getAllSchemas() {
        return mongoOperations.findAll(SchemaDTO.class, SCHEMA_COLLECTION_NAME);
    }


    @Override
    public SchemaDTO findById(String id) {
        SchemaDTO schema = mongoOperations.findById(id, SchemaDTO.class, SCHEMA_COLLECTION_NAME);
        if (schema == null) {
            throw new EntityNotFoundException("Schema with id [" + id + "] not found");
        }
        return schema;
    }

    @Override
    public StageDTO getSchemaStage(String schemaId, Long stageId) {
        StageDTO stage = findById(schemaId).getStage(stageId);
        if (stage == null) {
            throw new EntityNotFoundException("Schema with id [" + schemaId + "] doesn't contain stage " + stageId);
        }
        return stage;
    }

    @Override
    public Long getNextStageId(String schemaId, Long stageId, String outcome) {
        SchemaDTO schema = findById(schemaId);
        return schema.getStage(stageId).getNextStageId(outcome);
    }
}
