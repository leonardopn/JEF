package model.collections.entities;

import java.util.TreeSet;

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
}
