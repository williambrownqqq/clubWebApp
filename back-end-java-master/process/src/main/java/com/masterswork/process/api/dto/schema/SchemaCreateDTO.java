package com.masterswork.process.api.dto.schema;

import com.masterswork.process.api.dto.schema.stage.StageDTO;
import lombok.*;

import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SchemaCreateDTO {

    private String name;

    private Map<String, StageDTO> schema;
}
