package com.masterswork.account.api.dto.cathedra;

import com.masterswork.account.api.dto.faculty.FacultyResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class CathedraResponseDTO {

    private Long id;

    private String name;

    private FacultyResponseDTO faculty;

}
