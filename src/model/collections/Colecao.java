package model.collections;

import java.util.ArrayList;
import java.util.TreeSet;

import gui.utils.AlertsUtils;
import javafx.scene.control.Alert.AlertType;
import model.collections.entities.Agendamento;
import model.collections.entities.Categoria;
import model.collections.entities.Cliente;
import model.collections.entities.Funcionario;
import model.collections.entities.Operacao;
import model.collections.entities.Pacote;
import model.collections.entities.PacoteAssociado;
import model.collections.entities.Servico;

public class Colecao {
	static public TreeSet<Cliente> clientes = new TreeSet<>();
	static public TreeSet<Funcionario> funcionarios = new TreeSet<>();
	static public TreeSet<Pacote> pacotes = new TreeSet<>();
	static public ArrayList<Agendamento> agendamentos = new ArrayList<>();
	static public ArrayList<PacoteAssociado> pacoteAssociados = new ArrayList<>();
	static public ArrayList<Servico> servicos = new ArrayList<>();
	static public ArrayList<Categoria> categorias = new ArrayList<>();
	static public ArrayList<Operacao> operacoes = new ArrayList<>();

	public static boolean verificaFuncionario(Funcionario fun) {
		if(Colecao.funcionarios.contains(fun)) {
			AlertsUtils.showAlert("Alerta", "Funcionário já inscrito", "Funcionário não foi adicionado, pois ID já está em uso", AlertType.INFORMATION);
			return false;
		}
		return true;
	}
}
