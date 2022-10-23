package nazmul.culture.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import nazmul.culture.domain.Band;

import java.sql.Timestamp;

@Data
public class EventRequest {
    private String venue;
    private Long bandId;
    @JsonFormat(pattern="yyyy-MM-dd")
    private Timestamp timestamp;


}
