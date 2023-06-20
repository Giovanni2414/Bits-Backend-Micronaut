package edu.co.icesi.error;

import edu.co.icesi.constant.CodesError;
import edu.co.icesi.constants.ErrorConstants;
import edu.co.icesi.error.exception.VarxenPerformanceError;
import edu.co.icesi.error.exception.VarxenPerformanceException;
import io.micronaut.context.annotation.Primary;
import io.micronaut.context.annotation.Requires;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.annotation.Produces;
import io.micronaut.http.server.exceptions.ExceptionHandler;
import jakarta.inject.Singleton;

import javax.validation.ConstraintViolationException;
import java.util.Objects;

@Produces
@Singleton
@Primary
@Requires(classes = {ExceptionHandler.class, ConstraintViolationException.class})
public class ConstraintExceptionHandler implements ExceptionHandler<ConstraintViolationException, HttpResponse<VarxenPerformanceError>> {

    @Override
    public HttpResponse<VarxenPerformanceError> handle(HttpRequest request, ConstraintViolationException exception) {
        VarxenPerformanceError varxenError = new VarxenPerformanceError(CodesError.CONSTRAINT_VIOLATION.getCode(), Objects.requireNonNull(exception.getConstraintViolations().stream().reduce("", (s, constraintViolation) -> constraintViolation.getMessage(), (s, s2) -> s + s2)));
        VarxenPerformanceException varxenPerformanceException = new VarxenPerformanceException(HttpStatus.BAD_REQUEST, varxenError);
        return HttpResponse.status(varxenPerformanceException.getHttpStatus()).body(varxenPerformanceException.getError());
    }
}
