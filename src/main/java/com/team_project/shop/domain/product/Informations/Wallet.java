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
@AttributeOverride(name="id", column= @Column(name="WALLET_ACCESSORIES_ID"))
@DiscriminatorValue("WALLET_ACCESSORIES")
public class Wallet extends Accessories {
    @Builder
    public Wallet(String kind, String material, String size,
                    String producer, String madeIn, String precautions,
                    String qualityAssuranceStandard, String afterServiceAddress) {
        super(kind,material, size, producer, madeIn,
                precautions, qualityAssuranceStandard, afterServiceAddress);
    }
}
