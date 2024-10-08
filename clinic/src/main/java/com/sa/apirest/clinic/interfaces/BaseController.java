package com.sa.apirest.clinic.interfaces;


import com.sa.apirest.clinic.model.Base;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import java.io.Serializable;


public interface BaseController<E extends Base, ID extends Serializable> {

    ResponseEntity<?> getAllRecord();

    ResponseEntity<?> getAllRecord(Pageable pageable);

    ResponseEntity<?> getRecordById(@PathVariable ID id);

    ResponseEntity<?> save(@RequestBody E entity);

    ResponseEntity<?> update(@PathVariable ID id, @RequestBody E entity);

    ResponseEntity<?> delete(@PathVariable ID id);


}
