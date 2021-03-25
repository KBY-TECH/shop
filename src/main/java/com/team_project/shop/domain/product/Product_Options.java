package com.team_project.shop.domain.product;

import javax.persistence.*;

import com.team_project.shop.config.ShopException;
import com.team_project.shop.domain.BaseEntity;

import com.team_project.shop.domain.product.Informations.Informations;
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
	@Enumerated(EnumType.STRING)
	private ProductState state;

	@OneToOne
	@JoinColumn(name="INFORMATION_ID")
	private Informations information;


	@Builder
	public Product_Options(String optionName, Products product,Images mainImage, Images detailImage,  Long price, Long stock, Informations information){
		this.optionName = optionName;
		this.product = product;
		this.mainImage = mainImage;
		this.detailImage = detailImage;
		this.price = price;
		this.stock = stock;
		if(stock>0)	this.state = ProductState.ONSALE;
		else this.state = ProductState.OUTOFSTOCK;
		this.information = information;
	}
	public void update(String optionName, Images mainImage, Images detailImage, Long price, Long stock, String state){
		this.optionName = optionName;
		this.mainImage = mainImage;
		this.detailImage = detailImage;
		this.price = price;
		this.stock = stock;
		this.state = ProductState.find(state);
	}

	//재고가 부족할 시 서비스를 통해 에러메시지를 전달할 수 있도록 함
	public void removeStock(Long quantity) throws ShopException {
		if (this.state==ProductState.SUSPENSION) throw new ShopException("판매 중지된 상품", 100);

		long restStock = this.stock - quantity;
		if (restStock <0){
			throw new ShopException("재고 부족",200);
		}
		else if(restStock==0) {
			this.state = ProductState.OUTOFSTOCK;
		}
		this.stock = restStock;
	}

	public void stopSelling(){
		this.state = ProductState.SUSPENSION;
	}
}
