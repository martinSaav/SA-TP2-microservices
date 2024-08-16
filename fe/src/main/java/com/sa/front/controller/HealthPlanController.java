package com.sa.front.controller;

import com.sa.front.interfaces.IHealthPlanService;
import com.sa.front.model.HealthPlan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.result.view.Rendering;
import reactor.core.publisher.Mono;

@Controller
@RequestMapping("/healthplans")
public class HealthPlanController {

    @Autowired
    private IHealthPlanService healthPlanService;

    @GetMapping("/{id}")
    public Mono<Rendering> findById(@PathVariable Integer id) {
        return Mono.just(Rendering.view("healthplan/healthplan-details").modelAttribute("healthplan", healthPlanService.findById(id)).build());
    }

    @GetMapping("")
    public Mono<Rendering> search(@RequestParam(required = false, defaultValue = "") String filter,
                                  @RequestParam(required = false, defaultValue = "0") Integer page,
                                  @RequestParam(required = false, defaultValue = "5") Integer size,
                                  @RequestParam(required = false, defaultValue = "") String sort) {
        return healthPlanService
                .search(filter, page, size, sort)
                .map(paginationResponseDto -> Rendering.view("healthplan/healthplans")
                        .modelAttribute("filter", filter)
                        .modelAttribute("healthplans", paginationResponseDto.getContent())
                        .modelAttribute("page", paginationResponseDto.getNumber())
                        .modelAttribute("size", paginationResponseDto.getSize())
                        .modelAttribute("totalPages", paginationResponseDto.getTotalPages())
                        .modelAttribute("totalElements", paginationResponseDto.getTotalElements())
                        .modelAttribute("sort", paginationResponseDto.getSort())
                        .modelAttribute("first", paginationResponseDto.getFirst())
                        .modelAttribute("last", paginationResponseDto.getLast())
                        .modelAttribute("numberOfElements", paginationResponseDto.getNumberOfElements())
                        .build()
                )
                .onErrorResume(throwable -> Mono.just(Rendering.view("healthplan/healthplans")
                        .modelAttribute("filter", filter)
                        .modelAttribute("healthplans", healthPlanService.findAll())
                        .modelAttribute("page", 0)
                        .modelAttribute("size", 5)
                        .modelAttribute("totalPages", 0)
                        .modelAttribute("totalElements", 0)
                        .modelAttribute("sort", "")
                        .modelAttribute("first", true)
                        .modelAttribute("last", true)
                        .modelAttribute("numberOfElements", 0)
                        .build()));
    }

    @GetMapping("/create")
    public Mono<Rendering> createPage() {
        return Mono.just(Rendering.view("healthplan/healthplan-create").modelAttribute("healthplan", new HealthPlan()).build());
    }

    @GetMapping("/update/{id}")
    public Mono<Rendering> updatePage(@PathVariable Integer id) {
        return Mono.just(Rendering.view("healthplan/healthplan-update").modelAttribute("healthplan", healthPlanService.findById(id)).build());
    }


    @PostMapping("")
    public Mono<Rendering> save(@ModelAttribute HealthPlan healthPlan) {
        return healthPlanService
                .save(healthPlan)
                .then(Mono
                        .just(Rendering
                                .redirectTo("/healthplans")
                                .build()
                        )
                );
    }

    @PutMapping("/{id}")
    public Mono<Rendering> update(@PathVariable Integer id, @ModelAttribute @RequestBody HealthPlan healthPlan) {
        return healthPlanService
                .update(id, healthPlan)
                .then(Mono
                        .just(Rendering
                                .redirectTo("/healthplans").build()
                        )
                );
    }

    @DeleteMapping("/{id}")
    public Mono<Rendering> delete(@PathVariable Integer id) {
        return Mono.just(Rendering.view("healthplan/healthplans").modelAttribute("healthplans", healthPlanService.delete(id)).build());
    }
}
