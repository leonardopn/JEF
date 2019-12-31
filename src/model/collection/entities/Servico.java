package model.collection.entities;

public class Servico {
	
	private String nome;
	private double preco;
	private int id;
	private String categoria;
	
	public Servico(int id, String nome, double preco, String categoria) {
		this.id = id;
		this.nome = nome;
		this.preco = preco;
		this.categoria = categoria;
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
}
