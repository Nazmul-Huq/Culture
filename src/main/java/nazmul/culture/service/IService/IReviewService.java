package nazmul.culture.service.IService;

import nazmul.culture.domain.Event;
import nazmul.culture.domain.Review;

import java.util.List;

public interface IReviewService extends IGlobalService<Review, Long>{

    public List<Review> getReview(Event event);

}
