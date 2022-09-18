package nazmul.culture.service.ServiceImpl;

import nazmul.culture.domain.Event;
import nazmul.culture.domain.Review;
import nazmul.culture.repository.ReviewRepository;
import nazmul.culture.service.IService.IReviewService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReviewService implements IReviewService {

    ReviewRepository reviewRepository;

    public ReviewService(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }


    @Override
    public List<Review> findAll() {
        return reviewRepository.findAll();
    }

    @Override
    public Review save(Review review) {
        return reviewRepository.save(review);
    }

    @Override
    public Optional<Review> findById(Long id) {
        return reviewRepository.findById(id);
    }

    @Override
    public void deleteById(Long id) {
        reviewRepository.deleteById(id);
    }

    @Override
    public List<Review> getReview(Event event) {
        return reviewRepository.findAllByEvent(event);
    }



}
