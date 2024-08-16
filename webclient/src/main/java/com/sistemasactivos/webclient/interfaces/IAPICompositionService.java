package com.sistemasactivos.webclient.interfaces;

import com.sistemasactivos.webclient.model.HealthPlanComposition;
import reactor.core.publisher.Mono;

public interface IAPICompositionService {

    Mono<HealthPlanComposition> getHealthPlanComposition(Integer id);
}
