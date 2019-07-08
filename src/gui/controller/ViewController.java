package gui.controller;

import java.net.URL;
import java.util.ResourceBundle;

import application.Main;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import model.services.Carregar;
import model.services.Salvar;

public class ViewController implements Initializable{

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
			Main.trocaTela("funcionario");
		
	}
	
	public void onBtCriaClienteAction(){
		Main.trocaTela("cliente");
	}
	
	public void onBtAbreCaixaAction(){
		Main.trocaTela("caixa");
		
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
