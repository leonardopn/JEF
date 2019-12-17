package model.collection.entities;

public class Servico {
	
	private String nome;
	private double preco;;
	
	public Servico(String nome, double preco) {
		this.nome = nome;
		this.preco = preco;
	}
	
	//Gets & Sets
	
	public String getNome() {
		return nome;
	}

	public double getPreco() {
		return preco;
	}
}
