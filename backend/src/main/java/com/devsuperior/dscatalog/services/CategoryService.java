package com.devsuperior.dscatalog.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.dscatalog.dto.CategoryDTO;
import com.devsuperior.dscatalog.entities.Category;
import com.devsuperior.dscatalog.repositories.CategoryRepository;
import com.devsuperior.dscatalog.services.exceptions.ResourceNotFoundException;

@Service
public class CategoryService {
	
	@Autowired
	private CategoryRepository repository;
	
	// BUSCA TODAS AS CATEGORIAS
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
		 			* R: Eh um metodo de expressoes lambida q transforma cada elemento de um tipo em outro tipo. Ele aplica uma funcao a cada elemento da lista pra poder fazer isso. No video https://www.youtube.com/watch?v=ZYPQmfcZGxg&t=1s o Nelio explica c/ mais detalhes o q eh esse "map()".	   	
		 */
	}
	
	// BUSCA CATEGORIA POR ID
	@Transactional(readOnly = true)  
	public CategoryDTO findById(Long id) {
		// "Optional" eh um metodo q evita q eu trampe c/ valores nulos. Tipo, c/ esse metodo o retorno da busca nunca vai ser nulo, e sim do tipo "Optional". ATENCAO! O valor valor msm da busca pode ser nulo daí (ex: buscando o id 44, mas o id 44 n existe). A importacao eh do "Java.util". 
		Optional<Category> obj = repository.findById(id);
		// O "Optional" tem a entidade q veio do bd. Preciso obter essa entidade do Optional (eh isso q a linha 52 faz).
		Category entity = obj.orElseThrow(() -> new ResourceNotFoundException("Entidade não encontrada"));
		return new CategoryDTO(entity);
	}
	
	// INSERE CATEGORIA
	@Transactional
	public CategoryDTO insert(CategoryDTO dto) {
		// Transformando a categoria q recebi - q esta em "DTO" - em "Category" (entidade) pra poder salva-la no bd.
		Category entity = new Category();
		entity.setName(dto.getName());
		// Salvando a categoria no bd. * O metodo "save()" retorna uma referencia pra entidade salva, por isso essa operacao foi atribuida a uma variavel (entity).
		entity = repository.save(entity);
		return new CategoryDTO(entity);
	}
	
	// ATUALIZA CATEGORIA
	@Transactional
	public CategoryDTO update(Long id, CategoryDTO dto) {
		// "try" "catch()" pq o "id" q o metodo "update()" recebeu pode n existir.
		try {
			// "getOne()" eh um metodo do JPA pra atualizar dados. Ele atualiza um dado instanciando um obj provisorio (ex: Category entity) c/ seus dados (ex: No caso do Category, "id" e "name"), e chamando o metodo "save()" do JPA, q efetiva a atualizacao e acessa o bd 1x soh (diferentemente se fosse atualizar c/ os metodos "findById()" e "save()". O acesso ao bd seria duplicado dai).
			Category entity = repository.getOne(id);
			entity.setName(dto.getName());
			entity = repository.save(entity);
			return new CategoryDTO(entity);
		}
		// javax.persistence (importacao do "EntityNotFoundException")
		catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException("Id não encontrado");
		}
	}
}
