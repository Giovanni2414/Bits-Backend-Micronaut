package edu.co.icesi.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PerformanceTest {

    private String testingFrameworkPlatform;

    private int weight;

    private Session session;
}
