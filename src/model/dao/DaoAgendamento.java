package model.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import db.DB;
import model.collection.Colecao;
import model.collection.entities.Agendamento;
import model.collection.entities.Cliente;
import model.collection.entities.Funcionario;

public class DaoAgendamento {
	static private PreparedStatement st = null;
	static private ResultSet rs = null;
	
	public static void salvarAgendamento(String funcionario, int cliente, LocalDate dpData, String horario) {
		try {
			SimpleDateFormat formataData = new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat formataHora = new SimpleDateFormat("HH:mm:ss");
			Date data = formataData.parse(dpData.toString());
			for(Funcionario fun : Colecao.funcionarios) {
				if(funcionario.equals(fun.getNome())) {
					funcionario = fun.getCpf();
				}
			}
			
			st = DB.getConnection().prepareStatement(
						"INSERT INTO agenda"
						+ "(idcliente, cpffuncionario, data, time) "
						+ "VALUES "
						+ "(?, ?, ?, ? )");
			st.setInt(1, cliente);
			st.setString(2, funcionario);
			st.setDate(3, new java.sql.Date(data.getTime()));
			st.setTime(4, new java.sql.Time(formataHora.parse(horario).getTime()));
			st.execute();
		}
		catch(SQLException | ParseException e) {
			e.printStackTrace();
		}
		finally {
			DB.fechaStatement(st);
			DB.closeConnection();
		}
	}
	
	public static void carregaAgendamento(LocalDate data, String cliente) {
		Colecao.agendamentos.clear();
		int idCliente = 0;
		DateTimeFormatter localDateFormatada = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		for(Cliente cli : Colecao.clientes) {
			if(cliente.equals(cli.getNome())) {
				idCliente = cli.getId();
			}
		}
		try {
			 st = DB.getConnection().prepareStatement("SELECT * FROM agenda a inner join funcionario f on(a.cpffuncionario = f.cpffuncionario) "
	 					+ "inner join cliente c on(c.idcliente = a.idcliente)"
	 					+ "WHERE data = ?"
	 					+ " AND c.idcliente = ?");
			 st.setString(1, localDateFormatada.format(data));
			 st.setInt(2, idCliente);
			 rs = st.executeQuery();
			 SimpleDateFormat formataData = new SimpleDateFormat("dd/MM/yyyy");
			 SimpleDateFormat formataHora = new SimpleDateFormat("HH:mm");
			 while(rs.next()) {
				 Agendamento agen = new Agendamento(rs.getString("f.nome"), rs.getString("f.cpffuncionario"), rs.getString("c.nome"), rs.getInt("c.idcliente"), formataData.format(rs.getDate("data")), formataHora.format(rs.getTime("a.time")));
				 Colecao.agendamentos.add(agen);
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
	
	public static void excluirAgendamento(Agendamento age, LocalDate data) {
		try {
			SimpleDateFormat formataData = new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat formataHora = new SimpleDateFormat("HH:mm");
			st = DB.getConnection().prepareStatement(
					"DELETE FROM agenda "
					+ "WHERE idcliente= (?)"
					+ "AND data = (?)"
					+ "AND time = (?)"
					+"AND cpffuncionario = (?)");
			Date dataFormatada = formataData.parse(data.toString());
			st.setInt(1, age.getIdCliente()); 
			st.setDate(2, new java.sql.Date(dataFormatada.getTime())); 
			st.setTime(3, new java.sql.Time(formataHora.parse(age.getHorario()).getTime())); 
			st.setString(4, age.getCpfFuncionario());
			st.execute();
		}
		catch(SQLException | ParseException e) {
			e.printStackTrace();
		}
		finally {
			DB.fechaStatement(st);
			DB.closeConnection();
		}
	}
}
