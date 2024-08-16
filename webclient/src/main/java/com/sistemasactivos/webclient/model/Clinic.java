package com.sistemasactivos.webclient.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Clinic {

    private Integer id;

    private String nameClinic;

    private Integer zoneId;

    private Integer healthPlanId;
}
