package model.dao;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
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
import gui.util.Alerts;
import javafx.application.Platform;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.collection.Colecao;
import model.collection.entities.Caixa;
import model.collection.entities.Cliente;
import model.collection.entities.Funcionario;
import model.collection.entities.Transacao;
import model.services.IdentificadorSO;

public class DaoTransacao {
	static private PreparedStatement st = null;
	static private ResultSet rs = null;

	public static void salvarTransacao(TextField tfCliente, ChoiceBox<Funcionario> cbFuncionario, LocalDate dpData,
			TextField tfValor, ChoiceBox<String> cbFormaPagamento) {
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
				st = DB.getConnection().prepareStatement("INSERT INTO transacao "
						+ "(idcliente, cpffuncionario, formapagamento, data, valor) " + "VALUES " + "(?, ?, ?, ?, ?)");

				double valor = Double.parseDouble(tfValor.getText().replaceAll(",", "."));
				st.setInt(1, clienteId);
				st.setString(2, cbFuncionario.getValue().getCpf());
				st.setString(3, cbFormaPagamento.getValue());
				st.setDate(4, new java.sql.Date(data.getTime()));
				st.setDouble(5, valor);
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

	public static void carregaCaixa() {
		String linha = "";
		String caminho = System.getProperty("user.home") + File.separatorChar + "Documents" + File.separatorChar
				+ "JEF_DATA" + File.separatorChar + "caixa.csv";
		if (IdentificadorSO.sistema() == "linux") {
			caminho = System.getProperty("user.home") + File.separatorChar + "Documentos" + File.separatorChar
					+ "JEF_DATA" + File.separatorChar + "caixa.csv";
		}
		try (BufferedReader brCaixa = new BufferedReader(new FileReader(caminho));) {
			while ((linha = brCaixa.readLine()) != null) {
				String[] linhaCaixa = linha.split(";");
				Caixa.setStatus(Boolean.parseBoolean(linhaCaixa[0]));
			}
			brCaixa.close();
		} catch (FileNotFoundException e) {
			Alerts.showAlert("ERRO", "Algum problema aconteceu, contate o ADMINISTRADOR", e.getMessage(),
					AlertType.ERROR);
		} catch (IOException e) {
			Alerts.showAlert("ERRO", "Algum problema aconteceu, contate o ADMINISTRADOR", e.getMessage(),
					AlertType.ERROR);
		}
	}

	public static void salvarCaixa(LocalDate dpData, double fundoDeTroco) {
		try {
			SimpleDateFormat formataData = new SimpleDateFormat("yyyy-MM-dd");
			Date data = formataData.parse(dpData.plusDays(-1).toString());
			st = DB.getConnection().prepareStatement("select * from caixa where data = ?");
			st.setDate(1, new java.sql.Date(data.getTime()));
			rs = st.executeQuery();
			while(rs.next()){
				dpData.plusDays(1);
				data = formataData.parse(dpData.toString());
				st = DB.getConnection().prepareStatement("REPLACE INTO caixa "
						+ "(data, fundoDeTroco, montanteAnterior) " + "VALUES " + "(?, ?, ?)");
				st.setDate(1, new java.sql.Date(data.getTime()));
				st.setDouble(2, fundoDeTroco);
				st.setDouble(3, rs.getDouble("montanteTotal"));
				st.execute();
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
						formataData.format(rs.getDate("t.data")));
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

	public static void carregaTotalCaixa(Label totalCartao, Label totalDinheiro, Label valorTotalBruto, Label totalCaixa, LocalDate data) {
		boolean count = true;
		try {
			DateTimeFormatter localDateFormatadaProcura = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			st = DB.getConnection().prepareStatement("select * from caixa where data = ?");
			st.setString(1, localDateFormatadaProcura.format(data));
			rs = st.executeQuery();
			while(rs.next()) {
				totalCartao.setText(String.valueOf(String.format("%.2f", rs.getDouble("total_cartao"))));
				totalDinheiro.setText(String.valueOf(String.format("%.2f", rs.getDouble("total_dinheiro"))));
				valorTotalBruto.setText(String.valueOf(String.format("%.2f", rs.getDouble("totalDoDia"))));
				totalCaixa.setText(String.valueOf(String.format("%.2f", rs.getDouble("fundoDeTroco"))));
			}
		} catch (SQLException e) {
			Alerts.showAlert("ERRO", "Algum problema aconteceu, contate o ADMINISTRADOR", e.getMessage(),
					AlertType.ERROR);
		} finally {
			DB.fechaStatement(st);
			DB.closeConnection();
		}
	}

	public static void salvarStatus() {
		String caminho = System.getProperty("user.home") + File.separatorChar + "Documents" + File.separatorChar
				+ "JEF_DATA" + File.separatorChar + "caixa.csv";
		if (IdentificadorSO.sistema() == "linux") {
			caminho = System.getProperty("user.home") + File.separatorChar + "Documentos" + File.separatorChar
					+ "JEF_DATA" + File.separatorChar + "caixa.csv";
		}
		File arquivoCaixa = new File(caminho);
		try (BufferedWriter bwCaixa = new BufferedWriter(new FileWriter(arquivoCaixa))) {
			bwCaixa.write(Caixa.isStatus() + ";");
			bwCaixa.close();

		} catch (IOException e) {
			Alerts.showAlert("ERRO", "Algum problema aconteceu, contate o ADMINISTRADOR", e.getMessage(),
					AlertType.ERROR);
		}
	}
}
