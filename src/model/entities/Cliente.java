package model.entities;

import java.util.Objects;

import javafx.scene.control.CheckBox;

public class Cliente implements Comparable<Cliente>{
	private String cpf;
	private String nome;
	private String email;
	private String telefone;
	private CheckBox select;
	
	public Cliente(String cpf, String nome, String email, String telefone) {
		this.cpf = cpf;
		this.nome = nome;
		this.email = email;
		this.telefone = telefone;
		this.select = new CheckBox();
	}
	
	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public CheckBox getSelect() {
		return select;
	}

	public void setSelect(CheckBox select) {
		this.select = select;
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
	
	public String getTelefone() {
		return telefone;
	}
	
	public void setTelefone(String telefone) {
		this.telefone = telefone;
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
