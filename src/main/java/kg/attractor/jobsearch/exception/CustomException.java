package kg.attractor.jobsearch.exception;

import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus
public class CustomException extends RuntimeException {
    public CustomException(String message) {
        super(message);
    }
}
