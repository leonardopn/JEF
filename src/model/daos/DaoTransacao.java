package model.daos;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import javax.swing.JOptionPane;

import dataBase.DbUtils;
import gui.controllers.ViewCaixaController;
import gui.utils.AlertsUtils;
import gui.utils.GeraLogUtils;
import javafx.application.Platform;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import model.collections.Colecao;
import model.collections.entities.Caixa;
import model.collections.entities.Cliente;
import model.collections.entities.Funcionario;
import model.collections.entities.Transacao;

public class DaoTransacao {
	private static PreparedStatement st = null;
	private static ResultSet rs = null;

	public static void salvarTransacao(TextField tfCliente, ChoiceBox<Funcionario> cbFuncionario, LocalDate dpData,
			TextField tfValor, ChoiceBox<String> cbFormaPagamento, String servico, String obs, int idPacote) {
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
				st = DbUtils.getConnection()
						.prepareStatement("INSERT INTO transacao "
								+ "(idcliente, cpffuncionario, formapagamento, data, valor, obs, servico, pacote) "
								+ "VALUES " + "(?, ?, ?, ?, ?, ?, ?, ?)");

				double valor = Double.parseDouble(tfValor.getText().replaceAll(",", "."));
				st.setInt(1, clienteId);
				st.setString(2, cbFuncionario.getValue().getCpf());
				st.setString(3, cbFormaPagamento.getValue());
				st.setDate(4, new java.sql.Date(data.getTime()));
				st.setDouble(5, valor);
				st.setString(6, obs);
				st.setString(7, servico);
				st.setInt(8, idPacote);
				GeraLogUtils.gravarLogQuery(st.toString());
				st.execute();
			}
		} catch (SQLException e) {
			AlertsUtils.showAlert("ERRO", "Algum problema aconteceu, contate o ADMINISTRADOR", e.getMessage(),
					AlertType.ERROR);
			GeraLogUtils.gravarLogQuery("ERRO" + e.getMessage());
		} catch (ParseException e) {
			AlertsUtils.showAlert("ERRO", "Algum problema aconteceu, contate o ADMINISTRADOR", e.getMessage(),
					AlertType.ERROR);
		} catch (NumberFormatException e) {
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					AlertsUtils.showAlert("ERRO", "Erro de conversão", tfValor.getText() + " Não é um número!",
							AlertType.ERROR);
				}
			});

		} finally {
			DbUtils.fechaStatement(st);
			DbUtils.closeConnection();
		}
	}

	public static boolean carregaCaixa() {
		try {
			SimpleDateFormat formataData = new SimpleDateFormat("yyyy-MM-dd");
			st = DbUtils.getConnection().prepareStatement("select * from caixa order by data");
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
			AlertsUtils.showAlert("ERRO", "Algum problema aconteceu, contate o ADMINISTRADOR", e.getMessage(),
					AlertType.ERROR);
			GeraLogUtils.gravarLogQuery("ERRO" + e.getMessage());
		}
		return false;
	}

	public static void abreFechaCaixa(LocalDate dpData, double fundoDeTroco, boolean status) {
		try {
			if (status) {
				SimpleDateFormat formataData = new SimpleDateFormat("yyyy-MM-dd");
				st = DbUtils.getConnection().prepareStatement("select * from caixa order by data");
				rs = st.executeQuery();
				rs.last();
				Date data = formataData.parse(dpData.toString());
				st = DbUtils.getConnection()
						.prepareStatement("REPLACE INTO caixa " + "(data, fundoDeTroco) " + "VALUES " + "(?, ?)");
				st.setDate(1, new java.sql.Date(data.getTime()));
				st.setDouble(2, fundoDeTroco);
				GeraLogUtils.gravarLogQuery(st.toString());
				st.execute();
			} else {
				SimpleDateFormat formataData = new SimpleDateFormat("yyyy-MM-dd");
				Date data = formataData.parse(dpData.toString());
				st = DbUtils.getConnection().prepareStatement("update caixa set status = 0 where data = ?;");
				st.setDate(1, new java.sql.Date(data.getTime()));
				GeraLogUtils.gravarLogQuery(st.toString());
				st.executeQuery();
			}
		} catch (SQLException | ParseException e) {
			AlertsUtils.showAlert("ERRO", "Algum problema aconteceu, contate o ADMINISTRADOR", e.getMessage(),
					AlertType.ERROR);
			GeraLogUtils.gravarLogQuery("ERRO" + e.getMessage());
		} finally {
			DbUtils.fechaStatement(st);
			DbUtils.closeConnection();
		}
	}

	public static void carregaTransacaoExpecifica(LocalDate data) {
		Caixa.caixa.clear();
		DateTimeFormatter localDateFormatadaProcura = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		try {
			st = DbUtils.getConnection()
					.prepareStatement("select * from transacao t "
							+ "inner join funcionario f on(t.cpffuncionario = f.cpffuncionario)"
							+ "inner join cliente c on(c.idcliente = t.idcliente)" + "where t.data = ?");
			st.setString(1, localDateFormatadaProcura.format(data));
			rs = st.executeQuery();

			while (rs.next()) {
				SimpleDateFormat formataData = new SimpleDateFormat("dd/MM/yyyy");
				Transacao tran = new Transacao(rs.getInt("t.id"), rs.getDouble("t.valor"), rs.getString("c.nome"),
						rs.getString("f.nome"), rs.getString("t.formapagamento"),
						formataData.format(rs.getDate("t.data")), rs.getString("obs"), rs.getString("servico"));
				Caixa.caixa.add(tran);
			}
		} catch (SQLException e) {
			AlertsUtils.showAlert("ERRO", "Algum problema aconteceu, contate o ADMINISTRADOR", e.getMessage(),
					AlertType.ERROR);
			GeraLogUtils.gravarLogQuery("ERRO" + e.getMessage());
		} finally {
			DbUtils.closeConnection();
			DbUtils.fechaResultSet(rs);
			DbUtils.fechaStatement(st);
		}
	}

	public static void excluirTransacao(Transacao tran) {
		try {
			st = DbUtils.getConnection().prepareStatement("DELETE FROM transacao " + "WHERE id= " + "(?)");
			st.setInt(1, tran.getId());
			GeraLogUtils.gravarLogQuery(st.toString());
			st.execute();
		} catch (SQLException e) {
			AlertsUtils.showAlert("ERRO", "Algum problema aconteceu, contate o ADMINISTRADOR", e.getMessage(),
					AlertType.ERROR);
			GeraLogUtils.gravarLogQuery("ERRO" + e.getMessage());
		} finally {
			DbUtils.fechaStatement(st);
			DbUtils.closeConnection();
		}
	}

	public static int carregaTotalCaixa(LocalDate data) {
		try {

			DateTimeFormatter localDateFormatadaProcura = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			st = DbUtils.getConnection().prepareStatement("select * from caixa where data = ?");
			st.setString(1, localDateFormatadaProcura.format(data));
			rs = st.executeQuery();
			if (rs.next()) {
				ViewCaixaController.setValorCartao(rs.getDouble("total_cartao"));
				ViewCaixaController.setValorDinheiro(rs.getDouble("total_dinheiro"));
				ViewCaixaController.setValorTotal(rs.getDouble("totalDoDia"));
				ViewCaixaController.setFundoDeTroco(rs.getDouble("fundoDeTroco"));
				return rs.getInt("status");
			}
		} catch (SQLException e) {
			AlertsUtils.showAlert("ERRO", "Algum problema aconteceu, contate o ADMINISTRADOR", e.getMessage(),
					AlertType.ERROR);
			GeraLogUtils.gravarLogQuery("ERRO" + e.getMessage());
		} finally {
			DbUtils.fechaStatement(st);
			DbUtils.closeConnection();
		}
		return 1;
	}

	public static void salvaMontante(double valorDinheiro, double valorCartao, double valorTotal, double fundoDeTroco,
			LocalDate data) {
		try {

			DateTimeFormatter localDateFormatadaProcura = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			st = DbUtils.getConnection().prepareStatement(
					"update caixa set totalDoDia = ?, total_dinheiro = ?, total_cartao = ?, fundoDeTroco = ? where data = ?;");
			st.setDouble(1, valorTotal);
			st.setDouble(2, valorDinheiro);
			st.setDouble(3, valorCartao);
			st.setDouble(4, fundoDeTroco);
			st.setString(5, localDateFormatadaProcura.format(data));
			GeraLogUtils.gravarLogQuery(st.toString());
			st.execute();

		} catch (SQLException e) {
			AlertsUtils.showAlert("ERRO", "Algum problema aconteceu, contate o ADMINISTRADOR", e.getMessage(),
					AlertType.ERROR);
			GeraLogUtils.gravarLogQuery("ERRO" + e.getMessage());
		} finally {
			DbUtils.fechaStatement(st);
			DbUtils.closeConnection();
		}
	}
}
