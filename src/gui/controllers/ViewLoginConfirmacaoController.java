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

public class ViewLoginConfirmacaoController implements Initializable {
	static Statement st = null;
	static ResultSet rs = null;
	public static boolean status = false;

	final Image olhoFechado = new Image((getClass().getResourceAsStream("/model/images/icons8_closed_eye_48px.png")));
	final Image olhoAberto = new Image((getClass().getResourceAsStream("/model/images/icons8_eye_60px_1.png")));

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
			if (!DaoLogin.carregaLoginConfirmacao(login, tfUsuario, pfSenha)) {
				status = false;
			} else {
				status = true;
				Main.getStageLoginConfirmacao().hide();
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

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		status = false;
		btLogin.setDefaultButton(true);
	}
}
