package com.team_project.shop.domain.product;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

import com.team_project.shop.domain.BaseEntity;
import com.team_project.shop.domain.user.Role;
import com.team_project.shop.domain.user.Users;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@EqualsAndHashCode
@NoArgsConstructor
@Getter
@Entity
@AttributeOverride(name="id", column= @Column(name="CATEGORY_ID"))
public class Category extends BaseEntity{	
	
	@Column(nullable = false)
	private String name;

	@Enumerated(EnumType.STRING)
	private Classification classification;

	@ManyToOne
	@JoinColumn(name="PARENT_ID")
	private Category parent;

	@ManyToMany(mappedBy = "categories")
	private List<Products> products = new ArrayList<Products>();

	@Builder
	public Category(String name){
		this.name = name;
	}

	public void setParent(Category category){
		this.parent = category;
	}
}
