package com.alex.zanchenko.web.service;

import com.alex.zanchenko.web.dto.EventDTO;
import com.alex.zanchenko.web.model.Event;

import java.util.List;

public interface EventService {
    Event createEvent(Long clubID, EventDTO eventDTO);
    List<EventDTO> findAllEvents();

    EventDTO findEventById(Long eventID);

    void updateEvent(EventDTO eventDTO);

    void deleteEvent(Long eventID);

    List<EventDTO> searchEvents(String query);
}
