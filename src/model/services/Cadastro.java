package model.services;

import java.util.ArrayList;
import java.util.TreeSet;

import gui.util.Alerts;
import javafx.scene.control.Alert.AlertType;
import model.entities.Agendamento;
import model.entities.Cliente;
import model.entities.Funcionario;

public class Cadastro {
	static public TreeSet<Cliente> clientes = new TreeSet<>();
	static public TreeSet<Funcionario> funcionarios = new TreeSet<>();
	static public ArrayList<Agendamento> agendamentos = new ArrayList<>();

	public static boolean verificaFuncionario(Funcionario fun) {
		if(Cadastro.funcionarios.contains(fun)) {
			Alerts.showAlert("Alerta", "Funcionário já inscrito", "Funcionário não foi adicionado, pois ID já está em uso", AlertType.INFORMATION);
			return false;
		}
		return true;
	}
	
	public static void verificaCliente(Cliente cli) {
		if(Cadastro.clientes.contains(cli)) {
			Alerts.showAlert("Alerta", "CLiente j� inscrito", "Cliente n�o foi adicionado, pois ID j� est� em uso", AlertType.INFORMATION);
		}
	}
}
