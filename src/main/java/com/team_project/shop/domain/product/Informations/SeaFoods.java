package com.team_project.shop.domain.product.Informations;

import com.team_project.shop.domain.product.Category;
import com.team_project.shop.domain.product.Images;
import com.team_project.shop.domain.product.Product_Options;
import com.team_project.shop.domain.product.Products;
import com.team_project.shop.domain.user.Users;
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
@AttributeOverride(name="id", column= @Column(name="SEA_FOODS_ID"))
@DiscriminatorValue("SEA_FOOD")
public class SeaFoods extends Food {
    @Column
    private String foodName;

    @Column
    private String foodType;

    @Column
    private String materialContent;

    @Column
    private String nutritionalIngredients;

    @Column
    private String geneticallyModified;

    @Builder
    public SeaFoods(String producer, String qualityMaintenanceDate,
                    String importedFood, String precaution,
                    String consumerCounselingPhoneNum, String foodName,
                    String foodType, String capacityByPackingUnit, String materialContent,
                    String nutritionalIngredients, String geneticallyModified) {
        super(producer, capacityByPackingUnit, qualityMaintenanceDate, importedFood, precaution, consumerCounselingPhoneNum);
        this.foodName = foodName;
        this.foodType = foodType;
        this.materialContent = materialContent;
        this.nutritionalIngredients = nutritionalIngredients;
        this.geneticallyModified = geneticallyModified;
    }

    public void update(String producer, String qualityMaintenanceDate,
                    String importedFood, String precaution,
                    String consumerCounselingPhoneNum, String foodName,
                    String foodType, String capacityByPackingUnit, String materialContent,
                    String nutritionalIngredients, String geneticallyModified) {
        super.update(producer, capacityByPackingUnit,qualityMaintenanceDate, importedFood, precaution, consumerCounselingPhoneNum);
        this.foodName = foodName;
        this.foodType = foodType;
        this.materialContent = materialContent;
        this.nutritionalIngredients = nutritionalIngredients;
        this.geneticallyModified = geneticallyModified;
    }
}
