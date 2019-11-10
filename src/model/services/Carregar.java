package model.services;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.swing.JOptionPane;

import db.DB;
import model.entities.Agendamento;
import model.entities.Caixa;
import model.entities.Cliente;
import model.entities.Funcionario;
import model.entities.Transacao;
import model.exceptions.DbException;

public class Carregar {
	static Statement st = null;
	static ResultSet rs = null;
	static ResultSet rs2 = null;
	
	public static void carregaCliente() {
		try {	
			 st = DB.getConnection().createStatement();
			 rs = st.executeQuery("select * from cliente");
			 while(rs.next()) {
				 if(rs.getInt("status") !=0) {
					 Cliente cliente = new Cliente(rs.getString("cpfcliente"), rs.getString("nome"), rs.getString("email"), rs.getString("telefone"), rs.getString("rede_social"));
					 Cadastro.clientes.add(cliente);
				 }
			 }
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		finally {
			DB.closeConnection();
			DB.fechaResultSet(rs);
			DB.fechaStatement(st);
		}
	}
	
	public static void carregaCaixa() {
		String linha = "";
		String caminho = System.getProperty("user.home")+File.separatorChar+"Documents"+File.separatorChar+"teste"+ File.separatorChar+"caixa.csv";
		if (IdentificadorSO.sistema() == "linux"){
				caminho = System.getProperty("user.home")+File.separatorChar+"Documentos"+File.separatorChar+"teste"+ File.separatorChar+"caixa.csv";
		}
		try(BufferedReader brCaixa = new BufferedReader(new FileReader(caminho));) {
			while((linha = brCaixa.readLine()) != null) {	
				String[] linhaCaixa = linha.split(";");				
				Caixa.setStatus(Boolean.parseBoolean(linhaCaixa[0]));
			}
			brCaixa.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void carregaFuncionario() {
		Cadastro.funcionarios.clear();
		try {
			 st = DB.getConnection().createStatement();
			 rs = st.executeQuery("select * from funcionario");
			 while(rs.next()) {
				 if(rs.getInt("status") !=0) {
					 Funcionario fun = new Funcionario(rs.getString("cpffuncionario"), rs.getString("nome"),  rs.getDouble("salario"), rs.getInt("status"));
					 Cadastro.funcionarios.add(fun);
				 }
			 }
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		finally {
			DB.closeConnection();
			DB.fechaResultSet(rs);
			DB.fechaStatement(st);
		}
	}
	
	public static void carregaTransacaoExpecifica(LocalDate data) {
		Caixa.caixa.clear();
		DateTimeFormatter localDateFormatadaProcura = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		try {
			 st = DB.getConnection().createStatement();
			 rs = st.executeQuery("select * from transacao t "
			 						+ "inner join funcionario f on(t.cpffuncionario = f.cpffuncionario)"
			 						+ "inner join cliente c on(c.cpfcliente = t.cpfcliente)"
			 						+ "where t.data = "
			 						+ "'" + localDateFormatadaProcura.format(data) + "'");
			 
			 while(rs.next()) {
				 SimpleDateFormat formataData = new SimpleDateFormat("dd/MM/yyyy");
				 Transacao tran = new Transacao(rs.getInt("t.id"), rs.getDouble("t.valor"), rs.getString("c.nome"), 
						 rs.getString("f.nome"),  rs.getString("t.formapagamento"), formataData.format(rs.getDate("t.data")));
				 Caixa.caixa.add(tran);
			 }
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		finally {
			DB.closeConnection();
			DB.fechaResultSet(rs);
			DB.fechaStatement(st);
		}
	}
	
	public static void carregaAgendaFuncionario(LocalDate data) {
		DateTimeFormatter localDateFormatada = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		try {
			 st = DB.getConnection().createStatement();
			 rs = st.executeQuery("SELECT * FROM agenda a inner join cliente c on(a.cpfcliente = c.cpfcliente) "
					 +"WHERE data = "
					 +"'"+localDateFormatada.format(data)+"'"
					 );
			 SimpleDateFormat formataHora = new SimpleDateFormat("HH:mm");
			 for (Funcionario fun : Cadastro.funcionarios) {
				 fun.zeraHorarios();
			 }
			 while(rs.next()) {
				 for(Funcionario fun : Cadastro.funcionarios) {
					if(rs.getString("cpffuncionario").equals(fun.getCpf())) {
						fun.retornaHorario(formataHora.format(rs.getTime("a.time")), rs.getString("c.nome"));
					}
				}
			 }
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		finally {
			DB.closeConnection();
			DB.fechaResultSet(rs);
			DB.fechaStatement(st);
		}
	}
	
	public static void carregaAgendamento(LocalDate data, String cliente) {
		Cadastro.agendamentos.clear();
		DateTimeFormatter localDateFormatada = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		for(Cliente cli : Cadastro.clientes) {
			if(cliente.equals(cli.getNome())) {
				cliente = cli.getCpf();
			}
		}
		try {
			 st = DB.getConnection().createStatement();
			 rs = st.executeQuery("SELECT * FROM agenda a inner join funcionario f on(a.cpffuncionario = f.cpffuncionario) "
			 					+ "inner join cliente c on(c.cpfcliente = a.cpfcliente)"
			 					+ "WHERE data = "
			 					+"'"+localDateFormatada.format(data)+"'"
			 					+ " AND c.cpfcliente = '"
			 					+ cliente
			 					+ "'");
			 SimpleDateFormat formataData = new SimpleDateFormat("dd/MM/yyyy");
			 SimpleDateFormat formataHora = new SimpleDateFormat("HH:mm");
			 while(rs.next()) {
				 Agendamento agen = new Agendamento(rs.getString("f.nome"), rs.getString("f.cpffuncionario"), rs.getString("c.nome"), rs.getString("c.cpfcliente"), formataData.format(rs.getDate("data")), formataHora.format(rs.getTime("a.time")));
				 Cadastro.agendamentos.add(agen);
			 }
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		finally {
			DB.closeConnection();
			DB.fechaResultSet(rs);
			DB.fechaStatement(st);
		}
	}
	
	public static void carregar() {
		carregaCliente();
		carregaFuncionario();
		carregaCaixa();
	}
}
