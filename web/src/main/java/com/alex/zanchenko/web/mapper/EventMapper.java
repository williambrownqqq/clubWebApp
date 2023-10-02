package com.alex.zanchenko.web.mapper;

import com.alex.zanchenko.web.dto.EventDTO;
import com.alex.zanchenko.web.model.Event;

public class EventMapper {
    public static Event mapToEvent(EventDTO eventDTO){
        Event event = Event.builder()
                .id(eventDTO.getId())
                .name(eventDTO.getName())
                .type(eventDTO.getType())
                .photoURL(eventDTO.getPhotoURL())
                .startTime(eventDTO.getStartTime())
                .endTime(eventDTO.getEndTime())
                .createdOn(eventDTO.getCreatedOn())
                .updatedOn(eventDTO.getUpdatedOn())
                .club(eventDTO.getClub())
                .build();
        return event;
    }

    public static EventDTO mapToEventDTO(Event event){
        return EventDTO.builder()
                .id(event.getId())
                .name(event.getName())
                .type(event.getType())
                .photoURL(event.getPhotoURL())
                .startTime(event.getStartTime())
                .endTime(event.getEndTime())
                .createdOn(event.getCreatedOn())
                .updatedOn(event.getUpdatedOn())
                .club(event.getClub())
                .build();
    }
}
