package model.services;

import java.io.File;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import db.DB;
import model.entities.Agendamento;
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
					"UPDATE funcionario "
					+ "SET status = 0 WHERE cpffuncionario= "
					+ "(?)");
			st.setString(1, fun.getCpf()); 
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
	
	public static void excluirAgendamento(Agendamento age) {
		try {
			st = DB.getConnection().prepareStatement(
					"DELETE FROM agenda "
					+ "WHERE cliente= (?)"
					+ "AND data = (?)"
					+ "AND horario = (?)"
					+"AND funcionario = (?)");
			st.setString(1, age.getCliente()); st.setString(2, age.getData()); st.setString(3, age.getHorario()); st.setString(4, age.getFuncionario());
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
