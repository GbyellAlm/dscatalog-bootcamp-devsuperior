package com.devsuperior.dscatalog.dto;

import com.devsuperior.dscatalog.services.validation.UserInsertValid;

/*
 * Esse DTO existe pq qro trafegar a senha do usu somente na operacao "insert".
 * "extends UserDTO" pq qro carregar tdos os dados do DTO comum + a senha.
*/

// "@UserInsertValid" eh uma anotacao personalizada (q foi criada pelo prof) p/ poder verificar, por debaixo dos panos, se o e-mail q o usu botou jah existe no BD. 
@UserInsertValid
public class UserInsertDTO extends UserDTO{
	private static final long serialVersionUID = 1L;

	private String password;
	
	UserInsertDTO() {
		// "super();" eh soh p/ garantir q caso tenha alguma logica no construtor vazio da super classe (classe pai (DTO pai (userDTO))), ele pegue essa logica aq tb.
		super();
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
