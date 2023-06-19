package com.masterswork.process.api.dto.schema;

import com.masterswork.process.api.dto.schema.stage.StageDTO;
import lombok.*;

import javax.persistence.Id;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SchemaDTO {

    @Id
    private String id;

    private String name;

    private Map<String, StageDTO> schema;

    public StageDTO getStage(Long stageId) {
        return schema.get(Long.toString(stageId));
    }

    public static SchemaDTO from(SchemaCreateDTO schemaCreateDTO) {
        return SchemaDTO.builder()
                .name(schemaCreateDTO.getName())
                .schema(schemaCreateDTO.getSchema())
                .build();
    }
}
