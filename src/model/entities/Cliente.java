package model.entities;

import java.util.Objects;

public class Cliente implements Comparable<Cliente>{
	private int id;
	private String nome;
	private String email;
	private String redeSocial;
	
	public Cliente(String nome, String email, String redeSocial, String telefone) {
		this.nome = nome;
		this.email = email;
		this.redeSocial = redeSocial;
		this.telefone = telefone;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getRedeSocial() {
		return redeSocial;
	}
	public void setRedeSocial(String redeSocial) {
		this.redeSocial = redeSocial;
	}
	public String getTelefone() {
		return telefone;
	}
	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}
	private String telefone;
	
	@Override
	public boolean equals(Object outroObjeto) {
		if(this==outroObjeto)
			return true;
		if(outroObjeto == null || !(outroObjeto instanceof Funcionario))
			return false;
		Cliente outroCliente = (Cliente) outroObjeto;
		
		return(Objects.equals(this.id, outroCliente.id));
	}
	
	@Override
	public int compareTo(Cliente outroCliente) {
		if(this.id != outroCliente.id)
			return Integer.compare(this.id, outroCliente.id);
		return 0;	
	}
	
	@Override
	public String toString() {
		return "\nNome: "+this.nome+
				"\nId: "+this.id+
				"\nEmail: "+this.email+
				"\nTelefone: "+this.telefone;
	}
	
}
