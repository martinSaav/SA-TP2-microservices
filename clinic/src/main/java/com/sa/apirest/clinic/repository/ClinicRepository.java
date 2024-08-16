package com.sa.apirest.clinic.repository;


import com.sa.apirest.clinic.model.Clinic;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;


@Repository
public interface ClinicRepository extends BaseRepository<Clinic, Integer> {

    @Query(nativeQuery = true)
    List<Clinic> searchNative(@Param("filter") String filter);

    @Query(nativeQuery = true)
    Page<Clinic> searchNative(@Param("filter") String filter, Pageable pageable);
}
