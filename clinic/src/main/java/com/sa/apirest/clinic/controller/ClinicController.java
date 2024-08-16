package com.sa.apirest.clinic.controller;


import com.sa.apirest.clinic.exception.SearchNoElements;
import com.sa.apirest.clinic.exception.UnknownErrorException;
import com.sa.apirest.clinic.model.Clinic;
import com.sa.apirest.clinic.service.ClinicServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "/api/v1/clinics")
public class ClinicController extends BaseControllerImpl<Clinic, ClinicServiceImpl> {

    @Autowired
    private ClinicServiceImpl clinicPlanService;

    public ClinicController(ClinicServiceImpl baseService) {
        super(baseService);
    }

    @Operation(
            description = "Busqueda avanzada",
            parameters = {
                    @Parameter(name = "filter", description = "Filtro con el cual se buscara por el nombre de la clinica", required = true)
            },
            responses = {
                    @ApiResponse(responseCode = "200", ref = "okAPI"),
                    @ApiResponse(responseCode = "401", ref = "unauthorizedAPI"),
                    @ApiResponse(responseCode = "404", ref = "notFoundAPI"),
                    @ApiResponse(responseCode = "500", ref = "internalServerErrorAPI")
            }
    )
    @GetMapping("/search")
    public ResponseEntity<?> search(@RequestParam String filter) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(clinicPlanService.search(filter));
        } catch (IllegalArgumentException e) {
            throw new SearchNoElements(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            throw new UnknownErrorException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(
            description = "Busqueda avanzada con registros limitados",
            parameters = {
                    @Parameter(name = "filter", description = "Filtro con el cual se buscara por el nombre de la clinica", required = true),
                    @Parameter(name = "pageable", description = "Objeto Pageable", required = true)
            },
            responses = {
                    @ApiResponse(responseCode = "200", ref = "okAPI"),
                    @ApiResponse(responseCode = "401", ref = "unauthorizedAPI"),
                    @ApiResponse(responseCode = "404", ref = "notFoundAPI"),
                    @ApiResponse(responseCode = "500", ref = "internalServerErrorAPI")
            }
    )
    @GetMapping("/searchPaged")
    public ResponseEntity<?> search(@RequestParam String filter, Pageable pageable) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(clinicPlanService.search(filter, pageable));
        } catch (IllegalArgumentException e) {
            throw new SearchNoElements(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            throw new UnknownErrorException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
