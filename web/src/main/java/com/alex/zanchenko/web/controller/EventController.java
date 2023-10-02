package com.alex.zanchenko.web.controller;

import com.alex.zanchenko.web.dto.ClubDTO;
import com.alex.zanchenko.web.dto.EventDTO;
import com.alex.zanchenko.web.model.Event;
import com.alex.zanchenko.web.service.ClubService;
import com.alex.zanchenko.web.service.EventService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
public class EventController {

    private EventService eventService;
    private ClubService clubService;

    @Autowired
    public EventController(EventService eventService, ClubService clubService) {
        this.eventService = eventService;
        this.clubService = clubService;

    }

//    @GetMapping("/events/{clubID}/new")
//    public String createEventForm(@PathVariable("clubID") Long clubID, Model model){
//        Event event = new Event();
//        model.addAttribute("clubID", clubID);
//        model.addAttribute("event", event);
//        return "events-create";
//    }
//
//    @PostMapping("/events/{clubID}")
//    public String createEvent(@PathVariable("clubID") Long clubID, @ModelAttribute("event")EventDTO eventDTO, Model model){
//        eventService.createEvent(clubID, eventDTO);
//        return "redirect:/clubs/" + clubID;
//    }

    @GetMapping("/clubs/{clubID}/events/new")
    public ClubDTO createEventForm(@PathVariable("clubID") Long clubID){
        System.out.println(clubID);
        return clubService.findClubById(clubID);
    }
    @PostMapping("/clubs/{clubID}/events")
    public ResponseEntity<?>  createEvent(@PathVariable("clubID") Long clubID, @RequestBody EventDTO eventDTO) { //  The @RequestBody annotation is used to bind the Event object from the request body.
        // Logic to create the event
        // ...
        Event event = eventService.createEvent(clubID, eventDTO);
        // Return a success response
        return ResponseEntity.status(HttpStatus.CREATED).body(event);
    }

    @GetMapping("/events")
    public List<EventDTO> eventList(){
        List<EventDTO> events = eventService.findAllEvents();
        return events;
    }

    @GetMapping("/events/{eventID}")
    public EventDTO viewEvent(@PathVariable("eventID") Long eventID){
        EventDTO eventDTO = eventService.findEventById(eventID);
        return eventDTO;
    }

    @GetMapping("/events/{eventID}/edit")
    @ResponseBody
    public EventDTO editEvent(@PathVariable("eventID") Long eventID){
        EventDTO event = eventService.findEventById(eventID);
        return event;
    }

    @PutMapping("/events/{eventID}/edit")
    @ResponseBody
    public ResponseEntity<?> updateEvent(@PathVariable("eventID") Long eventID,
                                @Valid @RequestBody EventDTO event,
                                BindingResult result){
        if (result.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            for (FieldError error : result.getFieldErrors()) {
                errors.put(error.getField(), error.getDefaultMessage());
            }
            return ResponseEntity.badRequest().body(errors);
        }
        EventDTO eventDTO = eventService.findEventById(eventID);
        event.setId(eventID);
        event.setClub(eventDTO.getClub()); // you could also go into Event and set it for eager loading but i went with lazy loading
        eventService.updateEvent(event);
        System.out.println("updated");
        return ResponseEntity.ok("Event has been successfully updated");
    }

    @DeleteMapping("/events/{eventID}/delete")
    public void deleteEvent(@PathVariable("eventID") Long eventID){
        eventService.deleteEvent(eventID);
    }
    // we could do soft deletes
    // All you would do for a soft delete is just go into your
    // actual database create another table and
    // make it so that when it is actually deleted you just have a deleted and you
    // mark that deleted with a Boolean and it
    // will still uh not delete

    //it won't hard delete it where it deletes everything out of the database but it
    //will allow you to soft delete it so it's still there but it's not there

    @GetMapping("events/search")
    public List<EventDTO> searchEvent(@RequestParam(value = "query") String query, Model model){
        List<EventDTO> events = eventService.searchEvents(query);
        return events;
    }
}