package com.team_project.shop.domain.product;

import javax.persistence.*;

import com.team_project.shop.config.ProductStateAttribueConverter;
import com.team_project.shop.config.ShopException;
import com.team_project.shop.domain.BaseEntity;

import lombok.*;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Getter
@Entity
@ToString
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

	@Column(nullable = false)
	@Convert(converter = ProductStateAttribueConverter.class)
	private String state;

	@Builder
	public Product_Options(String optionName, Products product,Images mainImage, Images detailImage,  Long price, Long stock){
		this.optionName = optionName;
		this.product = product;
		this.mainImage = mainImage;
		this.detailImage = detailImage;
		this.price = price;
		this.stock = stock;
		this.state = "ONSALE";
	}

	//재고가 부족할 시 서비스를 통해 에러메시지를 전달할 수 있도록 함
	public void removeStock(Long quantity) throws ShopException {
		long restStock = this.stock - quantity;
		if(restStock <0){
			throw new ShopException("재고 부족",200);
		}else if(restStock==0){
			this.state = "OUTOFSTOCK";
		}

		this.stock = restStock;
	}
}
