package model.services;

import java.io.File;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import db.DB;
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
}
