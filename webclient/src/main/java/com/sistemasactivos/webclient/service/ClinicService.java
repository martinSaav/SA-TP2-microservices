package com.sistemasactivos.webclient.service;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sistemasactivos.webclient.dto.ErrorDto;
import com.sistemasactivos.webclient.dto.PaginationResponseDto;
import com.sistemasactivos.webclient.exception.BusinessException;
import com.sistemasactivos.webclient.interfaces.IClinicService;
import com.sistemasactivos.webclient.model.Clinic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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
public class ClinicService implements IClinicService {


    @Autowired
    @Qualifier("clinicWebClient")
    private WebClient webClient;

    @Override
    public Flux<Clinic> findAll() {
        return webClient.get()
                .uri("/clinics")
                .retrieve()
                .bodyToFlux(Clinic.class)
                .onErrorMap(WebClientResponseException.class, procesarErrorRespuesta())
                .timeout(Duration.ofMillis(100_000));
    }

    @Override
    public Mono<PaginationResponseDto> findAllPaged(Integer page, Integer size, String sort) {
        return webClient.get()
                .uri("/clinics/paged?page=" + page + "&size=" + size + "&sort=" + sort)
                .retrieve()
                .bodyToMono(PaginationResponseDto.class)
                .onErrorMap(WebClientResponseException.class, procesarErrorRespuesta())
                .timeout(Duration.ofMillis(100_000));
    }

    @Override
    public Mono<Clinic> findById(Integer id) {
        return webClient.get()
                .uri("/clinics/" + id)
                .retrieve()
                .bodyToMono(Clinic.class)
                .onErrorMap(WebClientResponseException.class, procesarErrorRespuesta())
                .timeout(Duration.ofMillis(10_000))
                .switchIfEmpty(Mono.error(new BusinessException("Tiempo de espera muy largo", HttpStatus.NOT_FOUND)));
    }

    @Override
    public Flux<Clinic> search(String filter) {
        return webClient.get()
                .uri("/clinics/search?filter=" + filter)
                .retrieve()
                .bodyToFlux(Clinic.class)
                .onErrorMap(WebClientResponseException.class, procesarErrorRespuesta());
    }

    @Override
    public Mono<PaginationResponseDto> searchPaged(String filter, Integer page, Integer size, String sort) {
        return webClient.get()
                .uri("/clinics/searchPaged?filter=" + filter + "&page=" + page + "&size=" + size + "&sort=" + sort)
                .retrieve()
                .bodyToMono(PaginationResponseDto.class)
                .onErrorMap(WebClientResponseException.class, procesarErrorRespuesta());
    }

    @Override
    public Mono<Clinic> save(Clinic clinic) {
        return webClient.post()
                .uri("/clinics")
                .contentType(APPLICATION_JSON)
                .body(Mono.just(clinic), Clinic.class)
                .retrieve()
                .bodyToMono(Clinic.class)
                .onErrorMap(WebClientResponseException.class, procesarErrorRespuesta());
    }

    @Override
    public Mono<Clinic> update(Integer id, Clinic clinic) {
        return webClient.put()
                .uri("/clinics/" + id)
                .contentType(APPLICATION_JSON)
                .body(Mono.just(clinic), Clinic.class)
                .retrieve()
                .bodyToMono(Clinic.class)
                .onErrorMap(WebClientResponseException.class, procesarErrorRespuesta());
    }

    @Override
    public Mono<Void> delete(Integer id) {
        return webClient.delete()
                .uri("/clinics/" + id)
                .retrieve()
                .bodyToMono(Void.class)
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


