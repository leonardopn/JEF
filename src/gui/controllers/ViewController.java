package gui.controllers;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.Date;
import java.util.ResourceBundle;

import org.controlsfx.control.textfield.TextFields;

import application.Main;
import gui.util.Alerts;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.entities.Agendamento;
import model.entities.Funcionario;
import model.services.Cadastro;
import model.services.Carregar;
import model.services.Excluir;

public class ViewController implements Initializable{
	
	private static Scene caixa;
	private static Scene funcionario;
	private static Scene cliente;
	private static Scene agendamento;
	private static Funcionario funTemp;
	private static LocalDate dpDataTemp;
	private static TableView<Funcionario> tvAgendaTemp;
	private static Stage stageAgenda;
	ObservableList<Agendamento> obAgendamento;
	
	@FXML
	private Button btCriaFuncionario;
	
	@FXML
	private DatePicker dpData;
	
	@FXML
	private DatePicker dpDataExcluir;
	
	@FXML
    private TextField tfCliente;
	
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
	private TableView<Funcionario> tvAgenda = new TableView<>();
	
	@FXML
	private TableView<Funcionario> tvFuncionario = new TableView<>();
	
	@FXML
	private TableColumn<Funcionario, String> colunaFuncionario;
	
	@FXML
	private TableColumn<Funcionario, String> coluna12;
	
	@FXML
	private TableColumn<Funcionario, String> coluna12_3;
	
	@FXML
	private TableColumn<Funcionario, String> coluna13;
	
	@FXML
	private TableColumn<Funcionario, String> coluna13_3;
	
	@FXML
	private TableColumn<Funcionario, String> coluna14;
	
	@FXML
	private TableColumn<Funcionario, String> coluna14_3;
	
	@FXML
	private TableColumn<Funcionario, String> coluna15;
	
	@FXML
	private TableColumn<Funcionario, String> coluna15_3;
	
	@FXML
	private TableColumn<Funcionario, String> coluna16;
	
	@FXML
	private TableColumn<Funcionario, String> coluna16_3;
	
	@FXML
	private TableColumn<Funcionario, String> coluna17;
	
	@FXML
	private TableColumn<Funcionario, String> coluna17_3;
	
	@FXML
	private TableColumn<Funcionario, String> coluna18;
	
	@FXML
    private Button btPesquisar;

    @FXML
    private TableView<Agendamento> tvAgendamento;

    @FXML
    private TableColumn<Agendamento, String> colunaCliente;

    @FXML
    private TableColumn<Agendamento, String> colunaHorario;

    @FXML
    private TableColumn<Agendamento, CheckBox> colunaExcluir;
    
    @FXML
    private TableColumn<Agendamento, CheckBox> colunaFuncionarioAgendamento;

    @FXML
    private Button btExcluir;
	
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
		Main.getStage().centerOnScreen();
	}
	
	public void carregaFuncionario() {
		ObservableList<Funcionario> obFuncionario = FXCollections.observableArrayList(Cadastro.funcionarios);
		tvFuncionario.setItems(obFuncionario);
		colunaFuncionario.setCellValueFactory(new PropertyValueFactory<>("nome"));
	}
	
	@FXML
	public void carregaAgendamento() {
		if(!(tvAgenda.getSelectionModel().isEmpty())) {
			retornaInformacaoAgenda();
			try {
				Parent fxmlAgenda = FXMLLoader.load(getClass().getResource("/gui/view/ViewAgenda.fxml"));
				Scene agenda = new Scene(fxmlAgenda);
				stageAgenda = new Stage();
				stageAgenda.setScene(agenda);
				stageAgenda.show();
				stageAgenda.centerOnScreen();
			}
			catch(IOException e) {
				e.printStackTrace();
			}
		}
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
		Main.getStage().centerOnScreen();
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
		Main.getStage().centerOnScreen();
	}
	
	@FXML
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
		dpDataExcluir.setValue(dpData.getValue());
		tvAgenda.refresh();
		ObservableList<Funcionario> obAgenda = FXCollections.observableArrayList(Cadastro.funcionarios);
		tvAgenda.setItems(obAgenda);
	}
	
	public void retornaInformacaoAgenda() {
		funTemp = this.tvAgenda.getSelectionModel().getSelectedItem();
		dpDataTemp = this.dpData.getValue();
		tvAgendaTemp = this.tvAgenda;
	}
	
	public static Scene getCaixa() {
		return caixa;
	}
	
	public static Funcionario getFunTemp() {
		return funTemp;
	}
	
	public static LocalDate getDpDataTemp() {
		return dpDataTemp;
	}
	
	public static TableView<Funcionario> getTvAgendaTemp(){
		return tvAgendaTemp;
	}

	public static Scene getFuncionario() {
		return funcionario;
	}

	public static Scene getCliente() {
		return cliente;
	}
	
	public static Stage getStageAgenda() {
		return stageAgenda;
	}
	
	public void onBtCarregarAction(){
		carregaFuncionario();
	}
	
	@FXML
    public void pesquisaAgendamento() {
    	Carregar.carregaAgendamento(dpDataExcluir.getValue(), tfCliente.getText());
    	populaTabela();
    }
   
   private void populaTabela() {
	   obAgendamento = FXCollections.observableArrayList(Cadastro.agendamentos);
	   colunaCliente.setCellValueFactory(new PropertyValueFactory<>("cliente"));
	   colunaHorario.setCellValueFactory(new PropertyValueFactory<>("horario"));
	   colunaExcluir.setCellValueFactory(new PropertyValueFactory<>("select"));
	   colunaFuncionarioAgendamento.setCellValueFactory(new PropertyValueFactory<>("funcionario"));
       tvAgendamento.setItems(obAgendamento);
   }
   
   @FXML
   public void excluirAgendamento() {
		if(Alerts.showAlertExclusao()) {
			ObservableList<Agendamento> obExcluirAgendamento = FXCollections.observableArrayList();
			for(Agendamento age : obAgendamento) {
				if(age.getSelect().isSelected()) {
					obExcluirAgendamento.add(age);
					Excluir.excluirAgendamento(age);
				}
			}
			obAgendamento.removeAll(obExcluirAgendamento);
			Cadastro.agendamentos.removeAll(obExcluirAgendamento);
			carregaAgenda();
		}
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		dpData.setValue(LocalDate.now());
		Carregar.carregar();	
		carregaFuncionario();
		carregaAgenda();
		TextFields.bindAutoCompletion(tfCliente, Cadastro.clientes);
		dpDataExcluir.setValue(LocalDate.now());
	}
}
