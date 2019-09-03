package model.services;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import db.DB;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import model.entities.Caixa;
import model.entities.Cliente;
import model.entities.Funcionario;

public class Salvar {
	static File arquivoTransacao;
	static PreparedStatement st = null;
	
	public static void salvarStatus() {
		String caminho = System.getProperty("user.home")+File.separatorChar+"Documents"+File.separatorChar+"teste"+ File.separatorChar+"caixa.csv";
		if (IdentificadorSO.sistema() == "linux"){
				caminho = System.getProperty("user.home")+File.separatorChar+"Documentos"+File.separatorChar+"teste"+ File.separatorChar+"caixa.csv";
		}
		File arquivoCaixa = new File(caminho);
		try(BufferedWriter bwCaixa = new BufferedWriter(new FileWriter(arquivoCaixa))) {
			bwCaixa.write(Caixa.isStatus() + ";");
			bwCaixa.close();
			
		} catch (IOException e) {
			System.out.println("Ocorreu um erro ao salvar o arquivo funcionario.csv: " + e.getMessage());
		}
	}
	
	public static void salvarFuncionario(TextField txtIdFuncionario, TextField txtNomeFuncionario, TextField txtSalarioFuncionario) {
		try {
			st = DB.getConnection().prepareStatement(
					"INSERT INTO funcionario "
					+ "(nome, id, salario) "
					+ "VALUES "
					+ "(?, ?, ?)");
			st.setInt(2, Integer.parseInt(txtIdFuncionario.getText()));
			st.setString(1, txtNomeFuncionario.getText());
			st.setString(3, txtSalarioFuncionario.getText());
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
	
	public static void salvarCliente(TextField txtCpfCliente, TextField txtNomeCliente, TextField txtEmailCliente, TextField txtTelefoneCliente) {
		try {
			st = DB.getConnection().prepareStatement(
					"INSERT INTO cliente "
					+ "(cpf, nome, email, telefone) "
					+ "VALUES "
					+ "(?, ?, ?, ? )");
			st.setString(1, txtCpfCliente.getText());
			st.setString(2, txtNomeCliente.getText());
			st.setString(3, txtEmailCliente.getText());
			st.setString(4, txtTelefoneCliente.getText());
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
	
	public static void salvarTransacao(TextField tfId, TextField cbCliente, ChoiceBox<Funcionario> cbFuncionario, LocalDate dpData, TextField tfValor, ChoiceBox<String> cbFormaPagamento) {
		try {
			DateTimeFormatter localDateFormatada = DateTimeFormatter.ofPattern("dd/MM/yyyy");
			st = DB.getConnection().prepareStatement(
					"INSERT INTO caixa "
					+ "(id, valor, cliente, atendente, forma_pagamento, data) "
					+ "VALUES "
					+ "(?, ?, ?, ?, ?, ? )");
			
			st.setInt(1, Integer.parseInt(tfId.getText()));
			st.setDouble(2, Double.parseDouble(tfValor.getText()));
			st.setString(3, cbCliente.getText());
			st.setString(4, cbFuncionario.getValue().getNome());
			st.setString(5, cbFormaPagamento.getValue());
			st.setString(6, localDateFormatada.format(dpData));
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
	
	public static void salvarCaixa(String data, double total, double cartao, double dinheiro) {
		try {
				st = DB.getConnection().prepareStatement(
						"REPLACE INTO fechamento_caixa "
						+ "(total, data, total_cartao, total_dinheiro) "
						+ "VALUES "
						+ "(?, ?, ?, ? )");
				st.setDouble(1, total);
				st.setString(2, data);
				st.setDouble(3, cartao);
				st.setDouble(4, dinheiro);
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
	
	public static void salvarAgendamento(String funcionario, String cliente, LocalDate data, String horario) {
		try {
			DateTimeFormatter localDateFormatada = DateTimeFormatter.ofPattern("dd/MM/yyyy");
				st = DB.getConnection().prepareStatement(
						"INSERT INTO agenda"
						+ "(funcionario, cliente, data, horario) "
						+ "VALUES "
						+ "(?, ?, ?, ? )");
				st.setString(1, funcionario);
				st.setString(2, cliente);
				st.setString(3,localDateFormatada.format(data));
				st.setString(4, horario);
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
	
	public static void salvar() {
		salvarStatus();
	}
}
