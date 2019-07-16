package model.services;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;

import db.DB;
import javafx.scene.control.TextField;
import model.entities.Caixa;
import model.entities.Cliente;
import model.entities.Funcionario;
import model.entities.Transacao;

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
	
	public static void excluirFuncionario(Funcionario fun) {
		try {
			st = DB.getConnection().prepareStatement(
					"DELETE FROM funcionario "
					+ "WHERE id= "
					+ "(?)");
			st.setInt(1, fun.getId()); 
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
	
	public static void salvarCliente(TextField txtIdCliente, TextField txtNomeCliente, TextField txtEmailCliente, TextField txtTelefoneCliente) {
		try {
			st = DB.getConnection().prepareStatement(
					"INSERT INTO cliente "
					+ "(id, nome, email, telefone) "
					+ "VALUES "
					+ "(?, ?, ?, ? )");
			st.setInt(1, Integer.parseInt(txtIdCliente.getText()));
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
	
	public static void excluirCliente(Cliente cli) {
		try {
			st = DB.getConnection().prepareStatement(
					"DELETE FROM cliente "
					+ "WHERE id= "
					+ "(?)");
			st.setInt(1, cli.getId()); 
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
	
	public static void salvarTransacao() {
		for(Transacao tran : Caixa.caixa) {
			String caminho = System.getProperty("user.home")+File.separatorChar+"Documents"+File.separatorChar+"teste"+ File.separatorChar+"transacoes"+ File.separatorChar + "transacoes.csv";
			if (IdentificadorSO.sistema() == "linux"){
				caminho = System.getProperty("user.home")+File.separatorChar+"Documentos"+File.separatorChar+"teste"+ File.separatorChar+"transacoes"+ File.separatorChar + "transacoes.csv";
			}
			arquivoTransacao = new File(caminho);
			
			try(BufferedWriter bwTransacao = new BufferedWriter(new FileWriter(arquivoTransacao))) {
				for(Transacao tran2 : Caixa.caixa) {
					bwTransacao.write(tran2.getId() + ";" + 
						      tran2.getValor()+ ";" + 
						      String.valueOf(tran2.getData()) + ";" + 
						      tran2.getCliente() + ";" +
						      tran2.getAtendente() + ";" +
						      tran2.getFormaPagamento()+ "\n");
				}			 
			}	
			catch(IOException e) {
				System.out.println("Ocorreu um erro ao salvar um arquivo de transação: " + e.getMessage());
			}                  
		}
		for(Transacao tran : Caixa.caixa) {
			String caminho = System.getProperty("user.home")+File.separatorChar+"Documents"+File.separatorChar+"teste"+ File.separatorChar+"transacoes"+ File.separatorChar+ tran.getData()+".csv";
			if (IdentificadorSO.sistema() == "linux"){
				caminho = System.getProperty("user.home")+File.separatorChar+"Documentos"+File.separatorChar+"teste"+ File.separatorChar+"transacoes"+ File.separatorChar+ tran.getData()+".csv";
			}
			arquivoTransacao = new File(caminho);
			
			try(BufferedWriter bwTransacao = new BufferedWriter(new FileWriter(arquivoTransacao))) {
				for(Transacao tran2 : Caixa.caixa) {
					if(tran2.getData().equals(tran.getData())) {
						bwTransacao.write(tran2.getId() + ";" + 
									      tran2.getValor()+ ";" + 
									      String.valueOf(tran2.getData()) + ";" + 
									      tran2.getCliente() + ";" +
									      tran2.getAtendente() + ";" +
									      tran2.getFormaPagamento()+ "\n");
						 
					}
				}
			}	
			catch(IOException e) {
				System.out.println("Ocorreu um erro ao salvar um arquivo de transação: " + e.getMessage());
			}                  
		}
	}
	
	public static void salvarTransacaoExcluidos(LocalDate data) {
		File arquivoTransacao;

		String caminho = System.getProperty("user.home") + File.separatorChar + "Documents" + File.separatorChar
				+ "teste" + File.separatorChar + "transacoes" + File.separatorChar + "transacoes.csv";
		if (IdentificadorSO.sistema() == "linux") {
			caminho = System.getProperty("user.home") + File.separatorChar + "Documentos" + File.separatorChar + "teste"
					+ File.separatorChar + "transacoes" + File.separatorChar + "transacoes.csv";
		}
		arquivoTransacao = new File(caminho);

		try (BufferedWriter bwTransacao = new BufferedWriter(new FileWriter(arquivoTransacao))) {
			for (Transacao tran2 : Caixa.caixa) {
				bwTransacao.write(tran2.getId() + ";" + tran2.getValor() + ";" + String.valueOf(tran2.getData()) + ";"
						+ tran2.getCliente() + ";" + tran2.getAtendente() + ";" + tran2.getFormaPagamento() + "\n");
			}
		} catch (IOException e) {
			System.out.println("Ocorreu um erro ao salvar um arquivo de transação: " + e.getMessage());
		}

		caminho = System.getProperty("user.home") + File.separatorChar + "Documents" + File.separatorChar + "teste"
				+ File.separatorChar + "transacoes" + File.separatorChar + data + ".csv";
		if (IdentificadorSO.sistema() == "linux") {
			caminho = System.getProperty("user.home") + File.separatorChar + "Documentos" + File.separatorChar + "teste"
					+ File.separatorChar + "transacoes" + File.separatorChar + data + ".csv";
		}
		arquivoTransacao = new File(caminho);
		if (arquivoTransacao.exists()) {
			arquivoTransacao.delete();
		}
		try (BufferedWriter bwTransacao = new BufferedWriter(new FileWriter(arquivoTransacao))) {
			for (Transacao tran : Caixa.caixaTemp) {
				bwTransacao.write(tran.getId() + ";" + tran.getValor() + ";" + String.valueOf(tran.getData()) + ";"
						+ tran.getCliente() + ";" + tran.getAtendente() + ";" + tran.getFormaPagamento() + "\n");

			}
		} catch (IOException e) {
			System.out.println("Ocorreu um erro ao salvar um arquivo de transação: " + e.getMessage());
		}
	}
	
	public static void salvar() {
		salvarTransacao();
		salvarStatus();
	}
	
	
}
