package gui.controller;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

import org.controlsfx.control.textfield.TextFields;

import application.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import model.entities.Agenda;
import model.entities.Agendamento;
import model.entities.Cliente;
import model.services.Cadastro;
import model.services.Carregar;

public class ViewAtualizaAgendaController implements Initializable{
	
	ObservableList<Agendamento> obAgendamento;
	ObservableList<Cliente> obCliente;
	ObservableList<Agendamento> obAgendamentoExcluido;
	
	@FXML
	private Button btAtualiza;
	
	@FXML
	private CheckBox cb12;
	
	@FXML
	private CheckBox cb12_3;
	
	@FXML
	private CheckBox cb13;
	
	@FXML
	private CheckBox cb13_3;
	
	@FXML
	private CheckBox cb14;
	
	@FXML
	private CheckBox cb14_3;
	
	@FXML
	private CheckBox cb15;
	
	@FXML
	private CheckBox cb15_3;
	
	@FXML
	private CheckBox cb16;
	
	@FXML
	private CheckBox cb16_3;
	
	@FXML
	private CheckBox cb17;
	
	@FXML
	private CheckBox cb17_3;
	
	@FXML
	private CheckBox cb18;
	
	@FXML
	private Button btBuscar;
	
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
	
	public void selecionaHorario() {
		cb12.setSelected(false);
		cb12_3.setSelected(false);
		cb13.setSelected(false);
		cb13_3.setSelected(false);
		cb14.setSelected(false);
		cb14_3.setSelected(false);
		cb15.setSelected(false);
		cb15_3.setSelected(false);
		cb16.setSelected(false);
		cb16_3.setSelected(false);
		cb17.setSelected(false);
		cb17_3.setSelected(false);
		cb18.setSelected(false);
		Agendamento agendamento = tvAgendamento.getSelectionModel().getSelectedItem();
		Carregar.carregaAgenda(dpData.getValue());
		Agenda agenda = null;
		for(Agenda age : Cadastro.agendas) {
			if(agendamento.getFuncionario().equals(age.getFuncionario())){
				agenda = age;
			}
		}
		if(!(agenda.getH12().equals("Livre"))){
			cb12.setSelected(true);
		}
		if(!(agenda.getH12_3().equals("Livre"))){
			cb12_3.setSelected(true);
		}
		if(!(agenda.getH13().equals("Livre"))){
			cb13.setSelected(true);
		}
		if(!(agenda.getH13_3().equals("Livre"))){
			cb13_3.setSelected(true);
		}
		if(!(agenda.getH14().equals("Livre"))){
			cb14.setSelected(true);
		}
		if(!(agenda.getH14_3().equals("Livre"))){
			cb14_3.setSelected(true);
		}
		if(!(agenda.getH15().equals("Livre"))){
			cb15.setSelected(true);
		}
		if(!(agenda.getH15_3().equals("Livre"))){
			cb15_3.setSelected(true);
		}
		if(!(agenda.getH16().equals("Livre"))){
			cb16.setSelected(true);
		}
		if(!(agenda.getH16_3().equals("Livre"))){
			cb16_3.setSelected(true);
		}
		if(!(agenda.getH17().equals("Livre"))){
			cb17.setSelected(true);
		}
		if(!(agenda.getH17_3().equals("Livre"))){
			cb17_3.setSelected(true);
		}
		if(!(agenda.getH18().equals("Livre"))){
			cb18.setSelected(true);
		}
		
		
	}
	
	public void carregaAgendamento() {
		Carregar.carregaAgendaCliente(dpData.getValue(), txtCliente.getText());
		obAgendamento = FXCollections.observableArrayList(Cadastro.agendamentos);
		tvAgendamento.setItems(obAgendamento);
        
	}
	
	public void carregaCliente() {
		obCliente = FXCollections.observableArrayList(Cadastro.clientes);
		TextFields.bindAutoCompletion(txtCliente, obCliente);
	}
	
	public void voltaScene() {
		try {
			Parent fxmlMain = FXMLLoader.load(getClass().getResource("/gui/view/View.fxml"));
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
		carregaCliente();
		
	}

}
