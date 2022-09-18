package nazmul.culture.service.ServiceImpl;

import nazmul.culture.domain.Band;
import nazmul.culture.repository.BandRepository;
import nazmul.culture.service.IService.IBandService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BandService implements IBandService {

    private BandRepository bandRepository;

    public BandService(BandRepository bandRepository) {
        this.bandRepository = bandRepository;
    }

    @Override
    public List<Band> findAll() {
        return null;
    }

    @Override
    public Band save(Band band) {
        return bandRepository.save(band);
    }

    @Override
    public Optional<Band> findById(Long id) {
        return bandRepository.findById(id);
    }

    @Override
    public void deleteById(Long id) {

    }
}
