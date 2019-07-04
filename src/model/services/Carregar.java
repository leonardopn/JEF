package model.services;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import gui.ViewCaixaController;
import model.entities.Caixa;
import model.entities.Cliente;
import model.entities.Funcionario;
import model.entities.Transacao;

public class Carregar {
	public static void carregaFuncionario() {
		String linha = "";
		String caminho = System.getProperty("user.home") + File.separator + "Documents" + File.separator + "teste" + File.separator + "funcionario.csv";
		if(IdentificadorSO.sistema() == "linux"){
			caminho = System.getProperty("user.home")+File.separatorChar+"Documentos"+File.separatorChar+"teste"+ File.separatorChar+"funcionario.csv";
		}
		try(BufferedReader brFuncionario = new BufferedReader(new FileReader(caminho));) {
			while((linha = brFuncionario.readLine()) != null) {	
				String[] linhaFuncionario = linha.split(";");				
				Funcionario funcionario = new Funcionario(
						linhaFuncionario[0],
						Integer.parseInt(linhaFuncionario[1]),
						Double.valueOf(linhaFuncionario[2]));
				Cadastro.funcionarios.add(funcionario);
			}
			brFuncionario.close();
		}
		catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void carregaCliente() {
		String linha = "";
		String caminho = System.getProperty("user.home") + File.separator + "Documents" + File.separator + "teste" + File.separator + "clientes.csv";
		if(IdentificadorSO.sistema() == "linux"){
			caminho = System.getProperty("user.home")+File.separatorChar+"Documentos"+File.separatorChar+"teste"+ File.separatorChar+"clientes.csv";
		}
		try(BufferedReader brCliente = new BufferedReader(new FileReader(caminho));) {
			while((linha = brCliente.readLine()) != null) {	
				String[] linhaCliente = linha.split(";");				
				Cliente cliente = new Cliente(
						Integer.parseInt(linhaCliente[0]),
						linhaCliente[1],
						linhaCliente[2],
						linhaCliente[3]);
				Cadastro.clientes.add(cliente);
			}
			brCliente.close();
		}
		catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	private static String getDateTime() {
	    DateFormat dateFormat = new SimpleDateFormat("yyy-MM-dd");
	    Date date = new Date();
	    return dateFormat.format(date);
	}
	
//	public static void carregaTransacao() {
//		String linha = "";
//		String caminho = System.getProperty("user.home") + File.separator + "Documents" + File.separator + "teste" + File.separator + "transacoes" + File.separator + ViewCaixaController.dpSelecao.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))+".csv";
//		if(IdentificadorSO.sistema() == "linux"){
//			caminho = System.getProperty("user.home")+File.separatorChar+"Documentos"+File.separatorChar+"teste"+ File.separator + "transacoes" + File.separator + ViewCaixaController.dpSelecao.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))+".csv";
//		}
//		try(BufferedReader brTransacao = new BufferedReader(new FileReader(caminho));) {
//			while((linha = brTransacao.readLine()) != null) {	
//				String[] linhaTransacao = linha.split(";");	
//				Transacao tran = new Transacao(
//						Integer.parseInt(linhaTransacao[0]),
//						Double.parseDouble(linhaTransacao[1]),
//						LocalDate.parse(linhaTransacao[2]),
//						linhaTransacao[3],
//						linhaTransacao[4],
//						linhaTransacao[5]);
//						
//				Caixa.caixa.add(tran);
//			}
//			brTransacao.close();
//		}
//		catch(IOException e) {
//			e.printStackTrace();
//		}
//	}
	
	public static void carregar() {
		carregaCliente();
		carregaFuncionario();
		//carregaTransacao();
	}
}
