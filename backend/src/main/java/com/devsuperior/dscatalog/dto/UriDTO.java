package com.devsuperior.dscatalog.dto;

import java.io.Serializable;

// Esse DTO vai conter a URI da imagem q foi upada na AWS S3. Essa URI vai estar no corpo da resposta da requisicao.
public class UriDTO implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private String uri;
	
	public UriDTO() {
		
	}
	
	public UriDTO(String uri) {
		this.uri = uri;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}
}
