package model.daos;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import dataBase.DbUtils;
import model.collections.Colecao;
import model.collections.entities.Categoria;
import model.collections.entities.Servico;

public class DaoServico {
	private static PreparedStatement st = null;
	private static ResultSet rs = null;

	public static void carregaServicos() {
		try {
			st = DbUtils.getConnection().prepareStatement(
					"select * from servicos s inner join categoria_de_servicos cs on (s.pacote = cs.id)");
			rs = st.executeQuery();
			while (rs.next()) {
				Servico servico = new Servico(rs.getInt("s.id"), rs.getString("s.nome"), rs.getDouble("s.preco"),
						rs.getString("cs.nome"));
				Colecao.servicos.add(servico);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DbUtils.fechaResultSet(rs);
			DbUtils.fechaStatement(st);
			DbUtils.closeConnection();
		}
	}

	public static void carregaCategoria() {
		try {
			st = DbUtils.getConnection().prepareStatement("select * from categoria_de_servicos");
			rs = st.executeQuery();
			while (rs.next()) {
				Categoria catg = new Categoria(rs.getInt("id"), rs.getString("nome"));
				Colecao.categorias.add(catg);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DbUtils.fechaResultSet(rs);
			DbUtils.fechaStatement(st);
			DbUtils.closeConnection();
		}
	}
}
