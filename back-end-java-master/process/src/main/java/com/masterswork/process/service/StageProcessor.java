package com.masterswork.process.service;

import com.masterswork.process.api.dto.schema.stage.StageDTO;
import com.masterswork.process.model.relational.ProcessInstance;

public interface StageProcessor {

    void process(StageDTO stageDTO, ProcessInstance processInstance);
}
