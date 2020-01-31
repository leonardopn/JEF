package model.daos;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;

import dataBase.DbUtils;
import gui.utils.AlertsUtils;
import javafx.application.Platform;
import javafx.scene.control.Alert.AlertType;
import model.collections.Colecao;
import model.collections.entities.Operacao;

public class DaoOperacao {
	private static PreparedStatement st = null;
	private static ResultSet rs = null;

	public static void carregaOperacao(int data, int mes) {
		Colecao.operacoes.clear();
		try {
			if (mes == 0) {
				st = DbUtils.getConnection().prepareStatement("select * from operacoes where year(data) = ?");
				st.setInt(1, data);
				rs = st.executeQuery();
			} else {
				st = DbUtils.getConnection()
						.prepareStatement("select * from operacoes where year(data) = ? and month(data) = ?");
				st.setInt(1, data);
				st.setInt(2, mes);
				rs = st.executeQuery();
			}
			SimpleDateFormat formataData = new SimpleDateFormat("dd/MM/yyyy");
			while (rs.next()) {
				Operacao op = new Operacao(rs.getInt("id"), rs.getString("descricao"),
						formataData.format(rs.getDate("data")), rs.getDouble("valor"),
						rs.getString("formaDePagamento"));
				Colecao.operacoes.add(op);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DbUtils.closeConnection();
			DbUtils.fechaResultSet(rs);
			DbUtils.fechaStatement(st);
		}
	}

	public static ArrayList<Integer> carregaAno() {
		Colecao.operacoes.clear();
		ArrayList<Integer> anos = new ArrayList<>();
		try {
			st = DbUtils.getConnection().prepareStatement("select distinct year(data) from operacoes order by data");
			rs = st.executeQuery();

			while (rs.next()) {
				anos.add(rs.getInt(1));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DbUtils.closeConnection();
			DbUtils.fechaResultSet(rs);
			DbUtils.fechaStatement(st);
		}
		return anos;
	}

	public static ArrayList<Operacao> carregaBusca(String busca, int opcao1, int opcao2, int mes, int mes2, int ano,
			int dia) {
		ArrayList<Operacao> opTemp = new ArrayList<>();
		try {
			String query;
			String filtro;
			if (opcao2 == 1) {
				filtro = "";
			} else {
				if (opcao2 == 2) {
					filtro = "< 0";
				} else {
					filtro = "> 0";
				}
			}

			if (dia == 0) {
				if (opcao1 == 1) {
					query = "select * from operacoes where descricao like ? and valor " + filtro
							+ " and year(data) = ? and MONTH(data) BETWEEN (?) and (?);";
					st = DbUtils.getConnection().prepareStatement(query);
					st.setString(1, "%" + busca + "%");
					st.setInt(2, ano);
					st.setInt(3, mes);
					st.setInt(4, mes2);
				} else {
					if (opcao1 == 2) {
						query = "select * from operacoes where id = ?";
						st = DbUtils.getConnection().prepareStatement(query);
						st.setString(1, busca);
					} else {
						if (opcao1 == 3) {
							query = "select * from operacoes where formaDePagamento like ? and valor " + filtro
									+ " and year(data) = ? and MONTH(data) BETWEEN (?) and (?);";
							st = DbUtils.getConnection().prepareStatement(query);
							st.setString(1, "%" + busca + "%");
							st.setInt(2, ano);
							st.setInt(3, mes);
							st.setInt(4, mes2);
						} else {
							query = "select * from operacoes where valor " + filtro
									+ " and year(data) = ? and MONTH(data) BETWEEN (?) and (?);";
							st = DbUtils.getConnection().prepareStatement(query);
							st.setInt(1, ano);
							st.setInt(2, mes);
							st.setInt(3, mes2);
						}
					}
				}
			} else {
				if (opcao1 == 1) {
					query = "select * from operacoes where descricao like ? and valor " + filtro
							+ " and year(data) = ? and MONTH(data) BETWEEN (?) and (?) and day(data) = ?;";
					st = DbUtils.getConnection().prepareStatement(query);
					st.setString(1, "%" + busca + "%");
					st.setInt(2, ano);
					st.setInt(3, mes);
					st.setInt(4, mes2);
					st.setInt(5, dia);
				} else {
					if (opcao1 == 2) {
						query = "select * from operacoes where id = ?";
						st = DbUtils.getConnection().prepareStatement(query);
						st.setString(1, busca);
					} else {
						if (opcao1 == 3) {
							query = "select * from operacoes where formaDePagamento like ? and valor " + filtro
									+ " and year(data) = ? and MONTH(data) BETWEEN (?) and (?) and day(data) = ?;";
							st = DbUtils.getConnection().prepareStatement(query);
							st.setString(1, "%" + busca + "%");
							st.setInt(2, ano);
							st.setInt(3, mes);
							st.setInt(4, mes2);
							st.setInt(5, dia);
						} else {
							query = "select * from operacoes where valor " + filtro
									+ " and year(data) = ? and MONTH(data) BETWEEN (?) and (?) and day(data) = ?;";
							st = DbUtils.getConnection().prepareStatement(query);
							st.setInt(1, ano);
							st.setInt(2, mes);
							st.setInt(3, mes2);
							st.setInt(4, dia);
						}
					}
				}
			}

			rs = st.executeQuery();

			SimpleDateFormat formataData = new SimpleDateFormat("dd/MM/yyyy");

			while (rs.next()) {
				Operacao op = new Operacao(rs.getInt("id"), rs.getString("descricao"),
						formataData.format(rs.getDate("data")), rs.getDouble("valor"),
						rs.getString("formaDePagamento"));
				opTemp.add(op);
			}
			return opTemp;

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DbUtils.closeConnection();
			DbUtils.fechaResultSet(rs);
			DbUtils.fechaStatement(st);
		}
		return opTemp;
	}

	public static void excluiOperacao(int id, double valor, LocalDate data, String formaPagamento) {
		try {
			st = DbUtils.getConnection().prepareStatement("delete from operacoes where id = ?");
			st.setInt(1, id);
			st.execute();
			if (formaPagamento.equals("Dinheiro")) {
				DateTimeFormatter localDateFormatadaProcura = DateTimeFormatter.ofPattern("yyyy-MM-dd");
				st = DbUtils.getConnection()
						.prepareStatement("update caixa set fundoDeTroco = fundoDeTroco + ? where data = ?;");
				st.setDouble(1, valor);
				st.setString(2, localDateFormatadaProcura.format(data));
				st.execute();
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DbUtils.closeConnection();
			DbUtils.fechaStatement(st);
		}
	}

	public static void salvaOperacao(String descricao, LocalDate data, String valor, String formaPagamento) {
		try {
			SimpleDateFormat formataData = new SimpleDateFormat("yyyy-MM-dd");
			Date date = formataData.parse(data.toString());
			st = DbUtils.getConnection().prepareStatement(
					"INSERT INTO operacoes(data, descricao, valor, formaDePagamento) values (?, ?, ?, ?)");
			st.setDate(1, new java.sql.Date(date.getTime()));
			st.setString(2, descricao);
			st.setDouble(3, Double.parseDouble(valor.replaceAll(",", ".")));
			st.setString(4, formaPagamento);
			st.execute();

			if ("Dinheiro".equals(formaPagamento)) {
				st = DbUtils.getConnection()
						.prepareStatement("update caixa set fundoDeTroco = fundoDeTroco + ? where data = ?;");
				st.setDate(2, new java.sql.Date(date.getTime()));
				st.setDouble(1, Double.parseDouble(valor.replaceAll(",", ".")));
				st.execute();
			}

			DaoOperacao.atualizaMontante(valor);

		} catch (SQLException e) {
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					AlertsUtils.showAlert("ERRO", "Erro durante a conexão com o banco!", e.getMessage(),
							AlertType.ERROR);
				}
			});
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (NumberFormatException e) {
			Platform.runLater(new Runnable() {

				@Override
				public void run() {
					AlertsUtils.showAlert("ERRO", "Erro de conversão", "O valor digitado não é um número!",
							AlertType.ERROR);
				}

			});
		} finally {
			DbUtils.closeConnection();
			DbUtils.fechaStatement(st);
		}
	}

	public static void atualizaMontante(String valor) {
		try {
			st = DbUtils.getConnection().prepareStatement("UPDATE contas set montante = montante + ?;");
			st.setString(1, valor);
			st.execute();
		} catch (SQLException e) {
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					AlertsUtils.showAlert("ERRO", "Erro durante a conexão com o banco!", e.getMessage(),
							AlertType.ERROR);
				}
			});
		} catch (NumberFormatException e) {
			Platform.runLater(new Runnable() {

				@Override
				public void run() {
					AlertsUtils.showAlert("ERRO", "Erro de conversão", "O valor digitado não é um número!",
							AlertType.ERROR);
				}
			});
		} finally {
			DbUtils.closeConnection();
			DbUtils.fechaStatement(st);
		}
	}
}
