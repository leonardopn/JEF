package model.services;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import db.DB;

public class Atualizar {
	
	static PreparedStatement st = null;
	static ResultSet rs = null;
	
	public static boolean atualizarCliente(int id, String nome, String email, String telefone, String redeSocial ) {
		boolean count = true;
		try {
			st = DB.getConnection().prepareStatement("select nome from cliente where nome = ?");
			st.setString(1, nome);
			rs = st.executeQuery();
			count = rs.next();
			if(count == false) {
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
	
	public static void atualizarFuncionario(String nome, String telefone, String cpf) {
		try {
			st = DB.getConnection().prepareStatement(
					"UPDATE funcionario "
					+"SET nome = ?, telefone = ?"
					+"WHERE cpffuncionario=?");
			st.setString(1, nome);
			st.setString(2, telefone); 
			st.setString(3, cpf); 
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
	
	public static void atualizarSalario(String cpf, double salario) {
		try {
			st = DB.getConnection().prepareStatement(
					"UPDATE funcionario "
					+"set salario = salario + ? "
					+"WHERE cpffuncionario = ?");
			st.setDouble(1, salario);
			st.setString(2, cpf); 
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
	
	public static void atualizarTotalCaixa(String data, double total, double cartao, double dinheiro) {
		try {
			st = DB.getConnection().prepareStatement(
					"UPDATE fechamento_caixa "
					+"SET total = ?, total_cartao = ?, total_dinheiro = ?"
					+"WHERE data=?");
			st.setDouble(1, total);
			st.setDouble(2, cartao); 
			st.setDouble(3, dinheiro);
			st.setString(4, data);
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
