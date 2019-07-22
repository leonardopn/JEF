package gui.controller;

import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
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
import model.entities.Agenda;
import model.entities.Funcionario;
import model.services.Cadastro;
import model.services.Carregar;

public class ViewController implements Initializable{

	private static Scene caixa;
	private static Scene funcionario;
	private static Scene cliente;
	private static Agenda agenda;
	
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
	
	public void carregaFuncionario() {
		ObservableList<Funcionario> obFuncionario = FXCollections.observableArrayList(Cadastro.funcionarios);
		cbManicure.setItems(obFuncionario);
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
	
	public void onBtSalvarAction(){
		Funcionario.criaHorarios();
	}
	
	public void onBtCarregarAction(){
		Carregar.carregar();
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		Carregar.carregar();	
		carregaFuncionario();
	}

}
