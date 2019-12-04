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

import db.DB;
import model.dao.DaoCliente;
import model.dao.DaoFuncionario;
import model.entities.Caixa;
import model.entities.Transacao;

public class Carregar {
	static Statement st = null;
	static ResultSet rs = null;
	
	public static void carregaCaixa() {
		String linha = "";
		String caminho = System.getProperty("user.home")+File.separatorChar+"Documents"+File.separatorChar+"JEF_DATA"+ File.separatorChar+"caixa.csv";
		if (IdentificadorSO.sistema() == "linux"){
				caminho = System.getProperty("user.home")+File.separatorChar+"Documentos"+File.separatorChar+"JEF_DATA"+ File.separatorChar+"caixa.csv";
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
	
	public static void carregaTransacaoExpecifica(LocalDate data) {
		Caixa.caixa.clear();
		DateTimeFormatter localDateFormatadaProcura = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		try {
			 st = DB.getConnection().createStatement();
			 rs = st.executeQuery("select * from transacao t "
			 						+ "inner join funcionario f on(t.cpffuncionario = f.cpffuncionario)"
			 						+ "inner join cliente c on(c.idcliente = t.idcliente)"
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
	
	public static void carregarBase() {
		DaoCliente.carregaCliente();
		DaoFuncionario.carregaFuncionario();
		carregaCaixa();
	}
}
