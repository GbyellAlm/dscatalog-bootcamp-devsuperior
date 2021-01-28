package com.devsuperior.dscatalog.services;

import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.dscatalog.dto.CategoryDTO;
import com.devsuperior.dscatalog.entities.Category;
import com.devsuperior.dscatalog.repositories.CategoryRepository;
import com.devsuperior.dscatalog.services.exceptions.DatabaseException;
import com.devsuperior.dscatalog.services.exceptions.ResourceNotFoundException;

@Service
public class CategoryService {
	
	@Autowired
	private CategoryRepository repository;
	
	/*
	 * BUSCA TODAS AS CATEGORIAS (FORMA N PAGINADA)
	 * @Transactional(readOnly = true)
	 * "findAll()" n eh uma funcao da linguagem. Eh uma funcao q criei.
	 * public List<CategoryDTO> findAll() {
	 * 		List<Category> list = repository.findAll();
	 * 		
	 * 		Transformando o "list" em uma lista de DTO.
	 * 		List <CategoryDTO> listDTO = new ArrayList<>();
	 * 		Pra cada categoria "cat" na minha lista "list"
	 * 		for (Category cat : list) {
	 * 			listDTO.add(new CategoryDTO(cat));
	 * 		}
	 * 		return listDTO;
	 * 
	 * EU PODERIA TRANSFORMAR O "list" EM UMA LISTA DE DTO DA SEGUINTE FORMA TB:
	 * 
	 * return list.stream().map(x -> new CategoryDTO(x)).collect(Collectors.toList());
	 * 		- O q eh esse metodo "stream()"?
	 * 			R: Eh um metodo (disponivel a partir do Java 8) q permite trabalhar c/ funcoes de alta ordem (inclusive c/ funcoes de expressoes lambida por ex). Ele permite fazer transformacoes na minha colecao (lista).
	 * 		
	 * 		- O q eh esse metodo "map()"?
	 * 			R: Eh um metodo de expressoes lambida q transforma cada elemento de um tipo em outro tipo. Ele aplica uma funcao a cada elemento da lista pra poder fazer isso. No video https://www.youtube.com/watch?v=ZYPQmfcZGxg&t=1s o Nelio explica c/ mais detalhes o q eh esse "map()".
	 */
	
	// BUSCA TDAS AS CATEGORIAS (FORMA PAGINADA)
	@Transactional(readOnly = true)  
	public Page<CategoryDTO> findAllPaged(PageRequest pageRequest) {
		Page<Category> list = repository.findAll(pageRequest);
		// N tem o metodo "stream()" pq ele ja ta implementado no Page, a partir do Java 8.
		return list.map(x -> new CategoryDTO(x));
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
		// "try" "catch()" pq posso tentar atualizar um "id" q n existe.
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
	// DELETA CATEGORIA
	// N tem a notation "@Transactional" pq qro pegar uma excecao do bd.
	public void delete(Long id) {
		// "try" "catch()" pq posso tentar deletar algo q n existe e algo q n eh pra deletar (algo q vai gerar uma inconsistencia referente ao modelo de classes/ bd).
		try {
			repository.deleteById(id);
		}
		catch (EmptyResultDataAccessException e ) {
			throw new ResourceNotFoundException("Id não encontrado");
			
		}
		catch (DataIntegrityViolationException e) {
			throw new DatabaseException("Violação de integridade");
		}
	}
}
