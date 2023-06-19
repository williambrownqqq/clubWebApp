package com.masterswork.process.repository;

import com.masterswork.process.model.relational.ProcessInstance;
import com.masterswork.process.model.relational.ProcessJob;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;

@Repository
public interface ProcessInstanceRepository extends JpaRepository<ProcessInstance, Long> {

    Page<ProcessInstance> findAllByOwnerIdOrSubordinateId(Long ownerId, Long subordinateId, Pageable pageable);
}
