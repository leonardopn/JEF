package model.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import db.DB;
import gui.util.Alerts;
import javafx.application.Platform;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import model.collection.Colecao;
import model.collection.entities.Cliente;
import model.collection.entities.Pacote;
import model.collection.entities.PacoteAssociado;

public class DaoPacote {
	static private PreparedStatement st = null;
	static private ResultSet rs = null;

	public static void salvarPacote(TextField tfNome, TextField tfValor, TextField tfValorMao, TextField tfQuantMao,
			TextField tfQuantPe, TextField tfValorPe) {
		try {
			st = DB.getConnection().prepareStatement("insert into pacotes"
					+ "(nome, valor, quant_mao, quant_pe, unit_mao, unit_pe) " + "values (?, ?, ?, ?, ?, ?)");
			st.setString(1, tfNome.getText());
			st.setDouble(2, Double.parseDouble(tfValor.getText().replaceAll(",", ".")));
			st.setInt(3, Integer.parseInt(tfQuantMao.getText().replaceAll(",", ".")));
			st.setInt(4, Integer.parseInt(tfQuantPe.getText().replaceAll(",", ".")));
			st.setDouble(5, Double.parseDouble(tfValorMao.getText().replaceAll(",", ".")));
			st.setDouble(6, Double.parseDouble(tfValorPe.getText().replaceAll(",", ".")));
			st.executeQuery();
			carregaPacote();

		} catch (SQLException e) {
			Alerts.showAlert("ERRO", "Algum problema aconteceu, contate o ADMINISTRADOR", e.getMessage(),
					AlertType.ERROR);
		} catch (NumberFormatException e) {
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					Alerts.showAlert("ERRO", "Erro de conversão ", e.getMessage(), AlertType.ERROR);
				}
			});
		} finally {
			DB.closeConnection();
			DB.fechaStatement(st);
		}
	}

	public static void carregaPacote() {
		Colecao.pacotes.clear();
		try {
			st = DB.getConnection().prepareStatement("select * from pacotes");
			rs = st.executeQuery();

			while (rs.next()) {
				if (rs.getInt("status") == 1) {
					Pacote pacote = new Pacote(rs.getInt("id"), rs.getString("nome"), rs.getDouble("valor"),
							rs.getInt("quant_pe"), rs.getInt("quant_mao"), rs.getDouble("unit_mao"),
							rs.getDouble("unit_pe"));
					Colecao.pacotes.add(pacote);
				}
			}
		} catch (SQLException e) {
			Alerts.showAlert("ERRO", "Algum problema aconteceu, contate o ADMINISTRADOR", e.getMessage(),
					AlertType.ERROR);
		} finally {
			DB.closeConnection();
			DB.fechaResultSet(rs);
			DB.fechaStatement(st);
		}
	}

	public static void excluiPacote(Pacote pacote) {
		try {
			st = DB.getConnection().prepareStatement("update pacotes set status = 0 where id = ?");
			st.setInt(1, pacote.getId());
			st.executeQuery();
			carregaPacote();
		} catch (SQLException e) {
			Alerts.showAlert("ERRO", "Algum problema aconteceu, contate o ADMINISTRADOR", e.getMessage(),
					AlertType.ERROR);
		} finally {
			DB.closeConnection();
			DB.fechaStatement(st);
		}
	}

	public static void atualizaPacote(TextField tfNome, TextField tfValor, TextField tfValorMao, TextField tfQuantMao,
			TextField tfQuantPe, TextField tfValorPe, TextField tfIdPacote) {
		try {
			st = DB.getConnection().prepareStatement("update pacotes set "
					+ "nome = ?, valor = ?, quant_mao = ?, quant_pe = ?, unit_mao = ?, unit_pe = ? " + "where id = ?");
			st.setString(1, tfNome.getText());
			st.setDouble(2, Double.parseDouble(tfValor.getText().replaceAll(",", ".")));
			st.setInt(3, Integer.parseInt(tfQuantMao.getText()));
			st.setInt(4, Integer.parseInt(tfQuantPe.getText()));
			st.setDouble(5, Double.parseDouble(tfValorMao.getText().replaceAll(",", ".")));
			st.setDouble(6, Double.parseDouble(tfValorPe.getText().replaceAll(",", ".")));
			st.setInt(7, Integer.parseInt(tfIdPacote.getText()));
			st.executeQuery();
			carregaPacote();
		} catch (SQLException e) {
			Alerts.showAlert("ERRO", "Algum problema aconteceu, contate o ADMINISTRADOR", e.getMessage(),
					AlertType.ERROR);
		} catch (NumberFormatException e) {
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					Alerts.showAlert("ERRO", "Erro de conversão ", e.getMessage(), AlertType.ERROR);
				}
			});
		} finally {
			DB.closeConnection();
			DB.fechaStatement(st);
		}
	}

	public static void carregaPacoteAssociado() {
		Colecao.pacoteAssociados.clear();
		try {
			st = DB.getConnection().prepareStatement("select * from pacotes_associados pa inner join cliente c on (c.idcliente = pa.idcliente) inner join pacotes p on (p.id = pa.idpacote)");
			rs = st.executeQuery();
			while(rs.next()) {
				PacoteAssociado pacoteAssociado = new PacoteAssociado(rs.getInt("pa.id"), rs.getString("c.nome"), rs.getString("p.nome"), rs.getInt("pa.quant_mao"), rs.getInt("pa.quant_pe"));
				Colecao.pacoteAssociados.add(pacoteAssociado);
			}
		} catch (SQLException e) {
			Alerts.showAlert("ERRO", "Algum problema aconteceu, contate o ADMINISTRADOR", e.getMessage(),
					AlertType.ERROR);
		} finally {
			DB.closeConnection();
			DB.fechaStatement(st);
			DB.fechaResultSet(rs);
		}
	}

	public static void salvarPacoteAssociado(TextField tfCliente, ChoiceBox<Pacote> cbPacote) {
		try {
			int idCliente = -1;
			st = DB.getConnection()
					.prepareStatement("insert into pacotes_associados " + "(idcliente, idpacote) " + "values (?, ?)");
			for (Cliente cliente : Colecao.clientes) {
				if (cliente.getNome().contentEquals(tfCliente.getText())) {
					idCliente = cliente.getId();
					st.setInt(1, idCliente);
					st.setInt(2, cbPacote.getValue().getId());
					st.executeQuery();
					carregaPacoteAssociado();
				}
			}

			if (idCliente == -1) {
				Platform.runLater(new Runnable() {
					@Override
					public void run() {
						Alerts.showAlert("AVISO", "Nenhum cliente encontrado para associar! ", "", AlertType.ERROR);
					}
				});
			}

		} catch (SQLException e) {
			Alerts.showAlert("ERRO", "Algum problema aconteceu, contate o ADMINISTRADOR", e.getMessage(),
					AlertType.ERROR);
		} finally {
			DB.closeConnection();
			DB.fechaStatement(st);
		}
	}
}
