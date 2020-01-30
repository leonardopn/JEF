package model.collections.entities;

import java.util.Objects;

import javafx.scene.control.CheckBox;

public class Pacote implements Comparable<Pacote> {
	private int id;
	private String pacote;
	private double valor;
	private int quantPe;
	private int quantMao;
	private double precoMao;
	private double precoPe;
	private CheckBox select;

	public Pacote(int id, String pacote, double valor, int quantPe, int quantMao, double precoMao, double precoPe) {
		this.id = id;
		this.pacote = pacote;
		this.valor = valor;
		this.quantPe = quantPe;
		this.quantMao = quantMao;
		this.precoMao = precoMao;
		this.precoPe = precoPe;
		this.select = new CheckBox();
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

	public CheckBox getSelect() {
		return select;
	}

	@Override
	public boolean equals(Object outroObjeto) {
		if (this == outroObjeto) {
			return true;
		}
		if (outroObjeto == null || !(outroObjeto instanceof Pacote)) {
			return false;
		}
		Pacote outroPacote = (Pacote) outroObjeto;

		return (Objects.equals(this.id, outroPacote.id));
	}

	@Override
	public int compareTo(Pacote outroPacote) {
		if (this.id != outroPacote.id) {
			return Integer.compare(this.id, outroPacote.id);
		}
		return 0;
	}

	@Override
	public String toString() {
		return pacote;
	}
}
