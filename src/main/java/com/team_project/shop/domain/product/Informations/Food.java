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
@AttributeOverride(name="id", column= @Column(name="FOODS_ID"))
@DiscriminatorValue("FOOD")
public abstract class Food extends Informations {
    @Column(nullable = false)
    private String producer;

    @Column(nullable = false)
    private String qualityMaintenanceDate;

    @Column(nullable = false)
    private String importedFood;

    @Column(nullable = false)
    private String precaution;

    @Column(nullable = false)
    private String consumerCounselingPhoneNum;

    public Food(String producer, String qualityMaintenanceDate, String importedFood,
                String precaution, String consumerCounselingPhoneNum) {
        this.producer = producer;
        this.qualityMaintenanceDate = qualityMaintenanceDate;
        this.importedFood = importedFood;
        this.precaution = precaution;
        this.consumerCounselingPhoneNum = consumerCounselingPhoneNum;
    }
}
