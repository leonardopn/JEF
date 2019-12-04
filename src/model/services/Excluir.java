package model.services;

import java.io.File;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

import db.DB;
import model.entities.Agendamento;
import model.entities.Transacao;

public class Excluir {
	static File arquivoTransacao;
	static PreparedStatement st = null;
	
	public static void excluirTransacao(Transacao tran) {
		try {
			st = DB.getConnection().prepareStatement(
					"DELETE FROM transacao "
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
