package model.entities;

import java.util.Objects;

import javafx.scene.control.CheckBox;

public class Cliente implements Comparable<Cliente>{
	private int id;
	private String nome;
	private String email;
	private String telefone;
	private String redeSocial;
	private CheckBox select;
	
	public Cliente(int id, String nome, String email, String telefone, String redeSocial) {
		this.id = id;
		this.nome = nome;
		this.email = email;
		this.telefone = telefone;
		this.redeSocial = redeSocial;
		this.select = new CheckBox();
	}
	
	public int getId() {
		return id;
	}

	public CheckBox getSelect() {
		return select;
	}

	public String getNome() {
		return nome;
	}
	
	public String getEmail() {
		return email;
	}
	
	public String getTelefone() {
		return telefone;
	}
	
	public String getRedeSocial() {
		return redeSocial;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public void setRedeSocial(String redeSocial) {
		this.redeSocial = redeSocial;
	}

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
		return this.nome;
	}
}
