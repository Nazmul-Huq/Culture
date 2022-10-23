package nazmul.culture.service.ServiceImpl;

import nazmul.culture.domain.Venue;
import nazmul.culture.repository.VenueRepository;
import nazmul.culture.service.IService.IVenueService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VenueService implements IVenueService {

    private VenueRepository venueRepository;

    public VenueService(VenueRepository venueRepository) {
        this.venueRepository = venueRepository;
    }

    @Override
    public List<Venue> findAll() {
        return venueRepository.findAll();
    }

    @Override
    public Venue save(Venue venue) {
        return venueRepository.save(venue);
    }

    @Override
    public Optional<Venue> findById(Long id) {
        return venueRepository.findById(id);
    }

    @Override
    public void deleteById(Long id) {
        venueRepository.deleteById(id);
    }
}
