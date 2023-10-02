package com.alex.zanchenko.web.service.impl;

import com.alex.zanchenko.web.dto.EventDTO;
import com.alex.zanchenko.web.model.Club;
import com.alex.zanchenko.web.model.Event;
import com.alex.zanchenko.web.repository.ClubRepository;
import com.alex.zanchenko.web.repository.EventRepository;
import com.alex.zanchenko.web.service.EventService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.alex.zanchenko.web.mapper.EventMapper.mapToEvent;
import static com.alex.zanchenko.web.mapper.EventMapper.mapToEventDTO;

@AllArgsConstructor
@Service
public class EventServiceImpl implements EventService {

    private EventRepository eventRepository;
    private ClubRepository clubRepository;
    @Override
    public Event createEvent(Long clubID, EventDTO eventDTO) {
        Club club = clubRepository.findById(clubID).get();
        Event event = mapToEvent(eventDTO);
        event.setClub(club);
        eventRepository.save(event);
        return event;
    }

    @Override
    public List<EventDTO> findAllEvents() {
        List<Event> events = eventRepository.findAll();
        return events.stream().map(event -> mapToEventDTO(event)).collect(Collectors.toList());
    }

    @Override
    public EventDTO findEventById(Long eventID) {
        Event event = eventRepository.findById(eventID).get();
        return mapToEventDTO(event);
    }

    @Override
    public void updateEvent(EventDTO eventDTO) {
        Event event = mapToEvent(eventDTO);
        eventRepository.save(event);
    }

    @Override
    public void deleteEvent(Long eventID) {
        eventRepository.deleteById(eventID);
    }

    @Override
    public List<EventDTO> searchEvents(String query) {
        List<Event> events = eventRepository.findEvents(query);
        return events.stream().map(event -> mapToEventDTO(event)).collect(Collectors.toList());
    }


}
