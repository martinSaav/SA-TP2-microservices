package com.sa.front.interfaces;

import com.sa.front.dto.PaginationResponseDto;
import com.sa.front.model.Clinic;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IClinicService {

    Flux<Clinic> findAll();

    Mono<PaginationResponseDto> findAllPaged(Integer page, Integer size, String sort);

    Mono<Clinic> findById(Integer id);

    Mono<Clinic> save(Clinic healthPlan);

    Mono<Clinic> update(Integer id, Clinic healthPlan);

    Mono<Void> delete(Integer id);

    Mono<PaginationResponseDto> search(String filter, Integer page, Integer size, String sort);
}

