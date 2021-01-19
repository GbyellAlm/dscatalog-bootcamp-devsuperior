package com.devsuperior.dscatalog.resources;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.devsuperior.dscatalog.entities.Category;

@RestController
@RequestMapping(value = "/categories")
public class CategoryResource {
	@GetMapping
	public ResponseEntity<List <Category>> findAll() {
		// O import do List foi o java.util.list
		List<Category> list = new ArrayList<>();
		// O "L" dps do 1 eh pra dizer q o id da lista q to criando eh do tipo long, assim como esta la na classe Category
		list.add(new Category(1L, "Books"));
		list.add(new Category(2L, "Electronics"));
		return ResponseEntity.ok().body(list);
	}
}
