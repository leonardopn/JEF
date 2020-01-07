package model.collection.entities;

import javafx.scene.control.CheckBox;

public class Categoria {

	private int id;
	private String nome;
	private CheckBox select;
	
	public Categoria(int id, String nome) {
		this.id = id;
		this.nome = nome;
		this.select = new CheckBox(nome);
	}
	
	public String getNome() {
		return nome;
	}
	public int getId() {
		return id;
	}

	public CheckBox getSelect() {
		return select;
	}
}
