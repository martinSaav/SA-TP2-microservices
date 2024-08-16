package com.sistemasactivos.apirest.metrics.repository;

import com.sistemasactivos.apirest.metrics.model.DiskSpace;
import org.springframework.stereotype.Repository;

@Repository
public interface DiskSpaceRepository extends BaseRepository<DiskSpace, Long> {

}
