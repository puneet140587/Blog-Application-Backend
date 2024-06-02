package com.puneet.blog.entites;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name="categories")
@NoArgsConstructor
@Data
public class Category {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer categoryId;
	
	@Column(name="title",length = 100,nullable = false)
	private String categoryTitle;
	
	@Column(name="description")
	private String categoryDescription;
	
//	private List<Post> posts = new ArrayList<>() ;
	@OneToMany(mappedBy = "category" , cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	//private Set<Post> post = new HashSet<>() ;
	private List<Post> posts = new ArrayList<>() ;

}
