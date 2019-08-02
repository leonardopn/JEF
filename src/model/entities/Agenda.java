//package model.entities;
//
//import java.util.Objects;
//
//public class Agenda implements Comparable<Agenda>{
//	
//	private String h12;
//	private String h12_3;
//	private String h13;
//	private String h13_3;
//	private String h14;
//	private String h14_3;
//	private String h15;
//	private String h15_3;
//	private String h16;
//	private String h16_3;
//	private String h17;
//	private String h17_3;
//	private String h18;
//	
//	public Agenda() {
//		this.h12 = "Livre";
//		this.h12_3 = "Livre";
//		this.h13 = "Livre";
//		this.h13_3 = "Livre";
//		this.h14 = "Livre";
//		this.h14_3 = "Livre";
//		this.h15 = "Livre";
//		this.h15_3 = "Livre";
//		this.h16 = "Livre";
//		this.h16_3 = "Livre";
//		this.h17 = "Livre";
//		this.h17_3 = "Livre";
//		this.h18 = "Livre";
//	}
//	
//	public void zeraHorarios() {
//		this.h12 = "Livre";
//		this.h12_3 = "Livre";
//		this.h13 = "Livre";
//		this.h13_3 = "Livre";
//		this.h14 = "Livre";
//		this.h14_3 = "Livre";
//		this.h15 = "Livre";
//		this.h15_3 = "Livre";
//		this.h16 = "Livre";
//		this.h16_3 = "Livre";
//		this.h17 = "Livre";
//		this.h17_3 = "Livre";
//		this.h18 = "Livre";
//	}
//	
//	public void retornaHorario(String horario, Agenda agenda, String cliente) {
//		if(horario.equals("12:00")) {
//			agenda.setH12(cliente);
//		}
//		if(horario.equals("12:30")) {
//			agenda.setH12_3(cliente);
//		}
//		if(horario.equals("13:00")) {
//			agenda.setH13(cliente);
//		}
//		if(horario.equals("13:30")) {
//			agenda.setH13_3(cliente);
//		}
//		if(horario.equals("14:00")) {
//			agenda.setH14(cliente);
//		}
//		if(horario.equals("14:30")) {
//			agenda.setH14_3(cliente);
//		}
//		if(horario.equals("15:00")) {
//			agenda.setH15(cliente);
//		}
//		if(horario.equals("15:30")) {
//			agenda.setH15_3(cliente);
//		}
//		if(horario.equals("16:00")) {
//			agenda.setH16(cliente);
//		}
//		if(horario.equals("16:30")) {
//			agenda.setH16_3(cliente);
//		}
//		if(horario.equals("17:00")) {
//			agenda.setH17(cliente);
//		}
//		if(horario.equals("17:30")) {
//			agenda.setH17_3(cliente);
//		}
//		if(horario.equals("18:00")) {
//			agenda.setH18(cliente);
//		}
//	}
//
//	public String getH12() {
//		return h12;
//	}
//	public void setH12(String h12) {
//		this.h12 = h12;
//	}
//	public String getH12_3() {
//		return h12_3;
//	}
//	public void setH12_3(String h12_3) {
//		this.h12_3 = h12_3;
//	}
//	public String getH13() {
//		return h13;
//	}
//	public void setH13(String h13) {
//		this.h13 = h13;
//	}
//	public String getH13_3() {
//		return h13_3;
//	}
//	public void setH13_3(String h13_3) {
//		this.h13_3 = h13_3;
//	}
//	public String getH14() {
//		return h14;
//	}
//	public void setH14(String h14) {
//		this.h14 = h14;
//	}
//	public String getH14_3() {
//		return h14_3;
//	}
//	public void setH14_3(String h14_3) {
//		this.h14_3 = h14_3;
//	}
//	public String getH15() {
//		return h15;
//	}
//	public void setH15(String h15) {
//		this.h15 = h15;
//	}
//	public String getH15_3() {
//		return h15_3;
//	}
//	public void setH15_3(String h15_3) {
//		this.h15_3 = h15_3;
//	}
//	public String getH16() {
//		return h16;
//	}
//	public void setH16(String h16) {
//		this.h16 = h16;
//	}
//	public String getH16_3() {
//		return h16_3;
//	}
//	public void setH16_3(String h16_3) {
//		this.h16_3 = h16_3;
//	}
//	public String getH17() {
//		return h17;
//	}
//	public void setH17(String h17) {
//		this.h17 = h17;
//	}
//	public String getH17_3() {
//		return h17_3;
//	}
//	public void setH17_3(String h17_3) {
//		this.h17_3 = h17_3;
//	}
//	public String getH18() {
//		return h18;
//	}
//	public void setH18(String h18) {
//		this.h18 = h18;
//	}
//
//	@Override
//	public boolean equals(Object outroObjeto) {
//		if(this==outroObjeto)
//			return true;
//		if(outroObjeto == null || !(outroObjeto instanceof Agenda))
//			return false;
//		Agenda outroAgenda = (Agenda) outroObjeto;
//		
//		return(Objects.equals(this.h12, outroAgenda.h12)
//				&& Objects.equals(this.h12_3, outroAgenda.h12_3)
//				&& Objects.equals(this.h13, outroAgenda.h13)
//				&& Objects.equals(this.h13_3, outroAgenda.h13_3)
//				&& Objects.equals(this.h14, outroAgenda.h14)
//				&& Objects.equals(this.h14_3, outroAgenda.h14_3)
//				&& Objects.equals(this.h15, outroAgenda.h15)
//				&& Objects.equals(this.h15_3, outroAgenda.h15_3)
//				&& Objects.equals(this.h16, outroAgenda.h16)
//				&& Objects.equals(this.h16_3, outroAgenda.h16_3)
//				&& Objects.equals(this.h17, outroAgenda.h17)
//				&& Objects.equals(this.h17_3, outroAgenda.h17_3)
//				&& Objects.equals(this.h18, outroAgenda.h18)
//				);
//	}
//
//	@Override
//	public int compareTo(Agenda o) {
//		if(!(this.h12.equals(o.h12)))
//			return h12.compareTo(o.h12);
//		
//		if(!(this.h12_3.equals(o.h12_3)))
//			return h12_3.compareTo(o.h12_3);
//		
//		if(!(this.h13.equals(o.h13)))
//			return h13.compareTo(o.h13);
//		
//		if(!(this.h13_3.equals(o.h13_3)))
//			return h13_3.compareTo(o.h13_3);
//		
//		if(!(this.h14.equals(o.h14)))
//			return h14.compareTo(o.h14);
//		
//		if(!(this.h14_3.equals(o.h14_3)))
//			return h14_3.compareTo(o.h14_3);
//		
//		if(!(this.h15.equals(o.h15)))
//			return h15.compareTo(o.h15);
//		
//		if(!(this.h15_3.equals(o.h15_3)))
//			return h15_3.compareTo(o.h15_3);
//		
//		if(!(this.h16.equals(o.h16)))
//			return h16.compareTo(o.h16);
//		
//		if(!(this.h16_3.equals(o.h16_3)))
//			return h16_3.compareTo(o.h16_3);
//		
//		if(!(this.h17.equals(o.h17)))
//			return h17.compareTo(o.h17);
//		
//		if(!(this.h17_3.equals(o.h17_3)))
//			return h17_3.compareTo(o.h17_3);
//		
//		if(!(this.h18.equals(o.h18)))
//			return h18.compareTo(o.h18);
//		
//		return 0;
//	
//	}
//}
