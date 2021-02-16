package com.team_project.shop.domain.product;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import com.team_project.shop.domain.BaseEntity;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Getter
@Entity
@AttributeOverride(name="id", column= @Column(name="PRODUCT_OPTION_ID"))
public class Product_Options extends BaseEntity{

	@Column(nullable = false)
	private String optionName;
	
	@ManyToOne
	@JoinColumn(name="PRODUCT_ID")
	private Products product;
	
	@OneToOne
	@JoinColumn(name="MAIN_IMAGE")
	private Images mainImage;
	
	@OneToOne
	@JoinColumn(name="DETAIL_IMAGE")
	private Images detailImage;
	
	@Column(nullable = false)
	private Long price;
	
	@Column(nullable = false)
	private Long stock;

	@Builder
	public Product_Options(String optionName, Products product,Images mainImage, Images detailImage,  Long price, Long stock){
		this.optionName = optionName;
		this.product = product;
		this.mainImage = mainImage;
		this.detailImage = detailImage;
		this.price = price;
		this.stock = stock;
	}
}
