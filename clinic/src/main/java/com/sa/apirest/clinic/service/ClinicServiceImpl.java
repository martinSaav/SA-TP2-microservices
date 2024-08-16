package com.sa.apirest.clinic.service;

import com.sa.apirest.clinic.interfaces.ClinicService;
import com.sa.apirest.clinic.model.Clinic;
import com.sa.apirest.clinic.repository.BaseRepository;
import com.sa.apirest.clinic.repository.ClinicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.List;


@Service
public class ClinicServiceImpl extends BaseServiceImpl<Clinic, Integer> implements ClinicService {

    @Autowired
    private ClinicRepository clinicRepository;

    public ClinicServiceImpl(BaseRepository<Clinic, Integer> baseRepository) {
        super(baseRepository);
    }

    public List<Clinic> search(String filter) {
        List<Clinic> Clinics = clinicRepository.searchNative(filter);
        if (Clinics.isEmpty()) {
            throw new IllegalArgumentException("No se encontraron registros con el filtro: " + filter);
        }
        return clinicRepository.searchNative(filter);
    }

    @Override
    public Page<Clinic> search(String filter, Pageable pageable) {
        Page<Clinic> Clinics = clinicRepository.searchNative(filter, pageable);
        if (Clinics.isEmpty()) {
            throw new IllegalArgumentException("No se encontraron registros con el filtro: " + filter + " y paginación: " + pageable.toString());
        }
        return clinicRepository.searchNative(filter, pageable);
    }
}
