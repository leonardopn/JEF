package gui.controllers;

import java.sql.ResultSet;
import java.sql.Statement;

import application.Main;
import gui.util.Notificacoes;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import model.collection.entities.Login;
import model.dao.DaoLogin;

public class ViewLoginController {
	static Statement st = null;
	static ResultSet rs = null;
	private static int tentativas = 1;
	
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
		Login login = new Login(useTemp, passTemp);
		if(useTemp.isEmpty() || passTemp.isEmpty()) {
			Notificacoes.mostraNotificacao("Aviso!", "Há campos vazios, preencha usuário ou senha!");
		}
		else {
			if(DaoLogin.carregaLogin(login, tfUsuario, pfSenha) == false && tentativas > 4) {
				Main.getStage().close();
			}
		}	
	}

	public static int getTentativas() {
		return tentativas;
	}

	public static void setTentativas(int tentativas) {
		ViewLoginController.tentativas = tentativas;
	}
}
