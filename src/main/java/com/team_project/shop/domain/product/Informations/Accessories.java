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
    @Column(nullable = false)
    private String kind;

    @Column(nullable = false)
    private String material;

    @Column(nullable = false)
    private String size;

    @Column(nullable = false)
    private String producer;

    @Column(nullable = false)
    private String madeIn;

    @Column(nullable = false)
    private String precautions;

    @Column(nullable = false)
    private String qualityAssuranceStandard;

    @Column(nullable = false)
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
}
