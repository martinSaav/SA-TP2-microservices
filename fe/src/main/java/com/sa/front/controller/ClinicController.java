package com.sa.front.controller;

import com.sa.front.interfaces.IClinicService;
import com.sa.front.model.Clinic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.result.view.Rendering;
import reactor.core.publisher.Mono;

@Controller
@RequestMapping("/clinics")
public class ClinicController {

    @Autowired
    private IClinicService clinicService;


    @GetMapping("/{id}")
    public Mono<Rendering> findById(@PathVariable Integer id) {
        return Mono.just(Rendering.view("clinic/clinic-details").modelAttribute("clinic", clinicService.findById(id)).build());
    }

    @GetMapping("")
    public Mono<Rendering> search(@RequestParam(required = false, defaultValue = "") String filter,
                                  @RequestParam(required = false, defaultValue = "0") Integer page,
                                  @RequestParam(required = false, defaultValue = "5") Integer size,
                                  @RequestParam(required = false, defaultValue = "") String sort) {
        return clinicService
                .search(filter, page, size, sort)
                .map(paginationResponseDto -> Rendering.view("clinic/clinics")
                        .modelAttribute("filter", filter)
                        .modelAttribute("clinics", paginationResponseDto.getContent())
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
                .onErrorResume(throwable -> Mono.just(Rendering.view("clinic/clinics")
                        .modelAttribute("filter", filter)
                        .modelAttribute("clinics", clinicService.findAll())
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
        return Mono.just(Rendering.view("clinic/clinic-create").modelAttribute("clinic", new Clinic()).build());
    }

    @GetMapping("/update/{id}")
    public Mono<Rendering> updatePage(@PathVariable Integer id) {
        return Mono.just(Rendering.view("clinic/clinic-update").modelAttribute("clinic", clinicService.findById(id)).build());
    }

    @PostMapping("")
    public Mono<Rendering> save(@ModelAttribute Clinic clinic) {
        return clinicService
                .save(clinic)
                .then(Mono
                        .just(Rendering
                                .redirectTo("/clinics")
                                .build()
                        )
                );
    }

    @PutMapping("/{id}")
    public Mono<Rendering> update(@PathVariable Integer id, @ModelAttribute @RequestBody Clinic clinic) {
        return clinicService
                .update(id, clinic)
                .then(Mono
                        .just(Rendering
                                .redirectTo("/clinics").build()
                        )
                );
    }

    @DeleteMapping("/{id}")
    public Mono<Rendering> delete(@PathVariable Integer id) {
        return Mono.just(Rendering.view("clinic/clinics").modelAttribute("clinics", clinicService.delete(id)).build());
    }
}
