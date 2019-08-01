package model.entities;

import java.util.Objects;

import javafx.scene.control.CheckBox;

public class Funcionario implements Comparable<Funcionario> {
	private String nome;
	private int id;
	private double salario;
	private String horario; 
	private CheckBox select;
	private Agenda agenda;
	
	public Funcionario(String nome, int id, double salario) {
		this.agenda = new Agenda();
		this.nome = nome;
		this.id = id;
		this.salario = salario;
		this.select = new CheckBox();
		this.horario = "livre";
		
	}

	public Agenda getAgenda() {
		return agenda;
	}

	public void setAgenda(Agenda agenda) {
		this.agenda = agenda;
	}

	public CheckBox getSelect() {
		return select;
	}

	public void setSelect(CheckBox select) {
		this.select = select;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public double getSalario() {
		return salario;
	}

	public void setSalario(double salario) {
		this.salario = salario;
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
		if(outroObjeto == null || !(outroObjeto instanceof Funcionario))
			return false;
		Funcionario outroFuncionario = (Funcionario) outroObjeto;
		
		return(Objects.equals(this.id, outroFuncionario.id));
	}
	
	@Override
	public int compareTo(Funcionario outroFuncionario) {
		if(this.id != outroFuncionario.id)
			return Integer.compare(this.id, outroFuncionario.id);
		return 0;	
	}
	
	@Override
	public String toString() {
		return this.nome;
	}
	
}
