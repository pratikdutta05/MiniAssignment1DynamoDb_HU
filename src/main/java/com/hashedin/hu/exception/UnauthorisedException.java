package com.hashedin.hu.exception;

import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;


@NoArgsConstructor
public class UnauthorisedException extends RuntimeException{

    private static final long serialVersionUID = 1L;

    public UnauthorisedException(String msg) {
        super(msg);
    }
}
