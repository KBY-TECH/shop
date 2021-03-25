package com.team_project.shop.domain.product.Informations;

import lombok.*;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Getter
@Entity
@ToString
@AttributeOverride(name="id", column= @Column(name="ACCESSORIES_ID"))
@DiscriminatorValue("ACCESSORIES")
public abstract class Accessories extends Informations {
    @Column
    private String kind;

    @Column
    private String material;

    @Column
    private String size;

    @Column
    private String producer;

    @Column
    private String madeIn;

    @Column
    private String precautions;

    @Column
    private String qualityAssuranceStandard;

    @Column
    private String afterServiceAddress;

    public Accessories(String kind, String material, String size,
                    String producer, String madeIn, String precautions,
                    String qualityAssuranceStandard, String afterServiceAddress) {
        this.kind = kind;
        this.material = material;
        this.size = size;
        this.producer = producer;
        this.madeIn = madeIn;
        this.precautions = precautions;
        this.qualityAssuranceStandard = qualityAssuranceStandard;
        this.afterServiceAddress = afterServiceAddress;
    }

    public void update(String kind, String material, String size,
                       String producer, String madeIn, String precautions,
                       String qualityAssuranceStandard, String afterServiceAddress) {
        this.kind = kind;
        this.material = material;
        this.size = size;
        this.producer = producer;
        this.madeIn = madeIn;
        this.precautions = precautions;
        this.qualityAssuranceStandard = qualityAssuranceStandard;
        this.afterServiceAddress = afterServiceAddress;
    }
}
