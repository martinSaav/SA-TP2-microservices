package com.sistemasactivos.webclient.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class HealthPlanComposition {

    private Integer id;

    private String namePlan;

    private String documentPath;

    private Integer providerId;

    private Integer employId;

    private String clinics;

    private String comments;

    private List<Clinic> otherClinics;

    public HealthPlanComposition(HealthPlan healthPlan, List<Clinic> clinics) {
        this.otherClinics = clinics;
        this.id = healthPlan.getId();
        this.namePlan = healthPlan.getNamePlan();
        this.documentPath = healthPlan.getDocumentPath();
        this.providerId = healthPlan.getProviderId();
        this.employId = healthPlan.getEmployId();
        this.clinics = healthPlan.getClinics();
        this.comments = healthPlan.getComments();
    }
}
