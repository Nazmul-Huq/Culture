package nazmul.culture.controller;

import nazmul.culture.domain.Band;
import nazmul.culture.domain.Event;
import nazmul.culture.dto.EventRequest;
import nazmul.culture.service.IService.IBandService;
import nazmul.culture.service.IService.IEventService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class EventController {

    IEventService eventService;
    IBandService bandService;

    public EventController(IEventService eventService, IBandService bandService) {
        this.eventService = eventService;
        this.bandService = bandService;
    }

    @PostMapping("/api/event/save")
    public ResponseEntity<String> createBand(@RequestBody EventRequest eventRequest){
        Optional<Band> band = bandService.findById(eventRequest.getId());
        if (band != null) {
            Event event = new Event();
            event.setVenue(eventRequest.getVenue());
            event.setBand(band.get());
            eventService.save(event);
            return new ResponseEntity<>("Event created",  HttpStatus.OK);
        }
        return new ResponseEntity<>("band do not exist",  HttpStatus.BAD_REQUEST);

    }
}
