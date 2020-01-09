package gui.controllers;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;

import application.Main;
import gui.util.Notificacoes;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import model.collection.entities.Login;
import model.dao.DaoLogin;

public class ViewLoginController implements Initializable {
	static Statement st = null;
	static ResultSet rs = null;
	private static int tentativas = 1;

	final Image olhoFechado = new Image((getClass().getResourceAsStream("/model/images/icons8_closed_eye_100px.png")));
	final Image olhoAberto = new Image((getClass().getResourceAsStream("/model/images/icons8_eye_48px.png")));

	@FXML
	private ImageView ivOlho;

	@FXML
	private Button btLogin;

	@FXML
	private TextField tfUsuario;

	@FXML
	private TextField tfSenhaVisivel;

	@FXML
	private PasswordField pfSenha;

	@FXML
	public void onBtLoginAction() {
		String useTemp = tfUsuario.getText();
		String passTemp = pfSenha.getText();
		Login login = new Login(useTemp, passTemp);
		if (useTemp.isEmpty() || passTemp.isEmpty()) {
			Notificacoes.mostraNotificacao("Aviso!", "Há campos vazios, preencha usuário ou senha!");
		} else {
			ivOlho.setImage(olhoFechado);
			tfSenhaVisivel.setVisible(false);
			pfSenha.setVisible(true);
			if (DaoLogin.carregaLogin(login, tfUsuario, pfSenha) == false && tentativas > 4) {
				Main.getStage().close();
			}
		}

	}

	@FXML
	public void onBtOlhoAction() {
		if (ivOlho.getImage() == olhoAberto) {
			ivOlho.setImage(olhoFechado);
			tfSenhaVisivel.setVisible(false);
			pfSenha.setVisible(true);
		} else {
			ivOlho.setImage(olhoAberto);
			tfSenhaVisivel.setText(pfSenha.getText());
			tfSenhaVisivel.setVisible(true);
			pfSenha.setVisible(false);
		}
	}

	public static int getTentativas() {
		return tentativas;
	}

	public static void setTentativas(int tentativas) {
		ViewLoginController.tentativas = tentativas;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		btLogin.setDefaultButton(true);
	}
}
