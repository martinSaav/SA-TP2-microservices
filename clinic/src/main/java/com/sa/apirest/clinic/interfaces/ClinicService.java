package com.sa.apirest.clinic.interfaces;


import com.sa.apirest.clinic.model.Clinic;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;


public interface ClinicService extends BaseService<Clinic, Integer> {

    List<Clinic> search(String filter) throws Exception;

    Page<Clinic> search(String filter, Pageable pageable) throws Exception;
}
