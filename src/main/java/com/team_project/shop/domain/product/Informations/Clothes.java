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
@AttributeOverride(name="id", column= @Column(name="CLOTHES_ID"))
@DiscriminatorValue("CLOTHES")
public abstract class Clothes extends Informations {
    @Column(nullable = false)
    private String material;

    @Column(nullable = false)
    private String color;

    @Column(nullable = false)
    private String size;

    @Column(nullable = false)
    private String producer;

    @Column(nullable = false)
    private String madeIn;

    @Column(nullable = false)
    private String precautions;

    @Column(nullable = false)
    private String manufacturedDate;

    @Column(nullable = false)
    private String qualityAssuranceStandard;

    @Column(nullable = false)
    private String afterServiceAddress;

    public Clothes(String material, String color, String size,
                   String producer, String madeIn, String precautions,
                   String manufacturedDate, String qualityAssuranceStandard,
                   String afterServiceAddress) {
        this.material = material;
        this.color = color;
        this.size = size;
        this.producer = producer;
        this.madeIn = madeIn;
        this.precautions = precautions;
        this.manufacturedDate = manufacturedDate;
        this.qualityAssuranceStandard = qualityAssuranceStandard;
        this.afterServiceAddress = afterServiceAddress;
    }
}
