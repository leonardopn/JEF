package model.entities;

import java.util.Objects;

public class Agendamento implements Comparable<Agendamento>{
	
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
	
	@Override
	public boolean equals(Object outroObjeto) {
		if(this==outroObjeto)
			return true;
		if(outroObjeto == null || !(outroObjeto instanceof Agendamento))
			return false;
		Agendamento outroAgendamento = (Agendamento) outroObjeto;
		
		return(Objects.equals(this.data, outroAgendamento.data)
				&& Objects.equals(this.horario, outroAgendamento.horario));
	}

	@Override
	public int compareTo(Agendamento o) {
		if(!(this.data.equals(o.data)))
			return data.compareTo(o.data);
		if(!(this.horario.equals(o.horario)))
			return horario.compareTo(o.horario);
		return 0;
		
	}
}
