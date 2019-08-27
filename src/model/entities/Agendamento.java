package model.entities;

import java.util.Objects;

import javafx.scene.control.CheckBox;

public class Agendamento implements Comparable<Agendamento>{
	
	private String funcionario;
	private String cliente;
	private String data;
	private String horario;
	private CheckBox select;
	
	public Agendamento(String funcionario, String cliente, String data, String horario) {
		this.cliente = cliente;
		this.data = data;
		this.funcionario = funcionario;
		this.horario = horario;
		this.select = new CheckBox();
	}
	
	public String getFuncionario() {
		return funcionario;
	}

	public void setFuncionario(String funcionario) {
		this.funcionario = funcionario;
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

	public CheckBox getSelect() {
		return select;
	}

	public void setSelect(CheckBox select) {
		this.select = select;
	}

	@Override
	public boolean equals(Object outroObjeto) {
		if(this==outroObjeto)
			return true;
		if(outroObjeto == null || !(outroObjeto instanceof Agendamento))
			return false;
		Agendamento outroAgendamento = (Agendamento) outroObjeto;
		
		return(Objects.equals(this.data, outroAgendamento.data)
				&& Objects.equals(this.horario, outroAgendamento.horario))
				&& Objects.equals(this.funcionario, outroAgendamento.horario);
	}

	@Override
	public int compareTo(Agendamento o) {
		if(!(this.data.equals(o.data)))
			return data.compareTo(o.data);
		if(!(this.horario.equals(o.horario)))
			return horario.compareTo(o.horario);
		return 0;
		
	}
	
	@Override
	public String toString() {
		return horario+"\n";
	}
}
