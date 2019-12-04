package model.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import db.DB;
import gui.util.Alerts;
import javafx.scene.control.Alert.AlertType;
import model.entities.Cliente;
import model.services.Cadastro;
import javafx.scene.control.TextField;

public class DaoCliente {
	static PreparedStatement st = null;
	static ResultSet rs = null;
	
	public static boolean salvarCliente(TextField txtNomeCliente, TextField txtEmailCliente, TextField txtTelefoneCliente, TextField txtRedeSocialCliente) {
		boolean count = true;
		try {
			st = DB.getConnection().prepareStatement("select nome from cliente where nome = ?");
			st.setString(1, txtNomeCliente.getText());
			rs = st.executeQuery();
			count = rs.next();
			if(count == false) {
				st = DB.getConnection().prepareStatement(
						"INSERT INTO cliente "
						+ "(email, nome, rede_social, telefone) "
						+ "VALUES "
						+ "(?, ?, ?, ?)");
				st.setString(1, txtEmailCliente.getText());
				st.setString(2, txtNomeCliente.getText());
				st.setString(3, txtRedeSocialCliente.getText());
				st.setString(4, txtTelefoneCliente.getText());
				st.execute();
			}
		}
		catch(SQLException e) {
			Alerts.showAlert("ERRO!", e.getMessage(), null, AlertType.ERROR);
		}
		finally {
			DB.fechaStatement(st);
			DB.fechaResultSet(rs);
			DB.closeConnection();
		}
		return count;
	}
	
	public static void carregaCliente() {
		Cadastro.clientes.clear();
		try {	
			Cadastro.clientes.clear();
			 st = DB.getConnection().prepareStatement("select * from cliente");
			 rs = st.executeQuery();
			 while(rs.next()) {
				 if(rs.getInt("status") !=0) {
					 Cliente cliente = new Cliente(rs.getInt("idcliente"), rs.getString("nome"), rs.getString("email"), rs.getString("telefone"), rs.getString("rede_social"));
					 Cadastro.clientes.add(cliente);
				 }
			 }
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		finally {
			DB.closeConnection();
			DB.fechaResultSet(rs);
			DB.fechaStatement(st);
		}
	}
	
	public static boolean atualizarCliente(int id, String nome, String email, String telefone, String redeSocial ) {
		boolean count = true;
		try {
			st = DB.getConnection().prepareStatement("select idcliente from cliente where nome = ?");
			st.setString(1, nome);
			rs = st.executeQuery();
			count = rs.next();
			if(count == false || rs.getInt(1) == id) {
				st = DB.getConnection().prepareStatement(
						"UPDATE cliente "
						+"SET nome = ?, email = ?, telefone = ?, rede_social = ?"
						+"WHERE idcliente=(?)");
				st.setString(1, nome);
				st.setString(2, email);
				st.setString(3, telefone);
				st.setString(4, redeSocial); 
				st.setInt(5, id); 
				st.execute();
				return false;
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		finally {
			DB.fechaStatement(st);
			DB.fechaResultSet(rs);
			DB.closeConnection();
		}
		return count;
	}
	
	public static void excluirCliente(Cliente cli) {
		try {
			st = DB.getConnection().prepareStatement("UPDATE cliente "
													+ "SET status = 0 WHERE idcliente= (?)");
			st.setInt(1, cli.getId()); 
			st.execute();
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		finally {
			DB.fechaStatement(st);
			DB.closeConnection();
		}
	}
}
