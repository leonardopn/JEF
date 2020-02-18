package model.daos;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import application.Main;
import dataBase.DbUtils;
import gui.controllers.ViewLoginController;
import gui.utils.AlertsUtils;
import gui.utils.GeraLogUtils;
import gui.utils.NotificacoesUtils;
import javafx.scene.control.Alert.AlertType;
import model.collections.entities.Login;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class DaoLogin {
	private static PreparedStatement st = null;
	private static ResultSet rs = null;
	private static ArrayList<Login> arrayLogin = new ArrayList<>();

	public static void carregaLogin() {

		try {
			st = DbUtils.getConnection().prepareStatement("select * from usuario");
			rs = st.executeQuery();
			while (rs.next()) {
				String usuario = rs.getString("login");
				String senha = rs.getString("senha");
				Login loginTemp = new Login(usuario, senha);
				arrayLogin.add(loginTemp);
			}
		} catch (SQLException e) {
			AlertsUtils.showAlert("ERRO", "Algum problema aconteceu, contate o ADMINISTRADOR", e.getMessage(),
					AlertType.ERROR);
			GeraLogUtils.gravarLogQuery("ERRO" + e.getMessage());
			System.exit(0);
		} finally {
			DbUtils.closeConnection();
			DbUtils.fechaResultSet(rs);
			DbUtils.fechaStatement(st);
		}

	}

	public static boolean carregaLogin(Login login, TextField tfUsuario, PasswordField pfSenha) {
		if (arrayLogin.contains(login)) {
			Main.getStage().setScene(Main.getMain());
			Main.getStage().centerOnScreen();
			arrayLogin.clear();
			return true;
		} else {
			int tentativas = ViewLoginController.getTentativas();
			NotificacoesUtils.mostraNotificacoes("Erro de login!!",
					"Usuário ou senha incorreto\nEssa é a tentativa: " + (tentativas) + " de 3");
			tfUsuario.clear();
			pfSenha.clear();
			tentativas += 1;
			ViewLoginController.setTentativas(tentativas);
			return false;
		}
	}

	public static boolean carregaLoginConfirmacao(Login login, TextField tfUsuario, PasswordField pfSenha) {
		try {
			st = DbUtils.getConnection().prepareStatement("select * from usuario");
			rs = st.executeQuery();
			ArrayList<Login> arrayLogin = new ArrayList<>();
			while (rs.next()) {
				String usuario = rs.getString("login");
				String senha = rs.getString("senha");
				Login loginTemp = new Login(usuario, senha);
				arrayLogin.add(loginTemp);
			}
			if (arrayLogin.contains(login)) {
				arrayLogin.clear();
				return true;
			} else {
				NotificacoesUtils.mostraNotificacoes("Erro de login!!", "Usuário ou senha incorreto");
				tfUsuario.clear();
				pfSenha.clear();
				return false;
			}
		} catch (SQLException e) {
			AlertsUtils.showAlert("ERRO", "Algum problema aconteceu, contate o ADMINISTRADOR", e.getMessage(),
					AlertType.ERROR);
			GeraLogUtils.gravarLogQuery("ERRO" + e.getMessage());
		} finally {
			DbUtils.closeConnection();
			DbUtils.fechaResultSet(rs);
			DbUtils.fechaStatement(st);
		}
		return false;
	}
}
