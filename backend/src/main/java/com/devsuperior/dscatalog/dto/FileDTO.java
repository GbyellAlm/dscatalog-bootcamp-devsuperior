package com.devsuperior.dscatalog.dto;

import java.io.Serializable;

import org.springframework.web.multipart.MultipartFile;

public class FileDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	// "MultipartFile" eh o tipo q tem q usar numa variavel arquivo no Spring Boot.
	private MultipartFile file;
	
	public FileDTO() {
		
	}

	public MultipartFile getFile() {
		return file;
	}

	public void setFile(MultipartFile file) {
		this.file = file;
	}
}
