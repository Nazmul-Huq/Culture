package nazmul.culture.controller;

import nazmul.culture.domain.Band;
import nazmul.culture.domain.Event;
import nazmul.culture.dto.EventRequest;
import nazmul.culture.service.IService.IBandService;
import nazmul.culture.service.IService.IEventService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/api/event")
public class EventController {

    IEventService eventService;
    IBandService bandService;

    public EventController(IEventService eventService, IBandService bandService) {
        this.eventService = eventService;
        this.bandService = bandService;
    }

    @PostMapping("/save")
    public ResponseEntity<String> createBand(@RequestBody EventRequest eventRequest){
        Optional<Band> band = bandService.findById(eventRequest.getBandId());
        if (band != null) {
            Event event = new Event();
            event.setVenue(eventRequest.getVenue());
            event.setBand(band.get());
            event.setTimestamp(eventRequest.getTimestamp());
            eventService.save(event);
            return new ResponseEntity<>("Event created",  HttpStatus.OK);
        }
        return new ResponseEntity<>("band do not exist",  HttpStatus.BAD_REQUEST);
    } // createBand() ends here

    @GetMapping("/get-event-by-date")
    public ResponseEntity<?> getEvents(){
        //List<Event> eventList = eventService.findByOrderByTimestamp();
      Set<EventRequest> events = new HashSet<>();
        eventService.findByOrderByTimestamp().forEach((event -> {
            EventRequest eventRequest = new EventRequest();
            eventRequest.setVenue(event.getVenue());
            eventRequest.setBandId(event.getBand().getId());
            eventRequest.setTimestamp(event.getTimestamp());
            events.add(eventRequest);
        }));
        return new ResponseEntity<>(events, HttpStatus.OK);
    }


} // class ends here
