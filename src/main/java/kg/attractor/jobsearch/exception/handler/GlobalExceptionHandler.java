package kg.attractor.jobsearch.exception.handler;

import kg.attractor.jobsearch.exception.CustomException;
import kg.attractor.jobsearch.exception.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomException.class)
    private ErrorResponse customExceptionHandler(CustomException e) {
        return ErrorResponse.builder(e, HttpStatus.BAD_REQUEST, e.getMessage()).build();
    }

    @ExceptionHandler(NotFoundException.class)
    public ErrorResponse NotFoundExceptionHandler(NotFoundException e) {
        return ErrorResponse.builder(e, HttpStatus.NOT_FOUND, e.getMessage()).build();
    }
}
