package com.sa.front.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sa.front.dto.ErrorDto;
import com.sa.front.dto.PaginationResponseDto;
import com.sa.front.exception.BusinessException;
import com.sa.front.interfaces.IHealthPlanService;
import com.sa.front.model.HealthPlan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.time.Duration;
import java.util.function.Function;

import static org.springframework.http.MediaType.APPLICATION_JSON;

@Service
public class HealthPlanService implements IHealthPlanService {

    @Autowired
    private WebClient webClient;

    @Override
    public Flux<HealthPlan> findAll() {
        return webClient.get()
                .uri("/healthplans")
                .retrieve()
                .bodyToFlux(HealthPlan.class)
                .onErrorMap(WebClientResponseException.class, procesarErrorRespuesta())
                .timeout(Duration.ofMillis(100000));
    }


    @Override
    public Mono<PaginationResponseDto> findAllPaged(Integer page, Integer size, String sort) {
        return webClient.get()
                .uri("/healthplans/paged?page=" + page + "&size=" + size + "&sort=" + sort)
                .retrieve()
                .bodyToMono(PaginationResponseDto.class)
                .onErrorMap(WebClientResponseException.class, procesarErrorRespuesta())
                .timeout(Duration.ofMillis(100000));
    }


    @Override
    public Mono<HealthPlan> findById(Integer id) {
        return webClient.get()
                .uri("/healthplans/" + id)
                .retrieve()
                .bodyToMono(HealthPlan.class)
                .onErrorMap(WebClientResponseException.class, procesarErrorRespuesta());
    }

    @Override
    public Mono<HealthPlan> save(HealthPlan healthPlan) {
        return webClient.post()
                .uri("/healthplans")
                .body(Mono.just(healthPlan), HealthPlan.class)
                .retrieve()
                .bodyToMono(HealthPlan.class)
                .onErrorMap(WebClientResponseException.class, procesarErrorRespuesta());
    }

    @Override
    public Mono<HealthPlan> update(Integer id, HealthPlan healthPlan) {
        return webClient.put()
                .uri("/healthplans/" + id)
                .contentType(APPLICATION_JSON)
                .body(Mono.just(healthPlan), HealthPlan.class)
                .retrieve()
                .bodyToMono(HealthPlan.class)
                .onErrorMap(WebClientResponseException.class, procesarErrorRespuesta());
    }

    @Override
    public Mono<Void> delete(Integer id) {
        return webClient.delete()
                .uri("/healthplans/" + id)
                .retrieve()
                .bodyToMono(Void.class)
                .onErrorMap(WebClientResponseException.class, procesarErrorRespuesta());
    }

    @Override
    public Mono<PaginationResponseDto> search(String filter, Integer page, Integer size, String sort) {
        return webClient.get()
                .uri("/healthplans/searchPaged?filter=" + filter + "&page=" + page + "&size=" + size + "&sort=" + sort)
                .retrieve()
                .bodyToMono(PaginationResponseDto.class)
                .onErrorMap(WebClientResponseException.class, procesarErrorRespuesta());
    }

    private static Function<WebClientResponseException, Throwable> procesarErrorRespuesta() {
        return ex -> {
            try {
                ErrorDto errorResponse = new ObjectMapper().readValue(ex.getResponseBodyAsString(), ErrorDto.class);
                return new BusinessException(errorResponse.getMessage(), HttpStatus.valueOf(ex.getStatusCode().value()));
            } catch (JsonProcessingException e) {
                throw new BusinessException("Error al procesar la respuesta", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        };
    }
}
