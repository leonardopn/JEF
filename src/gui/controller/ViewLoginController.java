package gui.controller;

import java.io.IOException;

import application.Main;
import gui.util.Alerts;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;

public class ViewLoginController {

	private static String usuario = "leonardo";
	private static String senha = "teste";
	
	@FXML
	private Button btLogin; 
	
	@FXML
	private TextField tfUsuario;
	
	@FXML
	private PasswordField pfSenha;
	
	@FXML
	public void onBtLoginAction() {
		String useTemp = tfUsuario.getText();
		String passTemp = pfSenha.getText();
		if(useTemp.equals(usuario) && passTemp.equals(senha)) {
			Main.getStage().setScene(Main.getMain());
		}
		else {
			Alerts.showAlert("Erro no login", "Usuário ou senha incorreto", null, AlertType.INFORMATION);
		}
	}
}
