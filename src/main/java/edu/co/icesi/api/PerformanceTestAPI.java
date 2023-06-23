package edu.co.icesi.api;

import io.micronaut.http.annotation.Post;
import edu.co.icesi.dto.PerformanceTestDTO;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.client.annotation.Client;

@Client
public interface PerformanceTestAPI {

    @Post(consumes = MediaType.APPLICATION_JSON)
    String generatePerformanceTest(@Body PerformanceTestDTO performanceTestDTO);
}
