package model.entities;

import java.util.TreeSet;

import gui.util.Alerts;
import javafx.scene.control.Alert.AlertType;

public class Caixa {
	
	private static double total;
	private static boolean status;
	static public TreeSet<Transacao> caixa = new TreeSet<>();
	
	public static double getTotal() {
		return total;
	}
	public static void setTotal(double total) {
		Caixa.total = total;
	}
	public static boolean isStatus() {
		return status;
	}
	public static void setStatus(boolean status) {
		Caixa.status = status;
	}
	
	public static void verificaTransacao(Transacao tran) {
		if(Caixa.caixa.contains(tran)) {
			Alerts.showAlert("Alerta", "Funcionário já inscrito", "Funcionário não foi adicionado, pois ID já está em uso", AlertType.INFORMATION);
		}
	}
	
}
