package com.team_project.shop.domain.product.Informations;

import com.team_project.shop.domain.BaseEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Entity
@ToString
@Inheritance(strategy = InheritanceType.JOINED)
@AttributeOverride(name="id", column= @Column(name="INFORMATIONS_ID"))
@DiscriminatorColumn(name="PRODUCT_TYPE")
public abstract class Informations extends BaseEntity{
}

