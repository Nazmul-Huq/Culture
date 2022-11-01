package nazmul.culture.service.IService;

import nazmul.culture.domain.Event;
import nazmul.culture.domain.Review;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IReviewService extends IGlobalService<Review, Long>{

    public List<Review> getReview(Event event);

}
