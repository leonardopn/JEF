package model.services;

import java.util.TreeSet;

import gui.util.Alerts;
import javafx.scene.control.Alert.AlertType;
import model.entities.Cliente;
import model.entities.Funcionario;

public class Cadastro {
	static public TreeSet<Cliente> clientes = new TreeSet<>();
	static public TreeSet<Funcionario> funcionarios = new TreeSet<>();
	
	public static void verificaFuncionario(Funcionario fun) {
		if(Cadastro.funcionarios.contains(fun)) {
			Alerts.showAlert("Alerta", "Funcion√°rio j√° inscrito", "Funcion√°rio n√£o foi adicionado, pois ID j√° est√° em uso", AlertType.INFORMATION);
		}
	}
	
	public static void verificaCliente(Cliente cli) {
		if(Cadastro.clientes.contains(cli)) {
			Alerts.showAlert("Alerta", "CLiente j· inscrito", "Cliente n„o foi adicionado, pois ID j· est· em uso", AlertType.INFORMATION);
		}
	}
}
