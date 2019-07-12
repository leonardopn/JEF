package gui.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import application.Main;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import model.services.Carregar;
import model.services.Salvar;

public class ViewController implements Initializable{

	private static Scene caixa;
	private static Scene funcionario;
	private static Scene cliente;
	
	@FXML
	private Button btCriaFuncionario;	
	
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
		Salvar.salvar();
	}
	
	public void onBtCarregarAction(){
		Carregar.carregar();
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		Carregar.carregar();
	}

}
