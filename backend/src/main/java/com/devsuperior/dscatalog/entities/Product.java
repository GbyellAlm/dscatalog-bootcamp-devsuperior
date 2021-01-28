package com.devsuperior.dscatalog.entities;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "tb_product")
public class Product implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	
	// "@Column(columnDefinition = "TEXT")" pra dizer pro bd q a coluna "description" eh do tipo text (Senao da erro ao tentar popular o bd c/ a coluna "description" recebendo texto mt longo).
	@Column(columnDefinition = "TEXT")
	private String description;
	private Double price;
	private String imgUrl;
	
	// "@Column(columnDefinition = "TIMESTAMP WITHOUT TIME ZONE")" pra instruir o bd a armazenar a data em UTC.
	@Column(columnDefinition = "TIMESTAMP WITHOUT TIME ZONE")
	private Instant date;
	
	// RELACIONANDO PROD C/ CATEGORY (Pq 1 prod tem q ter no min 1 categoria e no max "n" (Diag de classes)).
	/*
	 * "Set" pq ele n permite repeticoes. Ou seja, pq n qro q um prod tenha a msm categoria + de 1x.
	 * ATENCAO! A importacao dele eh a "java.util".
	 * "HashSet<>()" eh a classe q implementa o "Set". O "Set" eh uma interface assim como o "List" e ai n da pra instancia-los diretamente (se instancia classes q implementam elas dai).
	 */
	@ManyToMany
	@JoinTable(name = "tb_product_category", joinColumns = @JoinColumn(name = "product_id"), inverseJoinColumns = @JoinColumn(name = "category_id"))
	Set<Category> categories = new HashSet<>();
	
	public Product() {
		
	}

	public Product(Long id, String name, String description, Double price, String imgUrl, Instant date) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.price = price;
		this.imgUrl = imgUrl;
		this.date = date;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	public Instant getDate() {
		return date;
	}

	public void setDate(Instant date) {
		this.date = date;
	}

	public Set<Category> getCategories() {
		return categories;
	}
	
	// Aq tinha o "setCategories". Ele foi tirado pq em momento algum eu precisarei trocar a minha colecao de categorias em outra classe.

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Product other = (Product) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
