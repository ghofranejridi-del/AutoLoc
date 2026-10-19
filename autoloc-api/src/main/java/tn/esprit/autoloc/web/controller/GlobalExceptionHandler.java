package tn.esprit.autoloc.web.controller;

import java.time.OffsetDateTime;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import tn.esprit.autoloc.exception.ResourceNotFoundException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiError> handleNotFound(ResourceNotFoundException exception) {
        return error(HttpStatus.NOT_FOUND, exception.getMessage());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiError> handleBadRequest(IllegalArgumentException exception) {
        return error(HttpStatus.BAD_REQUEST, exception.getMessage());
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiError> handleUnreadableBody(HttpMessageNotReadableException exception) {
        return error(HttpStatus.BAD_REQUEST, "Le corps JSON est absent ou invalide");
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ApiError> handleMethodNotAllowed(HttpRequestMethodNotSupportedException exception) {
        return error(HttpStatus.METHOD_NOT_ALLOWED, "La méthode HTTP n'est pas autorisée pour cette ressource");
    }

    private ResponseEntity<ApiError> error(HttpStatus status, String message) {
        return ResponseEntity.status(status)
                .body(new ApiError(status.value(), message, OffsetDateTime.now()));
    }

    public record ApiError(int statut, String message, OffsetDateTime horodatage) { }
}
