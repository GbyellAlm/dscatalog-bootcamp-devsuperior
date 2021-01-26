package com.devsuperior.dscatalog.resources;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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
	public ResponseEntity<List <CategoryDTO>> findAll() {
		List<CategoryDTO> list = service.findAll();
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
}
