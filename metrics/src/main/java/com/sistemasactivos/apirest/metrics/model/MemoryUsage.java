package com.sistemasactivos.apirest.metrics.model;


import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@AllArgsConstructor
@NoArgsConstructor
@Entity
@Setter
@Getter
@Table(name = "memory_usage")
public class MemoryUsage extends Base{

    private String metricName;
    private Long MaxMemory;
    private Long UsedMemory;
    private Integer serviceId;

}
