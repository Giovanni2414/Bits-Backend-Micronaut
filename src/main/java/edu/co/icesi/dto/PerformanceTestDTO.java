package edu.co.icesi.dto;

import io.micronaut.core.annotation.Introspected;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Introspected
public class PerformanceTestDTO {

    private String testingFrameworkPlatform;

    private int weight;

    private UUID sessionId;
}
