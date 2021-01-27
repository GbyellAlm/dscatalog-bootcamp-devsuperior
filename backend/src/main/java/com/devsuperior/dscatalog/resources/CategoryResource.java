package com.devsuperior.dscatalog.resources;

import java.net.URI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.devsuperior.dscatalog.dto.CategoryDTO;
import com.devsuperior.dscatalog.services.CategoryService;

@RestController
@RequestMapping(value = "/categories")
public class CategoryResource {
	
	@Autowired
	private CategoryService service;
	
	// BUSCA TODAS AS CATEGORIAS
	@GetMapping
	// A importacao do "Page" eh a do "springframework".
	public ResponseEntity<Page <CategoryDTO>> findAll(
			@RequestParam(value = "page", defaultValue = "0") Integer page,
			@RequestParam(value = "linesPerPage", defaultValue = "12") Integer linesPerPage,
			@RequestParam(value = "direction", defaultValue = "ASC") String direction,
			@RequestParam(value = "orderBy", defaultValue = "name") String orderBy
			) {
		// "Direction.valueOf(direction)" ta convertendo o "direction" (q eh uma string) pro tipo Direction.
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		
		Page<CategoryDTO> list = service.findAllPaged(pageRequest);
		return ResponseEntity.ok().body(list);
	}
	
	// BUSCA CATEGORIA POR ID
	@GetMapping(value = "/{id}")
	// "@PathVariable" eh pra q o "Long id" seja o msm id passado como parametro na rota. 
	public ResponseEntity<CategoryDTO> findById(@PathVariable Long id) {
		CategoryDTO dto = service.findById(id);
		return ResponseEntity.ok().body(dto);
	}
	
	// INSERE CATEGORIA
	@PostMapping
	// "ResponseEntity<CategoryDTO>" pq qro ver o q foi inserido pra ver se deu certo.
	// "CategoryDTO" como parametro da funcao "insert()" pq n eh so passar o nome da categoria q qro criar como parametro. Tenho q passar o objeto da categoria pq eh nele q tem o valor nome da categoria.
	// "@RequestBody" eh pra q o "CategoryDTO dto" case c/ o objeto q eh passado na requisicao - eh basicamente a msm situacao do "Long id" do codigo de cima.
	public ResponseEntity<CategoryDTO> insert(@RequestBody CategoryDTO dto) {
		// O resultado da insercao vai retornar nessa variavel "dto".
		dto = service.insert(dto);
		// Esse "URI Uri..." eh pra q no cabecalho de resposta HTTP apareca o endereço (rota) do recurso inserido.
		// A importacao do "URI" eh o "java.net.uri".
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(dto.getId()).toUri();
		return ResponseEntity.created(uri).body(dto);
	}
	
	// ATUALIZA CATEGORIA
	@PutMapping(value = "{id}")
	public ResponseEntity<CategoryDTO> update(@PathVariable Long id, @RequestBody CategoryDTO dto) {
		dto = service.update(id, dto);
		return ResponseEntity.ok().body(dto);
	}
	
	// DELETA CATEGORIA
	@DeleteMapping(value = "{id}")
	public ResponseEntity<CategoryDTO> delete(@PathVariable Long id) {
		service.delete(id);
		// A resp HTTP vai ser de cod 204 e de corpo vazio.
		return ResponseEntity.noContent().build();
	}
}
