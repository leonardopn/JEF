package gui.controllers;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;

import application.Main;
import gui.util.Notificacoes;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import model.collection.entities.Login;
import model.dao.DaoLogin;

public class ViewLoginController implements Initializable {
	static Statement st = null;
	static ResultSet rs = null;
	private static int tentativas = 1;

	final Image olhoFechado = new Image((getClass().getResourceAsStream("/model/images/icons8_closed_eye_100px.png")));
	final Image olhoAberto = new Image((getClass().getResourceAsStream("/model/images/icons8_eye_48px.png")));
	private Image profile = new Image((getClass().getResourceAsStream("/model/images/icon.png")));

	@FXML
	private ImageView ivOlho;

	@FXML
	private Button btLogin;

	@FXML
	private Circle circuloProfile;

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
			if (!(DaoLogin.carregaLogin(login, tfUsuario, pfSenha)) && tentativas > 4) {
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

	public void setProfile() {
		if (tfUsuario.getText().equals("andreia.petta")) {
			profile.getClass().getResourceAsStream("/model/images/profile/andreia.petta.png");
			circuloProfile.setFill(new ImagePattern(profile));
		}

	}

	public void setaAcaoTfUsuario() {
		tfUsuario.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (newValue.equals("andreia.petta")) {
					Image teste = new Image(
							(getClass().getResourceAsStream("/model/images/profile/andreia_petta.png")));
					circuloProfile.setFill(new ImagePattern(teste));
				} else {
					if (newValue.equals("natalia.petta")) {
						Image teste = new Image(
								(getClass().getResourceAsStream("/model/images/profile/natalia_petta.png")));
						circuloProfile.setFill(new ImagePattern(teste));
					} else {
						if (newValue.equals("leonardopn")) {
							Image teste = new Image(
									(getClass().getResourceAsStream("/model/images/profile/leonardo_petta.png")));
							circuloProfile.setFill(new ImagePattern(teste));

						} else {
							circuloProfile.setFill(new ImagePattern(profile));
						}
					}
				}
			}
		});
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		btLogin.setDefaultButton(true);
		circuloProfile.setFill(new ImagePattern(profile));
		setaAcaoTfUsuario();
	}
}
