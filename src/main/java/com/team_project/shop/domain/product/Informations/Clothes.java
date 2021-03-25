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
    @Column
    private String material;

    @Column
    private String color;

    @Column
    private String size;

    @Column
    private String producer;

    @Column
    private String madeIn;

    @Column
    private String precautions;

    @Column
    private String manufacturedDate;

    @Column
    private String qualityAssuranceStandard;

    @Column
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

    public void update(String material, String color, String size,
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
