package com.team_project.shop.domain.product;

import java.util.List;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import com.team_project.shop.domain.BaseEntity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@EqualsAndHashCode
@NoArgsConstructor
@Getter
@Entity
@AttributeOverride(name="id", column= @Column(name="PRODUCT_ID"))
public class Products extends BaseEntity{
	
	@Column(nullable = false)
	private String name;
	
	@ManyToMany(mappedBy = "product")
	private List<Category> categories;
	
}
