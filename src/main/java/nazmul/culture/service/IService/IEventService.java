package nazmul.culture.service.IService;

import nazmul.culture.domain.Event;

import java.util.List;
import java.util.Set;

public interface IEventService extends IGlobalService<Event, Long> {

    // get all event sort by data
    public List<Event> findByOrderByTimestamp();

}
