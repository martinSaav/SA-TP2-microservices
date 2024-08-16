package com.sa.front.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.reactive.result.view.Rendering;
import reactor.core.publisher.Mono;

@Controller
public class MainController {
    @GetMapping("/")
    public Mono<Rendering> index() {
        return Mono.just(Rendering.view("index").build());
    }
}
