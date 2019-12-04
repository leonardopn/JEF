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
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
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
	
	public static void salvar() {
		salvarStatus();
	}
}
