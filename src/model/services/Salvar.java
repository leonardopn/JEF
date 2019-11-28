package model.services;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

import javax.swing.JOptionPane;

import db.DB;
import gui.controllers.ViewController;
import gui.util.Alerts;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import model.entities.Caixa;
import model.entities.Cliente;
import model.entities.Funcionario;

public class Salvar{
	static File arquivoTransacao;
	static PreparedStatement st = null;
	static ResultSet rs = null;
	
	public static void salvarStatus() {
		String caminho = System.getProperty("user.home")+File.separatorChar+"Documents"+File.separatorChar+"JEF_DATA"+ File.separatorChar+"caixa.csv";
		if (IdentificadorSO.sistema() == "linux"){
				caminho = System.getProperty("user.home")+File.separatorChar+"Documentos"+File.separatorChar+"JEF_DATA"+ File.separatorChar+"caixa.csv";
		}
		File arquivoCaixa = new File(caminho);
		try(BufferedWriter bwCaixa = new BufferedWriter(new FileWriter(arquivoCaixa))) {
			bwCaixa.write(Caixa.isStatus() + ";");
			bwCaixa.close();
			
		} catch (IOException e) {
			System.out.println("Ocorreu um erro ao salvar o arquivo funcionario.csv: " + e.getMessage());
		}
	}
	
	public static boolean salvarFuncionario(TextField txtCpfFuncionario, TextField txtTelefoneFuncionario, TextField txtNomeFuncionario) {
		boolean count = true;
		try {
			st = DB.getConnection().prepareStatement("select * from funcionario where cpffuncionario = ?");
			st.setString(1, txtCpfFuncionario.getText());
			rs = st.executeQuery();
			count = rs.next();
			
			if(count == false) {
				st = DB.getConnection().prepareStatement(
						"INSERT INTO funcionario "
						+ "(cpffuncionario, nome, telefone) "
						+ "VALUES "
						+ "(?, ?, ?)");
				st.setString(1, txtCpfFuncionario.getText());
				st.setString(2, txtNomeFuncionario.getText());
				st.setString(3, txtTelefoneFuncionario.getText());
				st.execute();
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		finally {
			DB.fechaStatement(st);
			DB.fechaResultSet(rs);
			DB.closeConnection();
		}
		return count;
	}
	
	public static boolean salvarCliente(TextField txtNomeCliente, TextField txtEmailCliente, TextField txtTelefoneCliente, TextField txtRedeSocialCliente) {
		boolean count = true;
		try {
			st = DB.getConnection().prepareStatement("select nome from cliente where nome = ?");
			st.setString(1, txtNomeCliente.getText());
			rs = st.executeQuery();
			count = rs.next();
			if(count == false) {
				st = DB.getConnection().prepareStatement(
						"INSERT INTO cliente "
						+ "(email, nome, rede_social, telefone) "
						+ "VALUES "
						+ "(?, ?, ?, ?)");
				st.setString(1, txtEmailCliente.getText());
				st.setString(2, txtNomeCliente.getText());
				st.setString(3, txtRedeSocialCliente.getText());
				st.setString(4, txtTelefoneCliente.getText());
				st.execute();
			}
		}
		catch(SQLException e) {
			Alerts.showAlert("ERRO!", e.getMessage(), null, AlertType.ERROR);
		}
		finally {
			DB.fechaStatement(st);
			DB.fechaResultSet(rs);
			DB.closeConnection();
		}
		return count;
	}
	
	public static int salvarTransacao(TextField tfCliente, ChoiceBox<Funcionario> cbFuncionario, LocalDate dpData, TextField tfValor, ChoiceBox<String> cbFormaPagamento) {
		int maiorId = 0;
		try {
			int clienteId = 0;
			SimpleDateFormat formataData = new SimpleDateFormat("yyyy-MM-dd");
			Date data = formataData.parse(dpData.toString());
			for(Cliente cliente : Cadastro.clientes) {
				if(tfCliente.getText().equals(cliente.getNome())) {
					clienteId = cliente.getId();
					tfCliente.setText("");
				}
			}
			
			if(!(tfCliente.getText().isEmpty())) {
				JOptionPane.showMessageDialog(null,"Primeiro crie um novo cliente com todos os dados"
						+ " ou então crie um cliente com pelo menos o dado: NOME", "Cliente não encontrado", JOptionPane.ERROR_MESSAGE);
						tfCliente.setText("");
			}
			else {
				st = DB.getConnection().prepareStatement("select max(id) from transacao");
				rs = st.executeQuery();
				while(rs.next()) {
					maiorId = rs.getInt(1);
				}
				maiorId += 1;
				DB.fechaStatement(st);
				st = DB.getConnection().prepareStatement(
						"INSERT INTO transacao "
						+ "(idcliente, cpffuncionario, formapagamento, data, valor, id) "
						+ "VALUES "
						+ "(?, ?, ?, ?, ?, ?)");
				
			
				st.setInt(1, clienteId);
				st.setString(2, cbFuncionario.getValue().getCpf());
				st.setString(3, cbFormaPagamento.getValue());
				st.setDate(4, new java.sql.Date(data.getTime()));
				st.setDouble(5, Double.parseDouble(tfValor.getText().replaceAll(",", ".")));
				st.setInt(6, maiorId);
				st.execute();
				return maiorId;
			}	
		}
		catch(SQLException | ParseException e) {
			e.printStackTrace();
		}
		finally {
			DB.fechaStatement(st);
			DB.closeConnection();
		}
		return maiorId;
	}
	
	public static void salvarCaixa(LocalDate dpData, double total, double cartao, double dinheiro) {
		try {
				SimpleDateFormat formataData = new SimpleDateFormat("yyyy-MM-dd");
				Date data = formataData.parse(dpData.toString());
				st = DB.getConnection().prepareStatement(
						"REPLACE INTO caixa "
						+ "(total, data, total_cartao, total_dinheiro) "
						+ "VALUES "
						+ "(?, ?, ?, ? )");
				st.setDouble(1, total);
				st.setDate(2, new java.sql.Date(data.getTime()));
				st.setDouble(3, cartao);
				st.setDouble(4, dinheiro);
				st.execute();
		}
		catch(SQLException | ParseException e) {
			e.printStackTrace();
		}
		finally {
			DB.fechaStatement(st);
			DB.closeConnection();
		}
	}
	
	public static void salvarAgendamento(String funcionario, int cliente, LocalDate dpData, String horario) {
		try {
			SimpleDateFormat formataData = new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat formataHora = new SimpleDateFormat("HH:mm:ss");
			Date data = formataData.parse(dpData.toString());
			for(Funcionario fun : Cadastro.funcionarios) {
				if(funcionario.equals(fun.getNome())) {
					funcionario = fun.getCpf();
				}
			}
			
			st = DB.getConnection().prepareStatement(
						"INSERT INTO agenda"
						+ "(idcliente, cpffuncionario, data, time) "
						+ "VALUES "
						+ "(?, ?, ?, ? )");
			st.setInt(1, cliente);
			st.setString(2, funcionario);
			st.setDate(3, new java.sql.Date(data.getTime()));
			st.setTime(4, new java.sql.Time(formataHora.parse(horario).getTime()));
			st.execute();
		}
		catch(SQLException | ParseException e) {
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
