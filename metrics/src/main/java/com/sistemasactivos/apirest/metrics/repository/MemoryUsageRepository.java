package com.sistemasactivos.apirest.metrics.repository;

import com.sistemasactivos.apirest.metrics.model.DiskSpace;
import com.sistemasactivos.apirest.metrics.model.MemoryUsage;
import org.springframework.stereotype.Repository;


@Repository
public interface MemoryUsageRepository extends BaseRepository<MemoryUsage, Long> {

}