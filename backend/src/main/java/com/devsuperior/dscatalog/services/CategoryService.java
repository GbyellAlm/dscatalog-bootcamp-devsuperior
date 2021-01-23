package com.devsuperior.dscatalog.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.dscatalog.dto.CategoryDTO;
import com.devsuperior.dscatalog.entities.Category;
import com.devsuperior.dscatalog.repositories.CategoryRepository;

@Service
public class CategoryService {
	
	@Autowired
	private CategoryRepository repository;
	
	@Transactional(readOnly = true)  
	// "findAll()" n eh uma funcao da linguagem. Eh um nome qlqr q o prof colocou pra funcao ("findAll()" eh o nome da funcao). 
	public List<CategoryDTO> findAll() {
		List<Category> list = repository.findAll();
		
		// Transformando a lista de categoria de cima em uma lista de DTO.
		List <CategoryDTO> listDTO = new ArrayList<>();
		// Pra cada categoria "cat" na minha lista "list"
		for (Category cat : list) {
			listDTO.add(new CategoryDTO(cat));
		}
		
		return listDTO;
		
		/* 
		 * Posso fazer essa transformacao dessa forma tb: 
		 	* return list.stream().map(x -> new CategoryDTO(x)).collect(Collectors.toList());
		 		* O q eh esse metodo "stream()"?
		 			* R: Eh um metodo (disponivel a partir do Java 8) q permite trabalhar c/ funcoes de alta ordem (inclusive c/ funcoes de expressoes lambida por ex). Ele permite fazer transformacoes na minha colecao (lista).
		 		* O q eh esse metodo "map()"?
		 			* R: Eh um metodo de expressoes lambida q transforma cada elemento de um tipo em outro tipo. Ele aplica uma funcao a cada elemento da lista pra poder fazer isso	   	
		 */
	}
}
