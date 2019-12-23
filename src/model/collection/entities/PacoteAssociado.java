package model.collection.entities;

import javafx.scene.control.CheckBox;

public class PacoteAssociado {
	private int id;
	private String cliente;
	private String pacote;
	private int quantMao;
	private int quantPe;
	private CheckBox select;
	
	
	
	public PacoteAssociado(int id, String cliente, String pacote, int quantMao, int quantPe) {
		this.id = id;
		this.cliente = cliente;
		this.pacote = pacote;
		this.quantMao = quantMao;
		this.quantPe = quantPe;
		this.select = new CheckBox();
	}

	public int getId() {
		return id;
	}
	
	public String getCliente() {
		return cliente;
	}
	
	public String getPacote() {
		return pacote;
	}
	
	public int getQuantMao() {
		return quantMao;
	}
	
	public int getQuantPe() {
		return quantPe;
	}
	
	public CheckBox getSelect() {
		return select;
	}
}
