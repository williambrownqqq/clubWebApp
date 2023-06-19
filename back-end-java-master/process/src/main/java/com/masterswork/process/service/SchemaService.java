package com.masterswork.process.service;

import com.masterswork.process.api.dto.schema.SchemaCreateDTO;
import com.masterswork.process.api.dto.schema.SchemaDTO;
import com.masterswork.process.api.dto.schema.stage.StageDTO;

import java.util.List;
import java.util.Map;

public interface SchemaService {

    SchemaDTO saveProcess(SchemaCreateDTO schemaCreateDTO);

    SchemaDTO updateProcess(String id, SchemaCreateDTO schemaCreateDTO);

    List<SchemaDTO> getAllSchemas();

    SchemaDTO findById(String id);

    StageDTO getSchemaStage(String schemaId, Long stageId);

    Long getNextStageId(String schemaId, Long stageId, String outcome);
}
