package com.masterswork.account.service;

import com.masterswork.account.api.dto.cathedra.CathedraCreateDTO;
import com.masterswork.account.api.dto.cathedra.CathedraResponseDTO;
import com.masterswork.account.api.dto.cathedra.CathedraUpdateDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CathedraService {

    CathedraResponseDTO createCathedraByFacultyId(Long facultyId, CathedraCreateDTO cathedraCreateDTO);

    List<CathedraResponseDTO>  createCathedrasByFacultyId(Long facultyId, List<CathedraCreateDTO> cathedraCreateDTO);

    Page<CathedraResponseDTO> getAllCathedras(Pageable pageable);

    Page<CathedraResponseDTO> getAllCathedrasByAppUserId(Long appUserId, Pageable pageable);

    Page<CathedraResponseDTO> findAllCathedrasByFacultyId(Long facultyId, Pageable pageable);

    CathedraResponseDTO updateCathedra(Long cathedraId, CathedraUpdateDTO cathedraUpdateDTO);

    CathedraResponseDTO assignCathedraToFaculty(Long facultyId, Long cathedraId);

    CathedraResponseDTO patchCathedra(Long cathedraId, CathedraUpdateDTO cathedraUpdateDTO);

    void deleteCathedraById(Long cathedraId);

    CathedraResponseDTO getCathedraById(Long cathedraId);
    CathedraResponseDTO getCathedraByFacultyIdAndCathedraId(Long facultyId, Long cathedraId);
}
