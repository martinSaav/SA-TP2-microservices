package com.sistemasactivos.webclient.controller;


import com.sistemasactivos.webclient.interfaces.IAPICompositionService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/healthplans-composition")
public class APICompositionController {

    @Autowired
    private IAPICompositionService apiCompositionService;

    @RequestMapping("/{id}")
    public ResponseEntity<?> getComposition(@PathVariable Integer id) {
        return ResponseEntity.status(HttpStatus.OK).body(apiCompositionService.getHealthPlanComposition(id));
    }

}
