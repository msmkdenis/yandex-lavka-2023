package ru.burtsev.yandexlavka2023.exception;

import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.*;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.net.URI;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss SSS z")
            .withZone(ZoneId.systemDefault());

    private static final String TIMESTAMP = "timestamp";

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatusCode status,
                                                                  WebRequest request) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, ex.getMessage());
        problemDetail.setTitle("Bad request");
        problemDetail.setType(URI.create("http://yandex-lavka-2023:8080/errors/bad-request"));
        problemDetail.setStatus(HttpStatus.BAD_REQUEST);
        problemDetail.setProperty(TIMESTAMP, formatter.format(Instant.now()));
        return new ResponseEntity<>(problemDetail, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BadRequest.class)
    ProblemDetail handleBadRequest(BadRequest e) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, e.getMessage());
        problemDetail.setTitle("Bad request");
        problemDetail.setType(URI.create("http://yandex-lavka-2023:8080/errors/bad-request"));
        problemDetail.setProperty(TIMESTAMP, formatter.format(Instant.now()));
        return problemDetail;
    }

    @ExceptionHandler(NotFound.class)
    ProblemDetail handleNotFound(NotFound e) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, e.getMessage());
        problemDetail.setTitle("Not found");
        problemDetail.setType(URI.create("http://yandex-lavka-2023:8080/errors/not-found"));
        problemDetail.setProperty(TIMESTAMP, formatter.format(Instant.now()));
        return problemDetail;
    }



    @ExceptionHandler(DateTimeParseException.class)
    ProblemDetail handleDateTimeParseException(DateTimeParseException e) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(
                HttpStatus.BAD_REQUEST,
                "Wrong working hours format, must be HH:mm-HH:mm. " + e.getMessage());
        problemDetail.setTitle("Bad request");
        problemDetail.setType(URI.create("http://yandex-lavka-2023:8080/errors/bad-request"));
        problemDetail.setProperty(TIMESTAMP, formatter.format(Instant.now()));
        return problemDetail;
    }

    @Override
    protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex,
                                                        HttpHeaders headers,
                                                        HttpStatusCode status,
                                                        WebRequest request) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, ex.getMessage());
        problemDetail.setTitle("Bad request");
        problemDetail.setType(URI.create("http://yandex-lavka-2023:8080/errors/bad-request"));
        problemDetail.setStatus(HttpStatus.BAD_REQUEST);
        problemDetail.setProperty(TIMESTAMP, formatter.format(Instant.now()));
        return new ResponseEntity<>(problemDetail, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    ProblemDetail handleConstraintViolation(ConstraintViolationException e) {
        log.info(e.getClass().toString());
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(
                HttpStatus.BAD_REQUEST,
                e.getMessage());
        problemDetail.setTitle("Bad request");
        problemDetail.setType(URI.create("http://yandex-lavka-2023:8080/errors/bad-request"));
        problemDetail.setProperty(TIMESTAMP, formatter.format(Instant.now()));
        return problemDetail;
    }


    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatusCode status,
                                                                  WebRequest request) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, ex.getMessage());
        problemDetail.setTitle("Bad request");
        problemDetail.setType(URI.create("http://yandex-lavka-2023:8080/errors/bad-request"));
        problemDetail.setStatus(HttpStatus.BAD_REQUEST);
        problemDetail.setProperty(TIMESTAMP, formatter.format(Instant.now()));
        return new ResponseEntity<>(problemDetail, HttpStatus.BAD_REQUEST);
    }
}
