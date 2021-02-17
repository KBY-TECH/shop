package com.team_project.shop.domain.product;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;

import com.team_project.shop.domain.BaseEntity;
import com.team_project.shop.domain.user.Role;
import com.team_project.shop.domain.user.Users;

import lombok.*;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Getter
@Entity
@ToString
@AttributeOverride(name="id", column= @Column(name = "image_id" ) )
public class Images extends BaseEntity{

	@Column(nullable = false)
	private String imageName;
	
	@Column(nullable = false)
	private String imageURL;

	@Builder
	public Images(String imageName, String imageURL){
		this.imageName = imageName;
		this.imageURL = imageURL;
	}


}
