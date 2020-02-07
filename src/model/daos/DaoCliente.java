package model.daos;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import dataBase.DbUtils;
import gui.utils.AlertsUtils;
import gui.utils.GeraLogUtils;
import javafx.scene.control.Alert.AlertType;
import javafx.application.Platform;
import javafx.scene.control.TextField;
import model.collections.Colecao;
import model.collections.entities.Cliente;

public class DaoCliente {
	private static PreparedStatement st = null;
	private static ResultSet rs = null;
	
	public static boolean salvarCliente(TextField txtNomeCliente, TextField txtEmailCliente, TextField txtTelefoneCliente, TextField txtRedeSocialCliente) {
		boolean count = true;
		try {
			String query = "select nome from cliente where nome = ?";
			st = DbUtils.getConnection().prepareStatement(query);
			st.setString(1, txtNomeCliente.getText());
			rs = st.executeQuery();
			count = rs.next();
			if(!count) {
				st = DbUtils.getConnection().prepareStatement(
						"INSERT INTO cliente "
						+ "(email, nome, rede_social, telefone) "
						+ "VALUES "
						+ "(?, ?, ?, ?)");
				st.setString(1, txtEmailCliente.getText());
				st.setString(2, txtNomeCliente.getText());
				st.setString(3, txtRedeSocialCliente.getText());
				st.setString(4, txtTelefoneCliente.getText());
				GeraLogUtils.gravarLogQuery(st.toString());
				st.execute();
			}
		}
		catch(SQLException e) {
			AlertsUtils.showAlert("ERRO", "Algum problema aconteceu, contate o ADMINISTRADOR", e.getMessage(), AlertType.ERROR);
			GeraLogUtils.gravarLogQuery("ERRO "+e.getMessage());
		}
		finally {
			DbUtils.fechaStatement(st);
			DbUtils.fechaResultSet(rs);
			DbUtils.closeConnection();
		}
		return count;
	}
	
	public static void carregaCliente() {
		Colecao.clientes.clear();
		try {	
			Colecao.clientes.clear();
			 st = DbUtils.getConnection().prepareStatement("select * from cliente");
			 rs = st.executeQuery();
			 while(rs.next()) {
				 if(rs.getInt("status") !=0) {
					 Cliente cliente = new Cliente(rs.getInt("idcliente"), rs.getString("nome"), rs.getString("email"), rs.getString("telefone"), rs.getString("rede_social"));
					 Colecao.clientes.add(cliente);
				 }
			 }
		}
		catch(SQLException e) {
			AlertsUtils.showAlert("ERRO", "Algum problema aconteceu, contate o ADMINISTRADOR", e.getMessage(), AlertType.ERROR);
			GeraLogUtils.gravarLogQuery("ERRO" + e.getMessage());
		}
		finally {
			DbUtils.closeConnection();
			DbUtils.fechaResultSet(rs);
			DbUtils.fechaStatement(st);
		}
	}
	
	public static boolean atualizarCliente(int id, String nome, String email, String telefone, String redeSocial ) {
		boolean count = true;
		try {
			String query = "select idcliente from cliente where nome = ?";
			st = DbUtils.getConnection().prepareStatement(query);
			st.setString(1, nome);
			rs = st.executeQuery();
			count = rs.next();
			if(!(count) || rs.getInt(1) == id) {
				st = DbUtils.getConnection().prepareStatement(
						"UPDATE cliente "
						+"SET nome = ?, email = ?, telefone = ?, rede_social = ? "
						+"WHERE idcliente=(?)");
				st.setString(1, nome);
				st.setString(2, email);
				st.setString(3, telefone);
				st.setString(4, redeSocial); 
				st.setInt(5, id); 
				GeraLogUtils.gravarLogQuery(st.toString());
				st.execute();
				return false;
			}
		}
		catch(SQLException e) {
			AlertsUtils.showAlert("ERRO", "Algum problema aconteceu, contate o ADMINISTRADOR", e.getMessage(), AlertType.ERROR);
			GeraLogUtils.gravarLogQuery("ERRO "+e.getMessage());
		}
		finally {
			DbUtils.fechaStatement(st);
			DbUtils.fechaResultSet(rs);
			DbUtils.closeConnection();
		}
		return count;
	}
	
	public static void excluirCliente(Cliente cli) {
		try {
			String query = "UPDATE cliente SET status = 0 WHERE idcliente= (?)";
			st = DbUtils.getConnection().prepareStatement(query);
			st.setInt(1, cli.getId()); 
			st.execute();
			GeraLogUtils.gravarLogQuery(st.toString());
		}
		catch(SQLException e) {
			AlertsUtils.showAlert("ERRO", "Algum problema aconteceu, contate o ADMINISTRADOR", e.getMessage(), AlertType.ERROR);
			GeraLogUtils.gravarLogQuery("ERRO "+e.getMessage());
		}
		finally {
			DbUtils.fechaStatement(st);
			DbUtils.closeConnection();
		}
	}

	public static ArrayList<Cliente> buscaCliente(String busca, int grupo) {
		ArrayList<Cliente> clienteTemp = new ArrayList<Cliente>();
		try {
			if(grupo == 1) {
				st = DbUtils.getConnection().prepareStatement("select * from cliente where status = 1 and idcliente = ? or status = 1 and nome like ? or status = 1 and telefone like ? or status = 1 and email like ? or status = 1 and rede_social like ?");
				st.setString(1, busca);
				st.setString(2, "%"+busca+"%");
				st.setString(3, "%"+busca+"%");
				st.setString(4, "%"+busca+"%");
				st.setString(5, "%"+busca+"%");
			}
			else {
				if(grupo == 2) {
					st = DbUtils.getConnection().prepareStatement("select * from cliente where idcliente = ? and status = 1;");
					st.setString(1, busca);
				}
				else {
					if(grupo == 3) {
						st = DbUtils.getConnection().prepareStatement("select * from cliente where nome like ? and status = 1;");
						st.setString(1, "%"+busca+"%");
					}
					else {
						if(grupo == 4){
							st = DbUtils.getConnection().prepareStatement("select * from cliente where email like ? and status = 1;");
							st.setString(1, "%"+busca+"%");
						}
						else {
							if(grupo == 5) {
								st = DbUtils.getConnection().prepareStatement("select * from cliente where telefone like ? and status = 1;");
								st.setString(1, "%"+busca+"%");
							}
							else {
								st = DbUtils.getConnection().prepareStatement("select * from cliente where rede_social like ? and status = 1;");
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
					AlertsUtils.showAlert("ERRO", "Erro durante a conexão com o banco!", e.getMessage(), AlertType.ERROR);
					GeraLogUtils.gravarLogQuery("ERRO" + e.getMessage());
				}
			});
		}
		finally {
			DbUtils.fechaStatement(st);
			DbUtils.closeConnection();
		}
		return clienteTemp;
	}
}
