package com.masterswork.account.service.impl;

import com.masterswork.account.api.dto.faculty.FacultyCreateDTO;
import com.masterswork.account.api.dto.faculty.FacultyResponseDTO;
import com.masterswork.account.api.dto.faculty.FacultyUpdateDTO;
import com.masterswork.account.model.Faculty;
import com.masterswork.account.repository.FacultyRepository;
import com.masterswork.account.service.FacultyService;
import com.masterswork.account.service.mapper.FacultyMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class FacultyServiceImpl implements FacultyService {

    private final FacultyRepository facultyRepository;
    private final FacultyMapper facultyMapper;

    @Override
    public FacultyResponseDTO createFaculty(FacultyCreateDTO facultyResponseDTO) {
        Faculty faculty = facultyRepository.save(facultyMapper.createFrom(facultyResponseDTO));
        return facultyMapper.toDto(faculty);
    }

    @Override
    public FacultyResponseDTO updateFaculty(Long id, FacultyUpdateDTO facultyResponseDTO) {
        Faculty faculty = facultyRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No faculty with id: " + id));
        facultyMapper.updateFrom(faculty, facultyResponseDTO);
        return facultyMapper.toDto(facultyRepository.save(faculty));
    }

    @Override
    public FacultyResponseDTO patchFaculty(Long id, FacultyUpdateDTO facultyResponseDTO) {
        Faculty faculty = facultyRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No faculty with id: " + id));
        facultyMapper.patchFrom(faculty, facultyResponseDTO);
        return facultyMapper.toDto(facultyRepository.save(faculty));
    }

    @Override
    public Page<FacultyResponseDTO> getAllFaculties(Pageable pageable) {
        return facultyRepository.findAll(pageable).map(facultyMapper::toDto);
    }

    @Override
    public FacultyResponseDTO getFaculty(Long id) {
        return facultyRepository.findById(id)
                .map(facultyMapper::toDto)
                .orElseThrow(() -> new EntityNotFoundException("No faculty with id: " + id));
    }

    @Override
    public void deleteFaculty(Long id) {
        if (facultyRepository.existsById(id)) {
            facultyRepository.deleteById(id);
        } else {
            throw new EntityNotFoundException("No faculty with id: " + id);
        }
    }
}
