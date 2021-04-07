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
    @Column
    private String foodType;


    @Column
    private String materialContent;

    @Column
    private String nutritionalIngredients;

    @Column
    private String geneticallyModified;

    @Builder
    public FrozenFoods(String foodType, String producer, String qualityMaintenanceDate,
                       String capacityByPackingUnit, String materialContent,
                       String nutritionalIngredients, String geneticallyModified,
                       String precaution, String importedFood, String consumerCounselingPhoneNum){
        super(producer,capacityByPackingUnit,qualityMaintenanceDate,importedFood,precaution,consumerCounselingPhoneNum);
        this.foodType = foodType;
        this.materialContent = materialContent;
        this.nutritionalIngredients = nutritionalIngredients;
        this.geneticallyModified = geneticallyModified;
    }

    public void update(String foodType, String producer, String qualityMaintenanceDate,
                       String capacityByPackingUnit, String materialContent,
                       String nutritionalIngredients, String geneticallyModified,
                       String precaution, String importedFood, String consumerCounselingPhoneNum){
        super.update(producer,capacityByPackingUnit,qualityMaintenanceDate,importedFood,precaution,consumerCounselingPhoneNum);
        this.foodType = foodType;
        this.materialContent = materialContent;
        this.nutritionalIngredients = nutritionalIngredients;
        this.geneticallyModified = geneticallyModified;
    }
}
