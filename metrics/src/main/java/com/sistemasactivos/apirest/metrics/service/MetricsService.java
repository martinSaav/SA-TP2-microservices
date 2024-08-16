package com.sistemasactivos.apirest.metrics.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.sistemasactivos.apirest.metrics.repository.MemoryUsageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import com.sistemasactivos.apirest.metrics.repository.DiskSpaceRepository;
import com.sistemasactivos.apirest.metrics.model.DiskSpace;
import com.sistemasactivos.apirest.metrics.model.MemoryUsage;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.List;

@Service
public class MetricsService {

    @Autowired
    @Qualifier("healthPlanWebClient")
    private WebClient healthPlanwebClient;

    @Autowired
    @Qualifier("clinicWebClient")
    private WebClient clinicWebClient;

    @Autowired
    private DiskSpaceRepository diskSpaceRepository;

    @Autowired
    private MemoryUsageRepository memoryUsageRepository;


    @Scheduled(fixedRate = 10000)
    public void fetchAndPersistMetrics() throws JsonProcessingException {
        List<WebClient> webClients = new ArrayList<>();
        webClients.add(healthPlanwebClient);
        webClients.add(clinicWebClient);
        Integer serviceId = 1;

        for (WebClient webClient : webClients) {
            diskSpaceMetric(webClient, serviceId);
            memoryUsageMetric(webClient, serviceId);
            serviceId++;
        }
    }

    private void diskSpaceMetric(WebClient webClient, Integer serviceId) throws JsonProcessingException {
        String diskSpaceResponse = webClient.get()
                .uri("/actuator/health/diskSpace")
                .retrieve()
                .bodyToMono(String.class)
                .block();

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode diskSpaceJson = objectMapper.readTree(diskSpaceResponse);

        JsonNode diskSpaceDetails = diskSpaceJson.get("details");

        DiskSpace diskSpace = new DiskSpace(
                "diskSpace",
                diskSpaceDetails.get("total").asLong(),
                diskSpaceDetails.get("free").asLong(),
                serviceId
        );

        diskSpaceRepository.save(diskSpace);
    }

    private void memoryUsageMetric(WebClient webClient, Integer serviceId) throws JsonProcessingException {
        String memoryMaxResponse = webClient.get()
                .uri("/actuator/metrics/jvm.memory.max")
                .retrieve()
                .bodyToMono(String.class)
                .block();

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode memoryMaxJson = objectMapper.readTree(memoryMaxResponse);

        Long memoryMax = memoryMaxJson.get("measurements").get(0).get("value").asLong();

        String memoryUsedResponse = webClient.get()
                .uri("/actuator/metrics/jvm.memory.used")
                .retrieve()
                .bodyToMono(String.class)
                .block();

        JsonNode memoryUsedJson = objectMapper.readTree(memoryUsedResponse);

        Long memoryUsed = memoryUsedJson.get("measurements").get(0).get("value").asLong();

        MemoryUsage memoryUsage = new MemoryUsage(
                "memoryUsage",
                memoryMax,
                memoryUsed,
                serviceId
        );

        memoryUsageRepository.save(memoryUsage);
    }
}
