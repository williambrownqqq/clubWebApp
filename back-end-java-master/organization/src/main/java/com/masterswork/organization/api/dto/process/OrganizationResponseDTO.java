package com.masterswork.organization.api.dto.process;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.validation.constraints.Min;
import java.util.Set;

@Data
@Builder(toBuilder = true)
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class OrganizationResponseDTO {

    private Long id;

    private String name;

    @Min(1)
    private Long headId;

    private Set<Long> participantsIds;

    private Set<Long> subsId;

    private String ownerRef;


}
