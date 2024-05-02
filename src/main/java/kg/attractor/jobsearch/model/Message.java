package kg.attractor.jobsearch.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table (name = "messages")
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "responded_applicant_id")
    private RespondedApplicant response;
    @Lob
    private String content;
    @ManyToOne
    @JoinColumn(name = "sender_id")
    private User sender;
    private LocalDateTime timestamp;
}
