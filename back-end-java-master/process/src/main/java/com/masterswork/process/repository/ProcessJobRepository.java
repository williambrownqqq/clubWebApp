package com.masterswork.process.repository;

import com.masterswork.process.model.relational.ProcessJob;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;

@Repository
public interface ProcessJobRepository extends JpaRepository<ProcessJob, Long> {

    List<ProcessJob> findAllByIsExecutedAndStartTimeIsLessThanEqual(Boolean isExecuted, Instant executeTime);
}
