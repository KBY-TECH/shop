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
@AttributeOverride(name="id", column= @Column(name="TOP_CLOTHES_ID"))
@DiscriminatorValue("TOP_CLOTHES")
public class Top extends Clothes {
    @Builder
    public Top(String material, String color, String size,
               String producer, String madeIn, String precautions,
               String manufacturedDate, String qualityAssuranceStandard, String afterServiceAddress) {
        super(material, color, size, producer, madeIn,
                precautions, manufacturedDate, qualityAssuranceStandard, afterServiceAddress);
    }

    public void update(String material, String color, String size,
               String producer, String madeIn, String precautions,
               String manufacturedDate, String qualityAssuranceStandard, String afterServiceAddress) {
        super.update(material, color, size, producer, madeIn,
                precautions, manufacturedDate, qualityAssuranceStandard, afterServiceAddress);
    }
}
