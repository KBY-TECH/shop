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
@ToString(callSuper = true)
@AttributeOverride(name="id", column= @Column(name="BACKPACK_ACCESSORIES_ID"))
@DiscriminatorValue("BACKPACK_ACCESSORIES")
public class Bag extends Accessories {
    @Column
    private String color;

    @Builder
    public Bag(String kind, String material, String color, String size,
               String producer, String madeIn, String precautions,
               String qualityAssuranceStandard, String afterServiceAddress) {
        super(kind,material, size, producer, madeIn,
                precautions, qualityAssuranceStandard, afterServiceAddress);
        this.color = color;
    }

    public void update(String kind, String material, String color, String size,
               String producer, String madeIn, String precautions,
               String qualityAssuranceStandard, String afterServiceAddress) {
        super.update(kind,material, size, producer, madeIn,
                precautions, qualityAssuranceStandard, afterServiceAddress);
        this.color = color;
    }
}
