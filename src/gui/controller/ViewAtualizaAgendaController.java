package gui.controller;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import application.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import model.entities.Agendamento;
import model.services.Cadastro;
import model.services.Carregar;

public class ViewAtualizaAgendaController implements Initializable{
	
	ObservableList<Agendamento> obAgendamento;
	ObservableList<Agendamento> obAgendamentoExcluido;
	
	@FXML
	private Button btAtualiza;
	
	@FXML
	private ContextMenu cmCliente;
	
	@FXML
	private DatePicker dpData;
	
	@FXML
	private Button btVoltar;
	
	@FXML
	private TextField txtCpfCliente;
	
	@FXML
	private TextField txtCliente;
	
	@FXML
	private TextField txtNomeCliente;
	
	@FXML
	private TextField txtEmailCliente;
	
	@FXML
	private TextField txtTelefoneCliente;
	
	@FXML
	private TableView<Agendamento> tvAgendamento = new TableView<>();
	
	@FXML
	private TableColumn<Agendamento, String> colunaNome;
	
	@FXML
    private TableColumn<Agendamento, String> colunaFuncionario;
	
	@FXML
	private TableColumn<Agendamento, String> colunaData;
	
	@FXML
	private TableColumn<Agendamento, String> colunaHorario;
	
	public void carregaAgendamento() {
		Carregar.carregaAgenda(dpData.getValue());
		obAgendamento = FXCollections.observableArrayList(Cadastro.agendamentos);
		tvAgendamento.setItems(obAgendamento);
        
	}
	
	public void voltaScene() {
		try {
			Parent fxmlMain = FXMLLoader.load(getClass().getResource("/gui/view/ViewTeste.fxml"));
			Scene main = new Scene(fxmlMain);
			Main.getStage().setScene(main);
		}
		catch(IOException e) {
			e.printStackTrace();
		}
	}
	
//	@FXML
//	public void selecionaCliente() {
//		Cliente cli = tvCliente.getSelectionModel().getSelectedItem();
//		txtCpfCliente.setText(cli.getCpf());
//		txtCpfCliente.setDisable(true);
//		txtNomeCliente.setText(cli.getNome());
//		txtEmailCliente.setText(cli.getEmail());
//		txtTelefoneCliente.setText(cli.getTelefone());
//		
//	}
	
//	@FXML
//	public void atualizaCliente() {
//		if(Alerts.showAlertAtualizacao()) {
//			String cpf = txtCpfCliente.getText();
//			String nome = txtNomeCliente.getText();
//			String email = txtEmailCliente.getText();
//			String telefone = txtTelefoneCliente.getText();
//			Cliente cliente = new Cliente(cpf, nome, email, telefone);
//			Cadastro.clientes.removeIf((Cliente cli) -> cli.getCpf().equals(cliente.getCpf()));
//			Cadastro.verificaCliente(cliente);
//			Cadastro.clientes.add(cliente);
//			Atualizar.atualizarCliente(cliente);
//			voltaScene();
//		}
//	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		dpData.setValue(LocalDate.now());
		colunaNome.setCellValueFactory(new PropertyValueFactory<>("cliente"));
		colunaFuncionario.setCellValueFactory(new PropertyValueFactory<>("funcionario"));
		colunaData.setCellValueFactory(new PropertyValueFactory<>("data"));
		colunaHorario.setCellValueFactory(new PropertyValueFactory<>("horario"));
		carregaAgendamento();
	}

}
