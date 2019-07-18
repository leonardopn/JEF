package model.services;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import db.DB;
import model.entities.Cliente;
import model.entities.Funcionario;

public class Atualizar {
	
	static PreparedStatement st = null;
	
	public static void atualizarCliente(Cliente cli) {
		try {
			st = DB.getConnection().prepareStatement(
					"UPDATE cliente "
					+"SET nome = ?, email = ?, telefone = ?"
					+"WHERE id=(?)");
			st.setString(1, cli.getNome());
			st.setString(2, cli.getEmail());
			st.setString(3, cli.getTelefone());
			st.setInt(4, cli.getId()); 
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
	
	public static void atualizarFuncionario(Funcionario fun) {
		try {
			st = DB.getConnection().prepareStatement(
					"UPDATE funcionario "
					+"SET nome = ?, salario = ?"
					+"WHERE id=?");
			st.setString(1, fun.getNome());
			st.setDouble(2, fun.getSalario());
			st.setInt(3, fun.getId()); 
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
