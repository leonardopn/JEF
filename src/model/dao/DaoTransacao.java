package model.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import javax.swing.JOptionPane;

import db.DB;
import gui.controllers.ViewCaixaController;
import gui.util.Alerts;
import javafx.application.Platform;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import model.collection.Colecao;
import model.collection.entities.Caixa;
import model.collection.entities.Cliente;
import model.collection.entities.Funcionario;
import model.collection.entities.Transacao;

public class DaoTransacao {
	static private PreparedStatement st = null;
	static private ResultSet rs = null;

	public static void salvarTransacao(TextField tfCliente, ChoiceBox<Funcionario> cbFuncionario, LocalDate dpData,
			TextField tfValor, ChoiceBox<String> cbFormaPagamento, String servico, String obs) {
		try {
			int clienteId = 0;
			SimpleDateFormat formataData = new SimpleDateFormat("yyyy-MM-dd");
			Date data = formataData.parse(dpData.toString());
			for (Cliente cliente : Colecao.clientes) {
				if (tfCliente.getText().equals(cliente.getNome())) {
					clienteId = cliente.getId();
					tfCliente.setText("");
				}
			}

			if (!(tfCliente.getText().isEmpty())) {
				JOptionPane.showMessageDialog(null,
						"Primeiro crie um novo cliente com todos os dados"
								+ " ou então crie um cliente com pelo menos o dado: NOME",
						"Cliente não encontrado", JOptionPane.ERROR_MESSAGE);
				tfCliente.setText("");
			} else {
				st = DB.getConnection()
						.prepareStatement("INSERT INTO transacao "
								+ "(idcliente, cpffuncionario, formapagamento, data, valor, obs, servico) " + "VALUES "
								+ "(?, ?, ?, ?, ?, ?, ?)");

				double valor = Double.parseDouble(tfValor.getText().replaceAll(",", "."));
				st.setInt(1, clienteId);
				st.setString(2, cbFuncionario.getValue().getCpf());
				st.setString(3, cbFormaPagamento.getValue());
				st.setDate(4, new java.sql.Date(data.getTime()));
				st.setDouble(5, valor);
				st.setString(6, obs);
				st.setString(7, servico);
				st.execute();
			}
		} catch (SQLException e) {
			Alerts.showAlert("ERRO", "Algum problema aconteceu, contate o ADMINISTRADOR", e.getMessage(),
					AlertType.ERROR);
		} catch (ParseException e) {
			Alerts.showAlert("ERRO", "Algum problema aconteceu, contate o ADMINISTRADOR", e.getMessage(),
					AlertType.ERROR);
		} catch (NumberFormatException e) {
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					Alerts.showAlert("ERRO", "Erro de conversão", tfValor.getText() + " Não é um número!",
							AlertType.ERROR);
				}
			});

		} finally {
			DB.fechaStatement(st);
			DB.closeConnection();
		}
	}

	public static boolean carregaCaixa() {
		try {
			SimpleDateFormat formataData = new SimpleDateFormat("yyyy-MM-dd");
			st = DB.getConnection().prepareStatement("select * from caixa order by data");
			rs = st.executeQuery();
			rs.last();
			int i = rs.getInt("status");
			String data = formataData.format(rs.getDate("data"));
			if (i == 0) {
				if (data.equals(LocalDate.now().toString())) {
					ViewCaixaController.setStatusCaixa(0);
					return false;
				} else {
					ViewCaixaController.setStatusCaixa(1);
					return false;
				}
			} else {
				if (data.equals(LocalDate.now().toString())) {
					return true;
				} else {
					LocalDate date = LocalDate.parse(data);
					abreFechaCaixa(date, 0.0, false);
					ViewCaixaController.setStatusCaixa(1);
					return false;
				}
			}
		} catch (SQLException e) {
			Alerts.showAlert("ERRO", "Algum problema aconteceu, contate o ADMINISTRADOR", e.getMessage(),
					AlertType.ERROR);
		}
		return false;
	}

	public static void abreFechaCaixa(LocalDate dpData, double fundoDeTroco, boolean status) {
		try {
			if (status == true) {
				SimpleDateFormat formataData = new SimpleDateFormat("yyyy-MM-dd");
				st = DB.getConnection().prepareStatement("select * from caixa order by data");
				rs = st.executeQuery();
				rs.last();
				Date data = formataData.parse(dpData.toString());
				st = DB.getConnection().prepareStatement("REPLACE INTO caixa "
						+ "(data, fundoDeTroco, montanteAnterior, montanteTotal) " + "VALUES " + "(?, ?, ?, ?)");
				st.setDate(1, new java.sql.Date(data.getTime()));
				st.setDouble(2, fundoDeTroco);
				st.setDouble(3, rs.getDouble("montanteTotal"));
				st.setDouble(4, rs.getDouble("montanteTotal"));
				st.execute();
			} else {
				SimpleDateFormat formataData = new SimpleDateFormat("yyyy-MM-dd");
				Date data = formataData.parse(dpData.toString());
				st = DB.getConnection().prepareStatement("update caixa set status = 0 where data = ?;");
				st.setDate(1, new java.sql.Date(data.getTime()));
				st.executeQuery();
			}
		} catch (SQLException | ParseException e) {
			Alerts.showAlert("ERRO", "Algum problema aconteceu, contate o ADMINISTRADOR", e.getMessage(),
					AlertType.ERROR);
		} finally {
			DB.fechaStatement(st);
			DB.closeConnection();
		}
	}

	public static void carregaTransacaoExpecifica(LocalDate data) {
		Caixa.caixa.clear();
		DateTimeFormatter localDateFormatadaProcura = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		try {
			st = DB.getConnection()
					.prepareStatement("select * from transacao t "
							+ "inner join funcionario f on(t.cpffuncionario = f.cpffuncionario)"
							+ "inner join cliente c on(c.idcliente = t.idcliente)" + "where t.data = ?");
			st.setString(1, localDateFormatadaProcura.format(data));
			rs = st.executeQuery();

			while (rs.next()) {
				SimpleDateFormat formataData = new SimpleDateFormat("dd/MM/yyyy");
				Transacao tran = new Transacao(rs.getInt("t.id"), rs.getDouble("t.valor"), rs.getString("c.nome"),
						rs.getString("f.nome"), rs.getString("t.formapagamento"),
						formataData.format(rs.getDate("t.data")), rs.getString("obs"), rs.getString("servico") );
				Caixa.caixa.add(tran);
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

	public static void excluirTransacao(Transacao tran) {
		try {
			st = DB.getConnection().prepareStatement("DELETE FROM transacao " + "WHERE id= " + "(?)");
			st.setInt(1, tran.getId());
			st.execute();
		} catch (SQLException e) {
			Alerts.showAlert("ERRO", "Algum problema aconteceu, contate o ADMINISTRADOR", e.getMessage(),
					AlertType.ERROR);
		} finally {
			DB.fechaStatement(st);
			DB.closeConnection();
		}
	}

	public static int carregaTotalCaixa(LocalDate data) {
		try {

			DateTimeFormatter localDateFormatadaProcura = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			st = DB.getConnection().prepareStatement("select * from caixa where data = ?");
			st.setString(1, localDateFormatadaProcura.format(data));
			rs = st.executeQuery();
			while (rs.next()) {
				ViewCaixaController.setValorCartao(rs.getDouble("total_cartao"));
				ViewCaixaController.setValorDinheiro(rs.getDouble("total_dinheiro"));
				ViewCaixaController.setValorTotal(rs.getDouble("totalDoDia"));
				ViewCaixaController.setFundoDeTroco(rs.getDouble("fundoDeTroco"));
				return rs.getInt("status");
			}
		} catch (SQLException e) {
			Alerts.showAlert("ERRO", "Algum problema aconteceu, contate o ADMINISTRADOR", e.getMessage(),
					AlertType.ERROR);
		} finally {
			DB.fechaStatement(st);
			DB.closeConnection();
		}
		return 1;
	}
}
