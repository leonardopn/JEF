package model.entities;

import java.util.Calendar;
import java.util.Date;
import java.util.TreeSet;

public class Agenda {
	
	
	public Funcionario funcionario;
	public int h12;
	public int h12_3;
	public int h13;
	public int h13_3;
	public int h14;
	public int h14_3;
	public int h15;
	public int h15_3;
	public int h16;
	public int h16_3;
	public int h17;
	public int h17_3;
	public int h18;
	public Agenda(Funcionario funcionario, int h12, int h12_3, int h13, int h13_3, int h14, int h14_3, int h15,
			int h15_3, int h16, int h16_3, int h17, int h17_3, int h18) {
		
		this.funcionario = funcionario;
		this.h12 = h12;
		this.h12_3 = h12_3;
		this.h13 = h13;
		this.h13_3 = h13_3;
		this.h14 = h14;
		this.h14_3 = h14_3;
		this.h15 = h15;
		this.h15_3 = h15_3;
		this.h16 = h16;
		this.h16_3 = h16_3;
		this.h17 = h17;
		this.h17_3 = h17_3;
		this.h18 = h18;
	}
	
	

}
