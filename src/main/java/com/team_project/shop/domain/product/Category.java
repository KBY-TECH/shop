package com.team_project.shop.domain.product;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;

import com.team_project.shop.domain.BaseEntity;
import com.team_project.shop.domain.user.Role;
import com.team_project.shop.domain.user.Users;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

//작성자 - 이득행
//상품 테이블에 필요한 카테고리 정보를 구현하기 위해 임시로 작성.
@EqualsAndHashCode
@NoArgsConstructor
@Getter
@Entity
@AttributeOverride(name="id", column= @Column(name="CATEGORY_ID"))
public class Category extends BaseEntity{	
	
	@Column(nullable = false)
	private String name;
	
	@ManyToMany
	@JoinTable(name="CATEGORY_PRODUCT", 
		joinColumns = @JoinColumn(name="CATEGORY_ID"),
		inverseJoinColumns = @JoinColumn(name="PRODUCT_ID"))
	private List<Products> product = new ArrayList<Products>();
	
}
