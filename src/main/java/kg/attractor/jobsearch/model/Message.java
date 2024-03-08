package kg.attractor.jobsearch.model;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class Message {
    private Integer id;
    private Integer respondedApplicantId;
    private String content;
    private LocalDateTime timeStamp;
}
