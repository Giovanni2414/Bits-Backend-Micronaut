package edu.co.icesi.error;

import edu.co.icesi.error.exception.VarxenPerformanceError;
import edu.co.icesi.error.exception.VarxenPerformanceException;
import io.micronaut.context.annotation.Requires;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Produces;
import io.micronaut.http.server.exceptions.ExceptionHandler;
import jakarta.inject.Singleton;

@Produces
@Singleton
@Requires(classes = {ExceptionHandler.class, VarxenPerformanceException.class})
public class VarxenExceptionHandler implements ExceptionHandler<VarxenPerformanceException, HttpResponse<VarxenPerformanceError>> {

    @Override
    public HttpResponse<VarxenPerformanceError> handle(HttpRequest request, VarxenPerformanceException exception) {
        return HttpResponse.status(exception.getHttpStatus()).body(exception.getError());
    }
}
