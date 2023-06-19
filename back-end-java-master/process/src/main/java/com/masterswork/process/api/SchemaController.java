package com.masterswork.process.api;

import com.masterswork.process.api.dto.schema.SchemaCreateDTO;
import com.masterswork.process.api.dto.schema.SchemaDTO;
import com.masterswork.process.api.dto.schema.stage.StageDTO;
import com.masterswork.process.service.SchemaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/schema")
@RequiredArgsConstructor
public class SchemaController {

    private final SchemaService schemaService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<SchemaDTO> createSchema(@RequestBody SchemaCreateDTO schemaCreateDTO) {
        return ResponseEntity.ok(schemaService.saveProcess(schemaCreateDTO));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<SchemaDTO> updateSchema(@PathVariable String id, @RequestBody SchemaCreateDTO schemaCreateDTO) {
        return ResponseEntity.ok(schemaService.updateProcess(id, schemaCreateDTO));
    }

    @GetMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<List<SchemaDTO>> getAllSchemas() {
        return ResponseEntity.ok(schemaService.getAllSchemas());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<SchemaDTO> getSchemaById(@PathVariable String id) {
        return ResponseEntity.ok(schemaService.findById(id));
    }

    @GetMapping("/{schemaId}/stage/{stageId}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Object> getSchemaStage(@PathVariable String schemaId, @PathVariable Long stageId) {
        return ResponseEntity.ok(schemaService.getSchemaStage(schemaId, stageId));
    }
}
