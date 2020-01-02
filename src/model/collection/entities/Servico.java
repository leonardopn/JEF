package model.collection.entities;

import javafx.scene.control.CheckBox;

public class Servico {
	
	private String nome;
	private double preco;
	private int id;
	private String categoria;
	private CheckBox select;
	
	public Servico(int id, String nome, double preco, String categoria) {
		this.id = id;
		this.nome = nome;
		this.preco = preco;
		this.categoria = categoria;
		this.select = new CheckBox(nome);
	}
	
	//Gets & Sets
	
	public String getNome() {
		return nome;
	}

	public double getPreco() {
		return preco;
	}

	public String getCategoria() {
		return categoria;
	}

	public int getId() {
		return id;
	}

	public CheckBox getSelect() {
		return select;
	}

	@Override
	public String toString() {
		return "nome=" + nome + ", preco=" + preco + ", id=" + id + ", categoria=" + categoria+"\n";
	}
	
	
}
