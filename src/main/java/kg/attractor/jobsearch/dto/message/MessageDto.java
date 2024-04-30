package kg.attractor.jobsearch.dto.message;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MessageDto {
    private String content;
    private Integer responseId;
    private Integer senderId;
    private String timeStamp;
}
