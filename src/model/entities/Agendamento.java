package model.entities;

public class Agendamento {
	
	private String funcionario;
	private String cliente;
	private String data;
	private String horario;
	
	public Agendamento(String funcionario, String cliente, String data, String horario) {
		this.funcionario = funcionario;
		this.cliente = cliente;
		this.data = data;
		this.horario = horario;
	}

	public void setFuncionario(String funcionario) {
		this.funcionario = funcionario;
	}
	
	public String getFuncionario() {
		return funcionario;
	}

	public String getCliente() {
		return cliente;
	}

	public void setCliente(String cliente) {
		this.cliente = cliente;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getHorario() {
		return horario;
	}

	public void setHorario(String horario) {
		this.horario = horario;
	}
}
