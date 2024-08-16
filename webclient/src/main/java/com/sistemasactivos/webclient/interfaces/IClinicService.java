package com.sistemasactivos.webclient.interfaces;


import com.sistemasactivos.webclient.dto.PaginationResponseDto;
import com.sistemasactivos.webclient.model.Clinic;
import com.sistemasactivos.webclient.model.HealthPlan;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


public interface IClinicService{
    Flux<Clinic> findAll();

    Mono<PaginationResponseDto> findAllPaged(Integer page, Integer size, String sort);

    Mono<Clinic> findById(Integer id);

    Flux<Clinic> search(String filter);

    Mono<PaginationResponseDto> searchPaged(String filter, Integer page, Integer size, String sort);

    Mono<Clinic> save(Clinic clinic);

    Mono<Clinic> update(Integer id, Clinic clinic);

    Mono<Void> delete(Integer id);
}

