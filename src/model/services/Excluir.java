package model.services;

import java.io.File;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import db.DB;
import model.entities.Cliente;
import model.entities.Funcionario;
import model.entities.Transacao;

public class Excluir {
	static File arquivoTransacao;
	static PreparedStatement st = null;
	
	public static void excluirTransacao(Transacao tran) {
		try {
			st = DB.getConnection().prepareStatement(
					"DELETE FROM caixa "
					+ "WHERE id= "
					+ "(?)");
			st.setInt(1, tran.getId()); 
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
	
	public static void excluirFuncionario(Funcionario fun) {
		try {
			st = DB.getConnection().prepareStatement(
					"DELETE FROM funcionario "
					+ "WHERE id= "
					+ "(?)");
			st.setInt(1, fun.getId()); 
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
	
	public static void excluirCliente(Cliente cli) {
		try {
			st = DB.getConnection().prepareStatement(
					"DELETE FROM cliente "
					+ "WHERE cpf= "
					+ "(?)");
			st.setString(1, cli.getCpf()); 
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
