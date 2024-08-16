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
@Getter
@Setter
@Table(name = "disk_space")
public class DiskSpace extends Base {

    private String metricName;
    private Long totalSpace;
    private Long freeSpace;
    private Integer serviceId;

}
