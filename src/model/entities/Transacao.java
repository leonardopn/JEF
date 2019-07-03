package model.entities;

import java.util.Date;

public class Transacao {
	
	private int id;
	private double valor;
	private Date data;
	private Cliente cliente;
	private Funcionario atendente;
	
	public Transacao(double valor, Date data, Cliente cliente, Funcionario atendente) {
		this.valor = valor;
		this.data = data;
		this.cliente = cliente;
		this.atendente = atendente;
	}
	
	public double getValor() {
		return valor;
	}
	public void setValor(double valor) {
		this.valor = valor;
	}
	public Date getData() {
		return data;
	}
	public void setData(Date data) {
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
}
