package com.team_project.shop.domain.product;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

import com.team_project.shop.domain.BaseEntity;

import com.team_project.shop.domain.user.Users;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Getter
@Entity
@AttributeOverride(name="id", column= @Column(name="PRODUCT_ID"))
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name="PRODUCT_TYPE")
public class Products extends BaseEntity{
	
	@Column(nullable = false)
	private String name;

	@ManyToOne
	@JoinColumn(name="USER_ID")
	private Users user;

	@ManyToMany
	@JoinTable(name="CATEGORY_PRODUCT",
			joinColumns = @JoinColumn(name="PRODUCT_ID"),
			inverseJoinColumns = @JoinColumn(name="CATEGORY_ID")			)
	private List<Category> categories = new ArrayList<>();

	@Builder
	public Products(String name, Users user){
		this.name = name;
		this.user = user;
	}

	public void setCategories(Category category){
		//이미 카테고리가 연결되어있는 경우 상대방의 연결고리 해제
		for (Category cate: categories ) {
			cate.getProducts().remove(this);
		}
		categories.clear();
		//카테고리 등록시 category 엔티티에도 연결해주어야함.
		categories.add(category);
		category.getProducts().add(this);
		//상위 카테고리도 함께 등록
		while(category.getParent() != null){
			category = category.getParent();
			categories.add(category);
			category.getProducts().add(this);
		}
	}


}
