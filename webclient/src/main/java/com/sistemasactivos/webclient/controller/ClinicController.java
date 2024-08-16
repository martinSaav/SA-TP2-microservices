package com.sistemasactivos.webclient.controller;

import com.sistemasactivos.webclient.interfaces.IClinicService;
import com.sistemasactivos.webclient.model.Clinic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/clinics")
public class ClinicController {

    @Autowired
    private IClinicService clinicService;

    @GetMapping("")
    public ResponseEntity<?> findAll() {
        return ResponseEntity.status(HttpStatus.OK).body(clinicService.findAll());
    }


    @GetMapping("/paged")
    public ResponseEntity<?> findAll(@RequestParam(required = false, defaultValue = "0") Integer page,
                                     @RequestParam(required = false, defaultValue = "50") Integer size,
                                     @RequestParam(required = false, defaultValue = "") String sort) {
        return ResponseEntity.status(HttpStatus.OK).body(clinicService.findAllPaged(page, size, sort));
    }


    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable("id") Integer id) {
        return ResponseEntity.status(HttpStatus.OK).body(clinicService.findById(id));
    }

    @GetMapping("/search")
    public ResponseEntity<?> search(@RequestParam String filter) {
        return ResponseEntity.status(HttpStatus.OK).body(clinicService.search(filter));
    }

    @GetMapping("/searchPaged")
    public ResponseEntity<?> searchPaged(@RequestParam(required = false, defaultValue = "") String filter,
                                         @RequestParam(required = false, defaultValue = "0") Integer page,
                                         @RequestParam(required = false, defaultValue = "50") Integer size,
                                         @RequestParam(required = false, defaultValue = "") String sort) {
        return ResponseEntity.status(HttpStatus.OK).body(clinicService.searchPaged(filter, page, size, sort));
    }

    @PostMapping("")
    public ResponseEntity<?> save(@RequestBody Clinic user) {
        return ResponseEntity.status(HttpStatus.CREATED).body(clinicService.save(user));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable("id") Integer id, @RequestBody Clinic clinic) {
        return ResponseEntity.status(HttpStatus.OK).body(clinicService.update(id, clinic));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Integer id) {
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(clinicService.delete(id));
    }
}