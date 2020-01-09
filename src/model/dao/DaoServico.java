package model.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import db.DB;
import model.collection.Colecao;
import model.collection.entities.Categoria;
import model.collection.entities.Servico;

public class DaoServico {
	static private PreparedStatement st = null;
	static private ResultSet rs = null;
	
	public static void carregaServicos() {
		try {
			st = DB.getConnection().prepareStatement("select * from servicos s inner join categoria_de_servicos cs on (s.pacote = cs.id)");
			rs = st.executeQuery();
			while(rs.next()) {
				Servico servico = new Servico(rs.getInt("s.id"), rs.getString("s.nome"), rs.getDouble("s.preco"), rs.getString("cs.nome"));
				Colecao.servicos.add(servico);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			DB.fechaResultSet(rs);
			DB.fechaStatement(st);
			DB.closeConnection();
		}
	}
	
	public static void carregaCategoria() {
		try {
			st = DB.getConnection().prepareStatement("select * from categoria_de_servicos");
			rs = st.executeQuery();
			while(rs.next()) {
				Categoria catg = new Categoria(rs.getInt("id"), rs.getString("nome"));
				Colecao.categorias.add(catg);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			DB.fechaResultSet(rs);
			DB.fechaStatement(st);
			DB.closeConnection();
		}
	}
}
