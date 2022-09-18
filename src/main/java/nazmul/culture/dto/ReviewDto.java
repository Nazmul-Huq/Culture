package nazmul.culture.dto;

import lombok.Data;

@Data
public class ReviewDto {

    private Long id;
    private String text;
    private int rating;
    private Long userId;
    private Long eventId;

}
