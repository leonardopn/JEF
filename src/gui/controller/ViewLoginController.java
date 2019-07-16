package gui.controller;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import application.Main;
import db.DB;
import gui.util.Alerts;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import model.entities.Login;
import model.exceptions.DbException;

public class ViewLoginController {
	static Statement st = null;
	static ResultSet rs = null;
	private static int tentativas = 0;
	
	@FXML
	private Button btLogin; 
	
	@FXML
	private TextField tfUsuario;
	
	@FXML
	private PasswordField pfSenha;
	
	@FXML
	public void onBtLoginAction() {
		if(tentativas != 2) {
				String useTemp = tfUsuario.getText();
				String passTemp = pfSenha.getText();
				Login login = new Login(useTemp, passTemp);
				carregaLogin(login);
		}
		else {
			Main.fechaProgram();
		}
	}
	
	public static void carregaLogin(Login login) {
		try {	
			 st = DB.getConnection().createStatement();
			 rs = st.executeQuery("select * from usuario");
			 ArrayList<Login> arrayLogin = new ArrayList<>();
			 while(rs.next()) {
				 String usuario = rs.getString("login");
				 String senha = rs.getString("senha");
				 Login loginTemp = new Login(usuario, senha);
				 arrayLogin.add(loginTemp);
			 }
			 if(arrayLogin.contains(login)) {
				Main.getStage().setScene(Main.getMain());
			}
			else {
				Alerts.showAlert("Erro no login", "Usuário ou senha incorreto", null, AlertType.INFORMATION);
			}
		}
		catch(SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeConnection();
			DB.fechaResultSet(rs);
			DB.fechaStatement(st);
			tentativas += 1;
		}
	}
}
