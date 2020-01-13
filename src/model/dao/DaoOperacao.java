package model.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import db.DB;
import model.collection.Colecao;
import model.collection.entities.Operacao;

public class DaoOperacao {
	static private PreparedStatement st = null;
	static private ResultSet rs = null;

	public static void carregaOperacao(int data, int mes) {
		Colecao.operacoes.clear();
		try {
			if(mes == 0) {
				st = DB.getConnection().prepareStatement("select * from operacoes where year(data) = ?");
				st.setInt(1, data);
				rs = st.executeQuery();
			}
			else {
				st = DB.getConnection().prepareStatement("select * from operacoes where year(data) = ? and month(data) = ?");
				st.setInt(1, data);
				st.setInt(2, mes);
				rs = st.executeQuery();
			}
			SimpleDateFormat formataData = new SimpleDateFormat("dd/MM/yyyy");
			while(rs.next()) {
				Operacao op = new Operacao(rs.getInt("id"), rs.getString("descricao"), formataData.format(rs.getDate("data")), rs.getDouble("valor"), rs.getString("formaDePagamento"));
				Colecao.operacoes.add(op);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static ArrayList<Integer> carregaAno() {
		Colecao.operacoes.clear();
		ArrayList<Integer> anos = new ArrayList<>();
		try {
			st = DB.getConnection().prepareStatement("select distinct year(data) from operacoes order by data");
			rs = st.executeQuery();
			
			while(rs.next()) {
				anos.add(rs.getInt(1));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			DB.closeConnection();
			DB.fechaResultSet(rs);
			DB.fechaStatement(st);
		}
		return anos;
	}
}
