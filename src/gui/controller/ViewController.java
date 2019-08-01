package gui.controller;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
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
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.entities.Agenda;
import model.entities.Agendamento;
import model.entities.Funcionario;
import model.services.Cadastro;
import model.services.Carregar;

public class ViewController implements Initializable{

	private static Scene caixa;
	private static Scene funcionario;
	private static Scene cliente;
	private static Scene agendamento;
	ArrayList<Agenda> agenda = new ArrayList<>();
	
	
	@FXML
	private Button btCriaFuncionario;
	
	@FXML
	private DatePicker dpData;
	
	@FXML
	private ChoiceBox<Funcionario> cbManicure;
	
	@FXML
	private ChoiceBox<Date> cbHorarios;
	
	@FXML
	private Button btCriaCliente;
	
	@FXML
	private Button btCaixa;
	
	@FXML
	private Button btSalvar;
	
	@FXML
	private Button btCarregar;
	
	@FXML
	private Button btAtualizaAgendamento;
	
	@FXML
	private TableView<Agenda> tvAgenda = new TableView<>();
	
	@FXML
	private TableView<Funcionario> tvFuncionario = new TableView<>();
	
	@FXML
	private TableColumn<Agenda, String> colunaFuncionario;
	
	@FXML
	private TableColumn<Agenda, String> coluna12;
	
	@FXML
	private TableColumn<Agenda, String> coluna12_3;
	
	@FXML
	private TableColumn<Agenda, String> coluna13;
	
	@FXML
	private TableColumn<Agenda, String> coluna13_3;
	
	@FXML
	private TableColumn<Agenda, String> coluna14;
	
	@FXML
	private TableColumn<Agenda, String> coluna14_3;
	
	@FXML
	private TableColumn<Agenda, String> coluna15;
	
	@FXML
	private TableColumn<Agenda, String> coluna15_3;
	
	@FXML
	private TableColumn<Agenda, String> coluna16;
	
	@FXML
	private TableColumn<Agenda, String> coluna16_3;
	
	@FXML
	private TableColumn<Agenda, String> coluna17;
	
	@FXML
	private TableColumn<Agenda, String> coluna17_3;
	
	@FXML
	private TableColumn<Agenda, String> coluna18;
	
	@FXML
	public void onBtCriaFuncionarioAction(){
		try {
			Parent fxmlfuncionario = FXMLLoader.load(getClass().getResource("/gui/view/ViewFuncionario.fxml"));
			funcionario = new Scene(fxmlfuncionario);
		}
		catch(IOException e) {
			e.printStackTrace();
		}
		Main.getStage().setScene(funcionario);
	}
	
	@FXML
	public void onBAtualizaAgendamento(){
		try {
			Parent fxmlAgendamento = FXMLLoader.load(getClass().getResource("/gui/view/ViewAtualizaAgenda.fxml"));
			agendamento = new Scene(fxmlAgendamento);
		}
		catch(IOException e) {
			e.printStackTrace();
		}
		Main.getStage().setScene(agendamento);
	}
	
	public void carregaFuncionario() {
		ObservableList<Funcionario> obFuncionario = FXCollections.observableArrayList(Cadastro.funcionarios);
		cbManicure.setItems(obFuncionario);
		tvFuncionario.setItems(obFuncionario);
		colunaFuncionario.setCellValueFactory(new PropertyValueFactory<>("nome"));
	}
	
	public void carregaAgenda() {
		coluna12.setCellValueFactory(new PropertyValueFactory<>("h12"));
		coluna12_3.setCellValueFactory(new PropertyValueFactory<>("h12_3"));
		coluna13.setCellValueFactory(new PropertyValueFactory<>("h13"));
		coluna13_3.setCellValueFactory(new PropertyValueFactory<>("h13_3"));
		coluna14.setCellValueFactory(new PropertyValueFactory<>("h14"));
		coluna14_3.setCellValueFactory(new PropertyValueFactory<>("h14_3"));
		coluna15.setCellValueFactory(new PropertyValueFactory<>("h15"));
		coluna15_3.setCellValueFactory(new PropertyValueFactory<>("h15_3"));
		coluna16.setCellValueFactory(new PropertyValueFactory<>("h16"));
		coluna16_3.setCellValueFactory(new PropertyValueFactory<>("h16_3"));
		coluna17.setCellValueFactory(new PropertyValueFactory<>("h17"));
		coluna17_3.setCellValueFactory(new PropertyValueFactory<>("h17_3"));
		coluna18.setCellValueFactory(new PropertyValueFactory<>("h18"));
		Carregar.carregaAgendaFuncionario(dpData.getValue());
		for(Funcionario fun : Cadastro.funcionarios) {
			agenda.add(fun.getAgenda());
		}
		ObservableList<Agenda> obAgenda = FXCollections.observableArrayList(agenda);
		tvAgenda.setItems(obAgenda);
		agenda.clear();
	}
	
	public static Scene getCaixa() {
		return caixa;
	}

	public static Scene getFuncionario() {
		return funcionario;
	}

	public static Scene getCliente() {
		return cliente;
	}

	public void onBtCriaClienteAction(){
		try {
			Parent fxmlCliente = FXMLLoader.load(getClass().getResource("/gui/view/ViewCliente.fxml"));
			cliente = new Scene(fxmlCliente);
		}
		catch(IOException e) {
			e.printStackTrace();
		}
		Main.getStage().setScene(cliente);
	}
	
	public void onBtAbreCaixaAction(){
			try {
				Parent fxmlCaixa = FXMLLoader.load(getClass().getResource("/gui/view/ViewCaixa.fxml"));
				caixa = new Scene(fxmlCaixa);
			}
			catch(IOException e) {
				e.printStackTrace();
			
		}
		Main.getStage().setScene(caixa);
		
	}
	
	public void onBtCarregarAction(){
		carregaFuncionario();
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		dpData.setValue(LocalDate.now());
		Carregar.carregar();	
		carregaFuncionario();
		carregaAgenda();
	}
}
