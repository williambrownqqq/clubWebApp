package com.masterswork.account.service.impl;

import com.masterswork.account.api.dto.cathedra.CathedraCreateDTO;
import com.masterswork.account.api.dto.cathedra.CathedraResponseDTO;
import com.masterswork.account.api.dto.cathedra.CathedraUpdateDTO;
import com.masterswork.account.model.Cathedra;
import com.masterswork.account.model.Faculty;
import com.masterswork.account.repository.CathedraRepository;
import com.masterswork.account.repository.FacultyRepository;
import com.masterswork.account.service.CathedraService;
import com.masterswork.account.service.mapper.CathedraMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class CathedraServiceImpl implements CathedraService {

    private final CathedraMapper cathedraMapper;
    private final FacultyRepository facultyRepository;
    private final CathedraRepository cathedraRepository;

    @Override
    public CathedraResponseDTO createCathedraByFacultyId(Long facultyId, CathedraCreateDTO cathedraCreateDTO) {
        Faculty faculty = facultyRepository.findById(facultyId)
                .orElseThrow(() -> new EntityNotFoundException("No faculty with id: " + facultyId));

        Cathedra newCathedra = cathedraMapper.createFrom(cathedraCreateDTO);
        newCathedra.setFaculty(faculty);

        return cathedraMapper.toDto(cathedraRepository.save(newCathedra));
    }

    @Override
    public List<CathedraResponseDTO> createCathedrasByFacultyId(Long facultyId, List<CathedraCreateDTO> cathedraCreateDTOList) {
        Faculty faculty = facultyRepository.findById(facultyId)
                .orElseThrow(() -> new EntityNotFoundException("No faculty with id: " + facultyId));

        List<Cathedra> newCathedras = cathedraMapper.createFrom(cathedraCreateDTOList);
        newCathedras.forEach(cathedra -> cathedra.setFaculty(faculty));

        return cathedraMapper.toDto(cathedraRepository.saveAll(newCathedras));
    }

    @Override
    public Page<CathedraResponseDTO> getAllCathedras(Pageable pageable) {
        return cathedraRepository.findAll(pageable).map(cathedraMapper::toDto);
    }

    @Override
    public Page<CathedraResponseDTO> getAllCathedrasByAppUserId(Long appUserId, Pageable pageable) {
        return cathedraRepository.findAllByUsers_Id(appUserId, pageable).map(cathedraMapper::toDto);
    }

    @Override
    public Page<CathedraResponseDTO> findAllCathedrasByFacultyId(Long facultyId, Pageable pageable) {
        return cathedraRepository.findAllByFacultyId(facultyId, pageable).map(cathedraMapper::toDto);
    }

    @Override
    public CathedraResponseDTO getCathedraById(Long cathedraId) {
        return cathedraRepository.findById(cathedraId)
                .map(cathedraMapper::toDto)
                .orElseThrow(() -> new EntityNotFoundException("No cathedra with id: " + cathedraId));
    }

    @Override
    public CathedraResponseDTO getCathedraByFacultyIdAndCathedraId(Long facultyId, Long cathedraId) {
        return cathedraRepository.findByFacultyIdAndId(facultyId, cathedraId)
                .map(cathedraMapper::toDto)
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format("No cathedra with facultyId %d and cathedraId %d", facultyId, cathedraId)
                ));
    }

    @Override
    public CathedraResponseDTO updateCathedra(Long cathedraId, CathedraUpdateDTO cathedraUpdateDTO) {
        Cathedra cathedra = cathedraRepository.findById(cathedraId)
                .orElseThrow(() -> new EntityNotFoundException("No cathedra with id " + cathedraId));

        cathedraMapper.updateFrom(cathedra, cathedraUpdateDTO);

        return cathedraMapper.toDto(cathedraRepository.save(cathedra));
    }

    @Override
    public CathedraResponseDTO assignCathedraToFaculty(Long facultyId, Long cathedraId) {
        Cathedra cathedra = cathedraRepository.findById(cathedraId)
                .orElseThrow(() -> new EntityNotFoundException("No cathedra with Id " + cathedraId));
        Faculty faculty = facultyRepository.findById(facultyId)
                .orElseThrow(() -> new EntityNotFoundException("No faculty with Id " + facultyId));

        cathedra.setFaculty(faculty);

        return cathedraMapper.toDto(cathedraRepository.save(cathedra));
    }

    @Override
    public CathedraResponseDTO patchCathedra(Long cathedraId, CathedraUpdateDTO cathedraUpdateDTO) {
        Cathedra cathedra = cathedraRepository.findById(cathedraId)
                .orElseThrow(() -> new EntityNotFoundException("No cathedra with id " + cathedraId));

        cathedraMapper.patchFrom(cathedra, cathedraUpdateDTO);

        return cathedraMapper.toDto(cathedraRepository.save(cathedra));
    }

    @Override
    public void deleteCathedraById(Long cathedraId) {
        Cathedra cathedra = cathedraRepository.findById(cathedraId)
                .orElseThrow(() -> new EntityNotFoundException("No cathedra with id " + cathedraId));

        cathedraRepository.delete(cathedra);
    }
}
