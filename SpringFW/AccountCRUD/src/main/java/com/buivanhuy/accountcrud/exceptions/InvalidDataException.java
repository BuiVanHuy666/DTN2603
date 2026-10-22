package com.buivanhuy.accountcrud.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.ErrorResponseException;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.*;

@Getter
public class InvalidDataException extends ErrorResponseException {

    private final Map<String, List<String>> errors = new HashMap<>();

    public InvalidDataException(MethodArgumentNotValidException ex) {
        super(HttpStatus.UNPROCESSABLE_CONTENT);

        ex.getBindingResult().getFieldErrors().forEach(error -> {
            this.errors.computeIfAbsent(error.getField(), _ -> new ArrayList<>())
                    .add(error.getDefaultMessage());
        });
    }

}