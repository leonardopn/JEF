package model.entities;

import java.util.Objects;

import javafx.scene.control.CheckBox;

public class Funcionario implements Comparable<Funcionario> {
	private String nome;
	private String cpf;
	private double salario; 
	private int status;
	private CheckBox select;
	private String h12;
	private String h12_3;
	private String h13;
	private String h13_3;
	private String h14;
	private String h14_3;
	private String h15;
	private String h15_3;
	private String h16;
	private String h16_3;
	private String h17;
	private String h17_3;
	private String h18;
	
	public Funcionario(String cpf, String nome, double salario, int status) {
		this.nome = nome;
		this.cpf = cpf;
		this.salario = salario;
		this.select = new CheckBox();
		this.status = status;
		this.h12 = "Livre"; this.h12_3 = "Livre"; this.h13 = "Livre"; this.h13_3 = "Livre"; this.h14 = "Livre";
		this.h14_3 = "Livre"; this.h15 = "Livre"; this.h15_3 = "Livre"; this.h16 = "Livre"; this.h16_3 = "Livre";
		this.h17 = "Livre"; this.h17_3 = "Livre"; this.h18 = "Livre";
	}

	public String getH12() {
		return h12;
	}

	public void setH12(String h12) {
		this.h12 = h12;
	}

	public String getH12_3() {
		return h12_3;
	}

	public void setH12_3(String h12_3) {
		this.h12_3 = h12_3;
	}

	public String getH13() {
		return h13;
	}

	public void setH13(String h13) {
		this.h13 = h13;
	}

	public String getH13_3() {
		return h13_3;
	}

	public void setH13_3(String h13_3) {
		this.h13_3 = h13_3;
	}

	public String getH14() {
		return h14;
	}

	public void setH14(String h14) {
		this.h14 = h14;
	}

	public String getH14_3() {
		return h14_3;
	}

	public void setH14_3(String h14_3) {
		this.h14_3 = h14_3;
	}

	public String getH15() {
		return h15;
	}

	public void setH15(String h15) {
		this.h15 = h15;
	}

	public String getH15_3() {
		return h15_3;
	}

	public void setH15_3(String h15_3) {
		this.h15_3 = h15_3;
	}

	public String getH16() {
		return h16;
	}

	public void setH16(String h16) {
		this.h16 = h16;
	}

	public String getH16_3() {
		return h16_3;
	}

	public void setH16_3(String h16_3) {
		this.h16_3 = h16_3;
	}

	public String getH17() {
		return h17;
	}

	public void setH17(String h17) {
		this.h17 = h17;
	}

	public String getH17_3() {
		return h17_3;
	}

	public void setH17_3(String h17_3) {
		this.h17_3 = h17_3;
	}

	public String getH18() {
		return h18;
	}

	public void setH18(String h18) {
		this.h18 = h18;
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

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public double getSalario() {
		return salario;
	}

	public void setSalario(double salario) {
		this.salario = salario;
	}
	
	public void zeraHorarios() {
		this.h12 = "Livre"; this.h12_3 = "Livre"; this.h13 = "Livre";
		this.h13_3 = "Livre"; this.h14 = "Livre"; this.h14_3 = "Livre";
		this.h15 = "Livre"; this.h15_3 = "Livre"; this.h16 = "Livre";
		this.h16_3 = "Livre"; this.h17 = "Livre"; this.h17_3 = "Livre";
		this.h18 = "Livre";
	}
	
	public void retornaHorario(String horario, String cliente) {
		if(horario.equals("12:00")) {
			setH12(cliente);
		}
		if(horario.equals("12:30")) {
			setH12_3(cliente);
		}
		if(horario.equals("13:00")) {
			setH13(cliente);
		}
		if(horario.equals("13:30")) {
			setH13_3(cliente);
		}
		if(horario.equals("14:00")) {
			setH14(cliente);
		}
		if(horario.equals("14:30")) {
			setH14_3(cliente);
		}
		if(horario.equals("15:00")) {
			setH15(cliente);
		}
		if(horario.equals("15:30")) {
			setH15_3(cliente);
		}
		if(horario.equals("16:00")) {
			setH16(cliente);
		}
		if(horario.equals("16:30")) {
			setH16_3(cliente);
		}
		if(horario.equals("17:00")) {
			setH17(cliente);
		}
		if(horario.equals("17:30")) {
			setH17_3(cliente);
		}
		if(horario.equals("18:00")) {
			setH18(cliente);
		}
	}

	@Override
	public boolean equals(Object outroObjeto) {
		if(this==outroObjeto)
			return true;
		if(outroObjeto == null || !(outroObjeto instanceof Funcionario))
			return false;
		Funcionario outroFuncionario = (Funcionario) outroObjeto;
		
		return(Objects.equals(this.cpf, outroFuncionario.cpf));
	}
	
	@Override
	public int compareTo(Funcionario outroFuncionario) {
		if(this.cpf != outroFuncionario.cpf)
			return this.cpf.compareTo(outroFuncionario.cpf);
		return 0;	
	}
	
	@Override
	public String toString() {
		return this.nome + "\n"+this.cpf;
	}
	
}
