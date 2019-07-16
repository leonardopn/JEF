package model.entities;

import java.util.Objects;

public class Login {
	private String usuario;
	private String senha;
	
	public Login(String usu, String pass) {
		this.usuario = usu;
		this.senha = pass;
	}
	
	public boolean equals(Object outroObjeto) {
		if(this==outroObjeto)
			return true;
		if(outroObjeto == null || !(outroObjeto instanceof Login))
			return false;
		Login outroLogin = (Login) outroObjeto;
		
		return(Objects.equals(this.usuario, outroLogin.usuario)
				&& Objects.equals(this.senha, outroLogin.senha));
	}
	
	@Override
	public String toString() {
		return "\n"+usuario+senha+"\n";
	}
}
