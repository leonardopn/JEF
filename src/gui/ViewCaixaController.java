package gui;

import java.net.URL;
import java.util.ResourceBundle;

import application.Main;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

public class ViewCaixaController implements Initializable {
	
	@FXML
	private Button btVoltar;
	
	@FXML
	private Button btAbrirFecharCaixa;
	
	@FXML
	private Button btEnviarTransacao;
	
	
	
	public void onBtAbrirFecharCaixaAction() {
		btAbrirFecharCaixa.setText("Fechar Caixa");
	}
	
	public void onBtVoltarAction() {
		Main.trocaTela("main");
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
	}
}
