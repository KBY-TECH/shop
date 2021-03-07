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
@AttributeOverride(name="id", column= @Column(name="MEATS_FOOD_ID"))
@DiscriminatorValue("MEAT_FOOD")
public class Meats extends Food {
    @Column(nullable = false)
    private String meatPart;

    @Column(nullable = false)
    private String origin;

    @Column(nullable = false)
    private String indication;

    @Column(nullable = false)
    private String composition;

    @Column(nullable = false)
    private String storageMethod;

    @Builder
    public Meats(String meatPart,String producer, String origin, String qualityMaintenanceDate,
                 String indication, String importedFood, String composition,
                 String storageMethod, String precaution, String consumerCounselingPhoneNum) {
        super(producer,qualityMaintenanceDate,importedFood,precaution,consumerCounselingPhoneNum);
        this.meatPart = meatPart;
        this.origin = origin;
        this.indication = indication;
        this.composition = composition;
        this.storageMethod = storageMethod;
    }
}
