package model.entities;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;

public class Funcionario implements Comparable<Funcionario> {
	private String nome;
	private int id;
	private double salario;
	private String horario;
	private ArrayList<Date> horarios; 
	private CheckBox select;
	private static SimpleDateFormat sdf;
	
	public Funcionario(String nome, int id, double salario) {
//		Calendar hora = Calendar.getInstance();
//		hora.get(Calendar.DATE);
//		hora.set(Calendar.HOUR, 00);
//		hora.set(Calendar.MINUTE, 00);
//		hora.set(Calendar.SECOND, 00);
//		Date horario = hora.getTime();
//		horarios.add(horario);
//		for(int i = 0; i < 12; i++) {
//			hora.add(Calendar.MINUTE, 30);
//			horario = hora.getTime();
//			horarios.add(horario);
//		}
		this.nome = nome;
		this.id = id;
		this.salario = salario;
		this.select = new CheckBox();
		this.horario = "livre";
		
	}
	
	public static void criaHorarios() {
        
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
