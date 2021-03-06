package model.collections.entities;

import java.util.Objects;

import javafx.scene.control.CheckBox;

public class Transacao implements Comparable<Transacao>{
	
	private int id;
	private double valor;
	private String data;
	private String cliente;
	private String atendente;
	private CheckBox select;
	private String formaPagamento;
	private String obs;
	private String servico;
	
	public Transacao(int id, double valor, String cliente, String atendente, String formaPagamento, String data, String obs, String servico) {
		this.id = id;
		this.valor = valor;
		this.data = data;
		this.cliente = cliente;
		this.atendente = atendente;
		this.setFormaPagamento(formaPagamento);
		this.obs = obs;
		this.servico = servico;
		select = new CheckBox();
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
	
	public double getValor() {
		return valor;
	}
	public void setValor(double valor) {
		this.valor = valor;
	}
	
	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getCliente() {
		return cliente;
	}
	public void setCliente(String cliente) {
		this.cliente = cliente;
	}
	public String getAtendente() {
		return atendente;
	}
	public void setAtendente(String atendente) {
		this.atendente = atendente;
	}
	
	public String getFormaPagamento() {
		return formaPagamento;
	}

	public String getObs() {
		return obs;
	}

	public String getServico() {
		return servico;
	}

	public void setFormaPagamento(String formaPagamento) {
		this.formaPagamento = formaPagamento;
	}
	
	@Override
	public boolean equals(Object outroObjeto) {
		if(this==outroObjeto) {
			return true;
		}
		if(outroObjeto == null || !(outroObjeto instanceof Transacao)) {
			return false;
		}	
		Transacao outraTransacao = (Transacao) outroObjeto;
		
		return(Objects.equals(this.id, outraTransacao.id));
	}
	
	@Override
	public int compareTo(Transacao outraTransacao) {
		if(this.id != outraTransacao.id) {
			return Integer.compare(this.id, outraTransacao.id);
		}
		return 0;	
	}
	
	@Override
	public String toString() {
		return ""+this.id+"\n"+
		this.valor+"\n"+
		this.data+"\n"+
		this.cliente+"\n"+
		this.atendente+
		this.formaPagamento;
	}
}
