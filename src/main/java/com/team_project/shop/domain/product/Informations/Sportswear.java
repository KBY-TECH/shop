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
@AttributeOverride(name="id", column= @Column(name="SPORTSWEAR_CLOTHES_ID"))
@DiscriminatorValue("SPORTSWEAR_CLOTHES")
public class Sportswear extends Clothes {
    @Builder
    public Sportswear(String material, String color, String size,
                      String producer, String madeIn, String precautions,
                      String manufacturedDate, String qualityAssuranceStandard, String afterServiceAddress) {
        super(material, color, size, producer, madeIn,
                precautions, manufacturedDate, qualityAssuranceStandard, afterServiceAddress);
    }
}
