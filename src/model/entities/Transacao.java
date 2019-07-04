package model.entities;

import java.time.LocalDate;
import java.util.Objects;

import javafx.scene.control.CheckBox;

public class Transacao implements Comparable<Transacao>{
	
	private int id;
	private double valor;
	private LocalDate data;
	private Cliente cliente;
	private Funcionario atendente;
	private CheckBox select;
	private String formaPagamento;
	
	public Transacao(int id, double valor, LocalDate data, Cliente cliente, Funcionario atendente, String formaPagamento) {
		this.id = id;
		this.valor = valor;
		this.data = data;
		this.cliente = cliente;
		this.atendente = atendente;
		this.setFormaPagamento(formaPagamento);
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
	public LocalDate getData() {
		return data;
	}
	public void setData(LocalDate data) {
		this.data = data;
	}
	public Cliente getCliente() {
		return cliente;
	}
	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
	public Funcionario getAtendente() {
		return atendente;
	}
	public void setAtendente(Funcionario atendente) {
		this.atendente = atendente;
	}
	
	public String getFormaPagamento() {
		return formaPagamento;
	}

	public void setFormaPagamento(String formaPagamento) {
		this.formaPagamento = formaPagamento;
	}
	
	@Override
	public boolean equals(Object outroObjeto) {
		if(this==outroObjeto)
			return true;
		if(outroObjeto == null || !(outroObjeto instanceof Transacao))
			return false;
		Transacao outraTransacao = (Transacao) outroObjeto;
		
		return(Objects.equals(this.id, outraTransacao.id));
	}
	
	@Override
	public int compareTo(Transacao outraTransacao) {
		if(this.id != outraTransacao.id)
			return Integer.compare(this.id, outraTransacao.id);
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
