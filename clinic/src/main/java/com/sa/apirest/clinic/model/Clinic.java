package com.sa.apirest.clinic.model;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "clinic")
public class Clinic extends Base {

    @Column(name = "name_clinic", nullable = false)
    private String nameClinic;

    @Column(name = "zone_id", nullable = false)
    private Integer zoneId;

    @Column(name = "health_plan_id", nullable = false)
    private Integer healthPlanId;
}
