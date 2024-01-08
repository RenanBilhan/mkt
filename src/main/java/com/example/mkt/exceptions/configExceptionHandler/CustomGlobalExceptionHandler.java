package com.example.mkt.exceptions.configExceptionHandler;

import com.example.mkt.exceptions.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;
import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;

@ControllerAdvice
public class CustomGlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatus status,
                                                                  WebRequest request) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", new Date());
        body.put("status", status.value());

        //Get all errors
        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(x -> x.getField() + ": " + x.getDefaultMessage())
                .collect(Collectors.toList());

        body.put("errors", errors);

        return new ResponseEntity<>(body, headers, status);
    }

    private <T extends Exception> Map<String, Object> createBody(T exception) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", new Date());
        body.put("status", HttpStatus.BAD_REQUEST.value());
        body.put("message", exception.getMessage());
        return body;
    }

    @ExceptionHandler(DataBaseException.class)
    public ResponseEntity<Object> handleException(DataBaseException exception,
                                                  HttpServletRequest request) {
        Map<String, Object> body = createBody(exception);

        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Object> handleException(ConstraintViolationException exception,
                                                  HttpServletRequest request) {
        Map<String, Object> body = createBody(exception);

        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EntitiesNotFoundException.class)
    public ResponseEntity<Object> handleNotEntitiesFoundException(EntitiesNotFoundException exception,
                                                                  HttpServletRequest request) {
        Date date = new Date();
        Timestamp timestamp = new Timestamp(date.getTime());
        Map<String, Object> body = new LinkedHashMap<>();

        body.put("timestamp", timestamp);
        body.put("status", HttpStatus.NOT_FOUND.value());
        body.put("message", exception.getMessage());
        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(FormatNotValid.class)
    public ResponseEntity<Object> handleFormatNotValidException(FormatNotValid exception,
                                                                HttpServletRequest request) {
        Date date = new Date();
        Timestamp timestamp = new Timestamp(date.getTime());
        Map<String, Object> body = new LinkedHashMap<>();

        body.put("timestamp", timestamp);
        body.put("status", HttpStatus.UNSUPPORTED_MEDIA_TYPE.value());
        body.put("message", exception.getMessage());
        return new ResponseEntity<>(body, HttpStatus.UNSUPPORTED_MEDIA_TYPE);
    }

    @ExceptionHandler(BussinessRuleException.class)
    public ResponseEntity<Object> handleException(BussinessRuleException exception,
                                                  HttpServletRequest request) {
            Map<String, Object> body = createBody(exception);

            return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UniqueFieldExistsException.class)
    public ResponseEntity<Object> handleException(UniqueFieldExistsException exception,
                                                  HttpServletRequest request) {
        Map<String, String> errors = exception.getViolatedFields();


        Map<String, Object> response = new HashMap<>();
        response.put("status", HttpStatus.BAD_REQUEST.value());
        response.put("message", "Campos inválidos encontrados");
        response.put("errors", errors);

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

}
