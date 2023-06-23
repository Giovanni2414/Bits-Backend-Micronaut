package edu.co.icesi.controller;

import edu.co.icesi.api.PerformanceTestAPI;
import edu.co.icesi.dto.PerformanceTestDTO;
import edu.co.icesi.mapper.PerformanceTestMapper;
import edu.co.icesi.service.PerformanceTestService;
import io.micronaut.http.annotation.Controller;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.rules.SecurityRule;
import lombok.AllArgsConstructor;

@Controller("/performanceTest")
@AllArgsConstructor
@Secured(SecurityRule.IS_ANONYMOUS)
public class PerformanceTestController implements PerformanceTestAPI {

    private PerformanceTestService performanceTestService;

    private PerformanceTestMapper performanceTestMapper;

    @Override
    public String generatePerformanceTest(PerformanceTestDTO performanceTestDTO) {
        System.out.println("Entro al controller");
        return performanceTestService.generatePerformanceTest(performanceTestMapper.toPerformanceTest(performanceTestDTO));
    }
}
