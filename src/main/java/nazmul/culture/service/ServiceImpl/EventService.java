package nazmul.culture.service.ServiceImpl;

import nazmul.culture.domain.Event;
import nazmul.culture.repository.EventRepository;
import nazmul.culture.service.IService.IEventService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EventService implements IEventService {

    EventRepository eventRepository;

    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    @Override
    public List<Event> findAll() {
        return null;
    }

    @Override
    public Event save(Event event) {
        return eventRepository.save(event);
    }

    @Override
    public Optional<Event> findById(Long id) {
        return Optional.empty();
    }
}
