package kg.attractor.jobsearch.exception.handler;

import jakarta.servlet.http.HttpServletRequest;
import kg.attractor.jobsearch.exception.ForbiddenException;
import kg.attractor.jobsearch.exception.NotFoundException;
import kg.attractor.jobsearch.exception.UserAlreadyExistsException;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public String NotFoundExceptionHandler(NotFoundException e, Model model, HttpServletRequest request) {
        model.addAttribute("status", HttpStatus.NOT_FOUND.value());
        model.addAttribute("reason", HttpStatus.NOT_FOUND.getReasonPhrase());
        model.addAttribute("message", e.getMessage());
        model.addAttribute("details", request);
        return "/error/error";
    }

    @ExceptionHandler(ForbiddenException.class)
    public String ForbiddenExceptionHandler(Model model, HttpServletRequest request, ForbiddenException e) {
        model.addAttribute("status", HttpStatus.FORBIDDEN.value());
        model.addAttribute("reason", HttpStatus.FORBIDDEN.getReasonPhrase());
        model.addAttribute("message", e.getMessage());
        model.addAttribute("details", request);
        return "/error/error";
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public String IllegalArgumentExceptionHandler(Model model, HttpServletRequest request, IllegalArgumentException e) {
        model.addAttribute("status", HttpStatus.BAD_REQUEST.value());
        model.addAttribute("reason", HttpStatus.BAD_REQUEST.getReasonPhrase());
        model.addAttribute("message", e.getMessage());
        model.addAttribute("details", request);
        return "/error/error";
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public String UserAlreadyExistsException(Model model, HttpServletRequest request, UserAlreadyExistsException e) {
        model.addAttribute("status", HttpStatus.CONFLICT.value());
        model.addAttribute("reason", HttpStatus.CONFLICT.getReasonPhrase());
        model.addAttribute("message", e.getMessage());
        model.addAttribute("details", request);
        return "/error/error";
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ErrorResponse MethodArgumentNotValidExceptionHandler (MethodArgumentNotValidException e) {
        return ErrorResponse.builder(e, HttpStatus.BAD_REQUEST, e.getMessage()).build();
    }
}
