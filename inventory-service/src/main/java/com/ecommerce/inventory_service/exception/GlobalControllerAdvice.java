package com.ecommerce.inventory_service.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.net.URI;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
@Slf4j
public class GlobalControllerAdvice {


    @ExceptionHandler(ResourceNotFoundException.class)
    public ProblemDetail handleResourceNotFoundException (ResourceNotFoundException ex, WebRequest webRequest){

        log.warn("Recurso no encontrado - Path : {} , Message: {} ", webRequest.getDescription(false), ex.getMessage());

        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, ex.getMessage());
        problemDetail.setTitle("Recurso no encontrado");
        problemDetail.setType(URI.create("http://api.ecommerce.com/errors/not-found"));
        problemDetail.setProperty("Timestamp", Instant.now());
        problemDetail.setProperty("Resource", ex.getResource() );
        problemDetail.setProperty("Campo", ex.getName() );
        problemDetail.setProperty("Value",  ex.getFieldValue());
        return problemDetail;
    }

    //Validacion del notBlank que se pone en el record
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ProblemDetail handleResourceBadRequestException (MethodArgumentNotValidException ex, WebRequest webRequest){

        log.warn("Bad Request - Path : {} , Message: {} ", webRequest.getDescription(false), ex.getMessage());

        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, "La validacion fallo en uno o mas campos");
        problemDetail.setTitle("Validacion");
        problemDetail.setType(URI.create("http://api.ecommerce.com/errors/bad-request"));
        problemDetail.setProperty("Timestamp", Instant.now());
        Map<String, String> errorsMap = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach( fieldError -> {
            errorsMap.put(fieldError.getField(), fieldError.getDefaultMessage());
        });
        problemDetail.setProperty("errors", errorsMap);
        return problemDetail;
    }

    @ExceptionHandler(Exception.class)
    public ProblemDetail handleResourceException (Exception ex, WebRequest webRequest){

        log.warn("A ocurrido un error inesperado {} : {} ", webRequest.getDescription(false), ex.getMessage(), ex);

        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR, "A ocurrido un error inesperado. Por favor, contactar con el administrador");
        problemDetail.setTitle("Internal Server Error");
        problemDetail.setType(URI.create("http://api.ecommerce.com/errors/internal-server-error"));
        problemDetail.setProperty("Timestamp", Instant.now());
        return problemDetail;
    }


}
