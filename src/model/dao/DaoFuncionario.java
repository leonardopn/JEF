package model.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import db.DB;
import javafx.scene.control.TextField;
import model.collection.Colecao;
import model.collection.entities.Funcionario;

public class DaoFuncionario {

	static PreparedStatement st = null;
	static ResultSet rs = null;
	
	public static boolean salvarFuncionario(TextField txtCpfFuncionario, TextField txtTelefoneFuncionario, TextField txtNomeFuncionario) {
		boolean count = true;
		try {
			st = DB.getConnection().prepareStatement("select * from funcionario where cpffuncionario = ?");
			st.setString(1, txtCpfFuncionario.getText());
			rs = st.executeQuery();
			count = rs.next();
			if(count == false) {
				st = DB.getConnection().prepareStatement(
						"INSERT INTO funcionario "
						+ "(cpffuncionario, nome, telefone) "
						+ "VALUES "
						+ "(?, ?, ?)");
				st.setString(1, txtCpfFuncionario.getText());
				st.setString(2, txtNomeFuncionario.getText());
				st.setString(3, txtTelefoneFuncionario.getText());  
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
	
	public static void carregaFuncionario() {
		try {
			Colecao.funcionarios.clear();
			 st = DB.getConnection().prepareStatement("select * from funcionario");
			 rs = st.executeQuery();
			 while(rs.next()) {
				 if(rs.getInt("status") !=0) {
					 Funcionario fun = new Funcionario(rs.getString("cpffuncionario"), rs.getString("telefone"), rs.getString("nome"),  rs.getDouble("salario"), rs.getInt("status"));
					 Colecao.funcionarios.add(fun);
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
	
	public static void carregaAgendaFuncionario(LocalDate data) {
		DateTimeFormatter localDateFormatada = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		try {
			 st = DB.getConnection().prepareStatement("SELECT * FROM agenda a inner join cliente c on(a.idcliente = c.idcliente) "
					 +"WHERE data = ?");
			 st.setString(1, localDateFormatada.format(data));
			 rs = st.executeQuery();
			 SimpleDateFormat formataHora = new SimpleDateFormat("HH:mm");
			 for (Funcionario fun : Colecao.funcionarios) {
				 fun.zeraHorarios();
			 }
			 while(rs.next()) {
				 for(Funcionario fun : Colecao.funcionarios) {
					if(rs.getString("cpffuncionario").equals(fun.getCpf())) {
						fun.retornaHorario(formataHora.format(rs.getTime("a.time")), rs.getString("c.nome"));
					}
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
}
