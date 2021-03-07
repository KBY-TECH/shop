package com.team_project.shop.domain.product;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

import com.team_project.shop.domain.BaseEntity;

import com.team_project.shop.domain.user.Users;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Getter
@Entity
@ToString
@AttributeOverride(name="id", column= @Column(name="PRODUCT_ID"))
public class Products extends BaseEntity {
	
	@Column(nullable = false)
	private String name;

	@ManyToOne
	@JoinColumn(name="USER_ID")
	private Users user;

	@ManyToMany
	@JoinTable(name="CATEGORY_PRODUCT",
			joinColumns = @JoinColumn(name="PRODUCT_ID"),
			inverseJoinColumns = @JoinColumn(name="CATEGORY_ID"))
	private List<Category> categories = new ArrayList<>();

	public void update(String name){
		this.name = name;
	}

	@Builder
	public Products(String name, Users user, Category category) {
		this.name = name;
		this.user = user;

		categories.add(category);
		category.getProducts().add(this);
		while(category.getParent() != null) {
			category = category.getParent();
			categories.add(category);
			category.getProducts().add(this);
		}
	}
}
