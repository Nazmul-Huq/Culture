package nazmul.culture.controller;

import nazmul.culture.domain.Event;
import nazmul.culture.domain.Review;
import nazmul.culture.domain.User;
import nazmul.culture.dto.ReviewDto;
import nazmul.culture.service.IService.IEventService;
import nazmul.culture.service.IService.IReviewService;
import nazmul.culture.service.IService.IUserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class ReviewController {

    private IReviewService reviewService;
    private IUserService userService;
    private IEventService eventService;

    public ReviewController(IReviewService reviewService,
                            IUserService userService,
                            IEventService eventService) {
        this.reviewService = reviewService;
        this.userService = userService;
        this.eventService = eventService;
    }

    /**
     * ADD A REVIEW
     * first check if given user id and event id exist or not
     * If one of them do not exist, then send Http Not found status back
     * @param request
     * @return
     */
    @PostMapping("/api/review/save")
    public ResponseEntity<?> createReview(@RequestBody ReviewDto request){
        Optional<User> user = userService.findById(request.getUserId()); // get the user
        Optional<Event> event = eventService.findById(request.getEventId()); // get the event

        if ( (user.isPresent()) & ( event.isPresent()) ) { // check if both user and event exist or not
            Review review = new Review();
            review.setText(request.getText());
            review.setRating(request.getRating());
            review.setUser(user.get());
            review.setEvent(event.get());
            reviewService.save(review);
            return new ResponseEntity<>("Review Created Successfully" + review, HttpStatus.OK);
        }
        return new ResponseEntity<>("User or Event do not exist", HttpStatus.NOT_FOUND);
    } // createReview() ends here

    /**
     * GET REVIEW BY EVENT
     * first check if given event is present or not
     * @param eventId
     * @return
     */
    @GetMapping("/api/review/get-review/{eventId}")
    public ResponseEntity<?> getReview(@PathVariable Long eventId){
        Optional<Event> event = eventService.findById(eventId); // get the event if exist
        if (!(event.isEmpty())) {
            List<ReviewDto> reviewDtos = new ArrayList<>(); // create an empty
            reviewService.getReview(event.get()).forEach((review -> { // get all reviews and convert to review dto
                ReviewDto reviewDto = new ReviewDto();
                reviewDto.setId(review.getId());
                reviewDto.setText(review.getText());
                reviewDto.setRating(review.getRating());
                reviewDtos.add(reviewDto);
            }));
            return new ResponseEntity<>(reviewDtos, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("No Event was found with the given event id: " + eventId, HttpStatus.NOT_FOUND);
        }
    } // getReview() ends here

    /**
     * DELETE A REVIEW BY ID
     * @param reviewId
     * @return
     */
    @DeleteMapping("/api/review/delete/{reviewId}")
    public ResponseEntity<String> deleteReview(@PathVariable Long reviewId){
        Optional<Review> review = reviewService.findById(reviewId);
        if (review.isPresent()) {
            reviewService.deleteById(reviewId);
            return new ResponseEntity<>("Review deleted successfully", HttpStatus.OK );
        } else {
            return new ResponseEntity<>("Review was not found", HttpStatus.NOT_FOUND );
        }
    }// deleteReview() ends here

    /**
     * UPDATE A REVIEW
     * Rirht now, only review text and rating can be modified
     * @param request
     * @return
     */
    @PutMapping("/api/review/update-review")
    public ResponseEntity<?> updateReview(@RequestBody ReviewDto request){
        Optional<Review> reviewToUpdate;
        reviewToUpdate = reviewService.findById(request.getId());
        if (reviewToUpdate.isPresent()) {
            reviewToUpdate.get().setText(request.getText());
            reviewToUpdate.get().setRating(request.getRating());
            reviewToUpdate = Optional.ofNullable(reviewService.save(reviewToUpdate.get()));
            return new ResponseEntity<>(reviewToUpdate, HttpStatus.OK);
        }
        return new ResponseEntity<>("Review was not found", HttpStatus.NOT_FOUND);
    } // updateReview() ends here

} // class ends here
