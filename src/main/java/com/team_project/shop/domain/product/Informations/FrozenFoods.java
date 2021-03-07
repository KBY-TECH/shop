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
@AttributeOverride(name="id", column= @Column(name="FROZEN_FOODS_ID"))
@DiscriminatorValue("FROZEN_FOOD")
public class FrozenFoods extends Food {
    @Column(nullable = false)
    private String foodType;

    @Column(nullable = false)
    private String capacityByPackingUnit;

    @Column(nullable = false)
    private String materialContent;

    @Column(nullable = false)
    private String nutritionalIngredients;

    @Column(nullable = false)
    private String geneticallyModified;

    @Builder
    public FrozenFoods(String foodType, String producer, String qualityMaintenanceDate,
                       String capacityByPackingUnit, String materialContent,
                       String nutritionalIngredients, String geneticallyModified,
                       String precaution, String importedFood, String consumerCounselingPhoneNum){
        super(producer,qualityMaintenanceDate,importedFood,precaution,consumerCounselingPhoneNum);
        this.foodType = foodType;
        this.capacityByPackingUnit = capacityByPackingUnit;
        this.materialContent = materialContent;
        this.nutritionalIngredients = nutritionalIngredients;
        this.geneticallyModified = geneticallyModified;
    }
}
