package model.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import application.Main;
import db.DB;
import gui.controllers.ViewLoginController;
import gui.util.Notificacoes;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import model.entities.Login;

public class DaoLogin {
	static private PreparedStatement st = null;
	static private ResultSet rs = null;
	
	public static boolean carregaLogin(Login login, TextField tfUsuario, PasswordField pfSenha) {
		try {	
			 st = DB.getConnection().prepareStatement("select * from usuario");
			 rs = st.executeQuery();
			 ArrayList<Login> arrayLogin = new ArrayList<>();
			 while(rs.next()) {
				 String usuario = rs.getString("login");
				 String senha = rs.getString("senha");
				 Login loginTemp = new Login(usuario, senha);
				 arrayLogin.add(loginTemp);
			 }
			 if(arrayLogin.contains(login)) {
				Main.getStage().setScene(Main.getMain());
				Main.getStage().centerOnScreen();
				arrayLogin.clear();;
				return true;
			}
			else {
				int tentativas = ViewLoginController.getTentativas();
				Notificacoes.mostraNotificacao("Erro de login!!", "Usuário ou senha incorreto\nEssa é a tentativa: "+(tentativas)+" de 3");
				tfUsuario.clear();
				pfSenha.clear();
				tentativas += 1;
				ViewLoginController.setTentativas(tentativas);
				return false;
			}
		}
		catch(SQLException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
			System.exit(0);
		}
		finally {
			DB.closeConnection();
			DB.fechaResultSet(rs);
			DB.fechaStatement(st);
		}
		return false;
	}
}
