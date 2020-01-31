package model.daos;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import dataBase.DbUtils;
import gui.utils.AlertsUtils;
import javafx.scene.control.Alert.AlertType;
import model.collections.Colecao;
import model.collections.entities.Agendamento;
import model.collections.entities.Cliente;
import model.collections.entities.Funcionario;

public class DaoAgendamento {
	private static PreparedStatement st = null;
	private static ResultSet rs = null;
	
	public static void salvarAgendamento(String funcionario, int cliente, LocalDate dpData, String horario) {
		try {
			SimpleDateFormat formataData = new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat formataHora = new SimpleDateFormat("HH:mm:ss");
			Date data = formataData.parse(dpData.toString());
			String cpfFuncionario = "";
			for(Funcionario fun : Colecao.funcionarios) {
				if(funcionario.equals(fun.getNome())) {
					cpfFuncionario = fun.getCpf();
				}
			}
			
			st = DbUtils.getConnection().prepareStatement(
						"INSERT INTO agenda"
						+ "(idcliente, cpffuncionario, data, time) "
						+ "VALUES "
						+ "(?, ?, ?, ? )");
			st.setInt(1, cliente);
			st.setString(2, cpfFuncionario);
			st.setDate(3, new java.sql.Date(data.getTime()));
			st.setTime(4, new java.sql.Time(formataHora.parse(horario).getTime()));
			st.execute();
		}
		catch(SQLException | ParseException e) {
			AlertsUtils.showAlert("ERRO", "Algum problema aconteceu, contate o ADMINISTRADOR", e.getMessage(), AlertType.ERROR);
		}
		finally {
			DbUtils.fechaStatement(st);
			DbUtils.closeConnection();
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
			 st = DbUtils.getConnection().prepareStatement("SELECT * FROM agenda a inner join funcionario f on(a.cpffuncionario = f.cpffuncionario) "
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
			AlertsUtils.showAlert("ERRO", "Algum problema aconteceu, contate o ADMINISTRADOR", e.getMessage(), AlertType.ERROR);
		}
		finally {
			DbUtils.closeConnection();
			DbUtils.fechaResultSet(rs);
			DbUtils.fechaStatement(st);
		}
	}
	
	public static void excluirAgendamento(Agendamento age, LocalDate data) {
		try {
			SimpleDateFormat formataData = new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat formataHora = new SimpleDateFormat("HH:mm");
			st = DbUtils.getConnection().prepareStatement(
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
			AlertsUtils.showAlert("ERRO", "Algum problema aconteceu, contate o ADMINISTRADOR", e.getMessage(), AlertType.ERROR);
		}
		finally {
			DbUtils.fechaStatement(st);
			DbUtils.closeConnection();
		}
	}
}
