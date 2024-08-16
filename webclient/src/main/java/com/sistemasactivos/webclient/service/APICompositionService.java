package com.sistemasactivos.webclient.service;

import com.sistemasactivos.webclient.interfaces.IAPICompositionService;
import com.sistemasactivos.webclient.interfaces.IClinicService;
import com.sistemasactivos.webclient.interfaces.IHealthPlanService;
import com.sistemasactivos.webclient.model.HealthPlanComposition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;


@Service
public class APICompositionService implements IAPICompositionService {

    @Autowired
    private IHealthPlanService healthPlanService;

    @Autowired
    private IClinicService clinicService;

    @Override
    public Mono<HealthPlanComposition> getHealthPlanComposition(Integer id) {
        return healthPlanService.findById(id)
                .flatMap(healthPlan -> clinicService.findAll()
                        .collectList()
                        .map(clinics -> new HealthPlanComposition(healthPlan, clinics)));
    }






}
