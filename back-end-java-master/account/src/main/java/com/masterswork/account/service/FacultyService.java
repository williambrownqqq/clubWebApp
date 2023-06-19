package com.masterswork.account.service;

import com.masterswork.account.api.dto.faculty.FacultyCreateDTO;
import com.masterswork.account.api.dto.faculty.FacultyResponseDTO;
import com.masterswork.account.api.dto.faculty.FacultyUpdateDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface FacultyService {

    FacultyResponseDTO createFaculty(FacultyCreateDTO facultyResponseDTO);

    FacultyResponseDTO updateFaculty(Long id, FacultyUpdateDTO facultyResponseDTO);

    FacultyResponseDTO patchFaculty(Long id, FacultyUpdateDTO facultyResponseDTO);

    Page<FacultyResponseDTO> getAllFaculties(Pageable pageable);

    FacultyResponseDTO getFaculty(Long id);

    void deleteFaculty(Long id);
}
