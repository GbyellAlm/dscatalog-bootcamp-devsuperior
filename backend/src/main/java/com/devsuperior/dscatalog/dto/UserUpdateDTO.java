package com.devsuperior.dscatalog.dto;

import com.devsuperior.dscatalog.services.validation.UserUpdateValid;

/*
 * Esse DTO existe pq qro trafegar a senha do usu somente na operacao "insert".
 * "extends UserDTO" pq qro carregar tdos os dados do DTO comum + a senha.
*/

// "@UserInsertValid" eh uma anotation personalizada (q foi criada pelo prof) p/ poder verificar, por debaixo dos panos, se o e-mail q o usu botou jah existe no BD. 
@UserUpdateValid
public class UserUpdateDTO extends UserDTO{
	private static final long serialVersionUID = 1L;

}
