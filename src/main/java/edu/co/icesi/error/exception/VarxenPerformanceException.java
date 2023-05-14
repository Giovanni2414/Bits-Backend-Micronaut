package edu.co.icesi.error.exception;

import io.micronaut.http.HttpStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class VarxenPerformanceException extends RuntimeException {

    private HttpStatus httpStatus;

    private VarxenPerformanceError error;

}
