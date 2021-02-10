package com.team_project.shop.domain.product;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;

import com.team_project.shop.domain.BaseEntity;
import com.team_project.shop.domain.user.Role;
import com.team_project.shop.domain.user.Users;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@EqualsAndHashCode
@NoArgsConstructor
@Getter
@Entity
@AttributeOverride(name="id", column= @Column(name="IMAGE_ID"))
public class Images extends BaseEntity{

	@Column(nullable = false)
	private String imageName;
	
	@Column(nullable = false)
	private String imageURL;
	
}
