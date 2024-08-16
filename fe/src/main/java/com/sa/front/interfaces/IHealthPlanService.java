package com.sa.front.interfaces;

import com.sa.front.dto.PaginationResponseDto;
import com.sa.front.model.HealthPlan;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IHealthPlanService {

    Flux<HealthPlan> findAll();

    Mono<PaginationResponseDto> findAllPaged(Integer page, Integer size, String sort);

    Mono<HealthPlan> findById(Integer id);

    Mono<HealthPlan> save(HealthPlan healthPlan);

    Mono<HealthPlan> update(Integer id, HealthPlan healthPlan);

    Mono<Void> delete(Integer id);

    Mono<PaginationResponseDto> search(String filter, Integer page, Integer size, String sort);
}
