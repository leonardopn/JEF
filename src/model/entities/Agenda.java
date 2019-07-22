package model.entities;

import java.util.Calendar;
import java.util.Date;

public class Agenda {
	
	private static Compromisso compromisso;
	private static Calendar horaInicio = Calendar.getInstance();
	private static Calendar horaFinal = Calendar.getInstance();
	
	public static void horarios() {
		horaInicio.set(Calendar.HOUR, 7);
		horaInicio.set(Calendar.MINUTE, 00);
		horaFinal.set(Calendar.HOUR, 6);
		horaFinal.set(Calendar.MINUTE, 00);
		Date data1 = horaInicio.getTime();
		Date data2 = horaFinal.getTime();
		System.out.println(data1);
		System.out.println(data2);
	}
	
	
}
