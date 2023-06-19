package com.masterswork.account.repository;

import com.masterswork.account.model.Cathedra;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CathedraRepository extends JpaRepository<Cathedra, Long> {

    Page<Cathedra> findAllByFacultyId(Long facultyId, Pageable pageable);

    Page<Cathedra> findAllByUsers_Id(Long userId, Pageable pageable);

    Optional<Cathedra> findByFacultyIdAndId(Long facultyId, Long cathedraId);
}
