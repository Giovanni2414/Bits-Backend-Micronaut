package edu.co.icesi.service.impl;

import edu.co.icesi.constant.CodesError;
import edu.co.icesi.error.exception.VarxenPerformanceError;
import edu.co.icesi.error.exception.VarxenPerformanceException;
import edu.co.icesi.model.PerformanceTest;
import edu.co.icesi.model.Session;
import edu.co.icesi.repository.SessionRepository;
import edu.co.icesi.service.BlobService;
import edu.co.icesi.service.PerformanceTestService;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.MediaType;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.annotation.Client;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import lombok.AllArgsConstructor;
import org.json.JSONObject;
import reactor.core.publisher.Mono;

import java.util.UUID;
import java.util.concurrent.ExecutionException;

@Singleton
@AllArgsConstructor
public class PerformanceTestImpl implements PerformanceTestService {

    BlobService blobService;

    SessionRepository sessionRepository;

    @Client("http://${performance-frameworks.locust.host}:${performance-frameworks.locust.port}")
    @Inject
    private HttpClient client;

    @Override
    public String generatePerformanceTest(PerformanceTest performanceTest) {
        UUID uuidSession = performanceTest.getSession().getSessionId();
        Session session = sessionRepository.findById(uuidSession).orElseThrow(()-> new VarxenPerformanceException(HttpStatus.BAD_REQUEST, new VarxenPerformanceError(CodesError.SESSION_NOT_FOUND.getCode(), CodesError.SESSION_NOT_FOUND.getMessage())));
        performanceTest.setSession(session);

        byte[] bites = blobService.download(performanceTest.getSession().getBlob().getBlobId());

        JSONObject file = new JSONObject(new String(bites));

        JSONObject JSONRequest = new JSONObject();
        JSONRequest.put("testingFramework", performanceTest.getTestingFrameworkPlatform());
        JSONRequest.put("weight",performanceTest.getWeight());
        JSONRequest.put("file",file);

        HttpRequest<String> request = HttpRequest.POST("/api/v1.0/performance_tests", JSONRequest.toString()).
                contentType(MediaType.APPLICATION_JSON).
                accept(MediaType.TEXT_PLAIN);


        HttpResponse<String> res;
        try {
            res = Mono.from(client.exchange(request, String.class)).toFuture().get();
            String body = res.getBody().orElseThrow(()->new RuntimeException("Err"));
            return new JSONObject(body).toString();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

}
