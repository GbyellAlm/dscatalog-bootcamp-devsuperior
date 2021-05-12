package com.devsuperior.dscatalog.services.validation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerMapping;

import com.devsuperior.dscatalog.dto.UserUpdateDTO;
import com.devsuperior.dscatalog.entities.User;
import com.devsuperior.dscatalog.repositories.UserRepository;
import com.devsuperior.dscatalog.resources.exceptions.FieldMessage;

public class UserUpdateValidator implements ConstraintValidator<UserUpdateValid, UserUpdateDTO> {
	
	// "private HttpServletRequest request;" eh p/ q eu consiga pegar o id do usuario, q eh passado na URL.
	@Autowired
	private HttpServletRequest request;
	
	@Autowired
	private UserRepository repository;
	
	@Override
	public void initialize(UserUpdateValid ann) {
	}

	@Override
	public boolean isValid(UserUpdateDTO dto, ConstraintValidatorContext context) {
		
		/*
		 *  "var uriVars" pega as variaveis (o id do usu q passo na req) da requisicao;
		 *  "(Map<String, String>)" eh um cast q eu tive q fazer pq o ".get" tava dando pau;
		 *  "String, String", o 1º "String" significa o parametro "id" q passo la na minha API, e o 2º "String" significa o valor desse "id", q eh uma string tb (no http td eh string).
		*/
		var uriVars = (Map<String, String>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
		long userId = Long.parseLong(uriVars.get("id"));
		
		List<FieldMessage> list = new ArrayList<>();
		
		User user = repository.findByEmail(dto.getEmail());
		// "user != null && userId != user.getId()" significa: BD buscou o usu pelo e-mail, retornou um usu, se o "id" desse usu q o BD retornou for != do "id" do usu q ta tentando atualizar seu e-mail, tenho q estourar uma msg pra ele dizendo q 'esse e-mail jah existe". 
		if (user != null && userId != user.getId()) {
			list.add(new FieldMessage("email", "E-mail já existe"));
		}
		
		// Esse "for" insere na lista de erros do "BeansValidation" os erros q aconteceram.
		for (FieldMessage e : list) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getFieldName())
					.addConstraintViolation();
		}
		return list.isEmpty();
	}
}
