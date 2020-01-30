package model.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import db.DB;
import gui.util.Alerts;
import javafx.scene.control.Alert.AlertType;
import javafx.application.Platform;
import javafx.scene.control.TextField;
import model.collection.Colecao;
import model.collection.entities.Cliente;

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
			Alerts.showAlert("ERRO", "Algum problema aconteceu, contate o ADMINISTRADOR", e.getMessage(), AlertType.ERROR);
		}
		finally {
			DB.fechaStatement(st);
			DB.fechaResultSet(rs);
			DB.closeConnection();
		}
		return count;
	}
	
	public static void carregaCliente() {
		Colecao.clientes.clear();
		try {	
			Colecao.clientes.clear();
			 st = DB.getConnection().prepareStatement("select * from cliente");
			 rs = st.executeQuery();
			 while(rs.next()) {
				 if(rs.getInt("status") !=0) {
					 Cliente cliente = new Cliente(rs.getInt("idcliente"), rs.getString("nome"), rs.getString("email"), rs.getString("telefone"), rs.getString("rede_social"));
					 Colecao.clientes.add(cliente);
				 }
			 }
		}
		catch(SQLException e) {
			Alerts.showAlert("ERRO", "Algum problema aconteceu, contate o ADMINISTRADOR", e.getMessage(), AlertType.ERROR);
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
			if(!(count) || rs.getInt(1) == id) {
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
			Alerts.showAlert("ERRO", "Algum problema aconteceu, contate o ADMINISTRADOR", e.getMessage(), AlertType.ERROR);
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
			Alerts.showAlert("ERRO", "Algum problema aconteceu, contate o ADMINISTRADOR", e.getMessage(), AlertType.ERROR);
		}
		finally {
			DB.fechaStatement(st);
			DB.closeConnection();
		}
	}

	public static ArrayList<Cliente> buscaCliente(String busca, int grupo) {
		ArrayList<Cliente> clienteTemp = new ArrayList<Cliente>();
		try {
			if(grupo == 1) {
				st = DB.getConnection().prepareStatement("select * from cliente where status = 1 and idcliente = ? or status = 1 and nome like ? or status = 1 and telefone like ? or status = 1 and email like ? or status = 1 and rede_social like ?");
				st.setString(1, busca);
				st.setString(2, "%"+busca+"%");
				st.setString(3, "%"+busca+"%");
				st.setString(4, "%"+busca+"%");
				st.setString(5, "%"+busca+"%");
			}
			else {
				if(grupo == 2) {
					st = DB.getConnection().prepareStatement("select * from cliente where idcliente = ? and status = 1;");
					st.setString(1, busca);
				}
				else {
					if(grupo == 3) {
						st = DB.getConnection().prepareStatement("select * from cliente where nome like ? and status = 1;");
						st.setString(1, "%"+busca+"%");
					}
					else {
						if(grupo == 4){
							st = DB.getConnection().prepareStatement("select * from cliente where email like ? and status = 1;");
							st.setString(1, "%"+busca+"%");
						}
						else {
							if(grupo == 5) {
								st = DB.getConnection().prepareStatement("select * from cliente where telefone like ? and status = 1;");
								st.setString(1, "%"+busca+"%");
							}
							else {
								st = DB.getConnection().prepareStatement("select * from cliente where rede_social like ? and status = 1;");
								st.setString(1, "%"+busca+"%");
							}
						}
					}
				}
			}
			rs = st.executeQuery();
			while(rs.next()) {
				Cliente cli = new Cliente(rs.getInt("idCliente"), rs.getString("nome"), rs.getString("email"), rs.getString("telefone"), rs.getString("rede_social"));
				clienteTemp.add(cli);
			}
			return clienteTemp;
		}
		catch(SQLException e) {
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					Alerts.showAlert("ERRO", "Erro durante a conexão com o banco!", e.getMessage(), AlertType.ERROR);
				}
			});
		}
		finally {
			DB.fechaStatement(st);
			DB.closeConnection();
		}
		return clienteTemp;
	}
}
