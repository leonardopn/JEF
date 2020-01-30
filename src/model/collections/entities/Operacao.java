package model.collections.entities;

import javafx.scene.control.CheckBox;

public class Operacao {
	private int id;
	private String obs;
	private String data;
	private double receita;
	private double despesa;
	private String formaPagamento;
	private CheckBox select;
	
	public Operacao(int id, String obs, String data, double receita, String formaPagamento) {
		this.id = id;
		this.obs = obs;
		this.data = data;
		if(receita<0) {
			this.despesa = receita;
		}
		else {
			this.receita = receita;
		}
		this.formaPagamento = formaPagamento;
		this.select = new CheckBox();
	}

	public int getId() {
		return id;
	}

	public String getObs() {
		return obs;
	}

	public String getData() {
		return data;
	}

	public double getReceita() {
		return receita;
	}

	public double getDespesa() {
		return despesa;
	}

	public String getFormaPagamento() {
		return formaPagamento;
	}

	public CheckBox getSelect() {
		return select;
	}
	
}
