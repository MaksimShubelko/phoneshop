package com.es.core.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class OutOfStockException extends RuntimeException {

    public OutOfStockException() {
    }
}
