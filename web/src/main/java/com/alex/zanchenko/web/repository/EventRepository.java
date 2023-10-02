package com.alex.zanchenko.web.repository;

import com.alex.zanchenko.web.model.Club;
import com.alex.zanchenko.web.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EventRepository extends JpaRepository<Event, Long> {
    @Query("SELECT c from Event c WHERE c.name LIKE CONCAT('%', :query, '%')")
    List<Event> findEvents(String query);
}
