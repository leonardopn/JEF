package model.entities;

import java.util.Objects;

import javafx.scene.control.CheckBox;

public class Cliente implements Comparable<Cliente>{
	private int id;
	private String nome;
	private String email;
	private String telefone;
	private CheckBox select;
	
	public Cliente(int id, String nome, String email, String telefone) {
		this.id = id;
		this.nome = nome;
		this.email = email;
		this.telefone = telefone;
		this.select = new CheckBox();
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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
