package model.collection.entities;

public class Pacote {
	private int id;
	private String pacote;
	private double valor;
	private int quantPe;
	private int quantMao;
	private double precoMao;
	private double precoPe;
	
	public Pacote(int id, String pacote, double valor, int quantPe, int quantMao, double precoMao, double precoPe) {
		this.id = id;
		this.pacote = pacote;
		this.valor = valor;
		this.quantPe = quantPe;
		this.quantMao = quantMao;
		this.precoMao = precoMao;
		this.precoPe = precoPe;
	}

	public int getId() {
		return id;
	}
	
	public String getPacote() {
		return pacote;
	}
	
	public double getValor() {
		return valor;
	}
	
	public int getQuantPe() {
		return quantPe;
	}
	
	public int getQuantMao() {
		return quantMao;
	}
	
	public double getPrecoMao() {
		return precoMao;
	}
	
	public double getPrecoPe() {
		return precoPe;
	}
}
