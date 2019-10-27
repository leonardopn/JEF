package model.entities;

import java.util.Objects;

import javafx.scene.control.CheckBox;

public class Cliente implements Comparable<Cliente>{
	private String cpf;
	private String nome;
	private String email;
	private String telefone;
	private String redeSocial;
	private CheckBox select;
	
	public Cliente(String cpf, String nome, String email, String telefone, String redeSocial) {
		this.cpf = cpf;
		this.nome = nome;
		this.email = email;
		this.telefone = telefone;
		this.redeSocial = redeSocial;
		this.select = new CheckBox();
	}
	
	public String getCpf() {
		return cpf;
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
	
	@Override
	public boolean equals(Object outroObjeto) {
		if(this==outroObjeto)
			return true;
		if(outroObjeto == null || !(outroObjeto instanceof Funcionario))
			return false;
		Cliente outroCliente = (Cliente) outroObjeto;
		
		return(Objects.equals(this.cpf, outroCliente.cpf));
	}
	
	@Override
	public int compareTo(Cliente outroCliente) {
		if(this.cpf != outroCliente.cpf)
			return this.cpf.compareTo(outroCliente.cpf);
		return 0;	
	}
	
	@Override
	public String toString() {
		return this.nome;
	}
}
