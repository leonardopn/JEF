package model.services;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import model.entities.Caixa;
import model.entities.Cliente;
import model.entities.Funcionario;
import model.entities.Transacao;

public class Salvar {
	public static void salvarFuncionario() {
		String caminho = System.getProperty("user.home")+File.separatorChar+"Documents"+File.separatorChar+"teste"+ File.separatorChar+"funcionario.csv";
		if (IdentificadorSO.sistema() == "linux"){
				caminho = System.getProperty("user.home")+File.separatorChar+"Documentos"+File.separatorChar+"teste"+ File.separatorChar+"funcionario.csv";
		}
		
		File arquivoFuncionario = new File(caminho);
		try(BufferedWriter bwFuncionario = new BufferedWriter(new FileWriter(arquivoFuncionario))) {
			
			for(Funcionario fun : Cadastro.funcionarios) {
				bwFuncionario.write(fun.getNome()+ ";"  + fun.getId() + ";" + fun.getSalario() + "\n");
			}
			bwFuncionario.close();
			
		} catch (IOException e) {
			System.out.println("Ocorreu um erro ao salvar o arquivo funcionario.csv: " + e.getMessage());
		}
	}
	
	public static void salvarCliente() {
		String caminho = System.getProperty("user.home")+File.separatorChar+"Documents"+File.separatorChar+"teste"+ File.separatorChar+"clientes.csv";
		if (IdentificadorSO.sistema() == "linux"){
			caminho = System.getProperty("user.home")+File.separatorChar+"Documentos"+File.separatorChar+"teste"+ File.separatorChar+"clientes.csv";
		}
		File arquivoCliente = new File(caminho);
		try(BufferedWriter bwCliente = new BufferedWriter(new FileWriter(arquivoCliente))) {
			
			for(Cliente cli : Cadastro.clientes) {
				bwCliente.write(cli.getId() + ";" + cli.getNome()+ ";" + cli.getEmail() + ";" + cli.getTelefone() + "\n");
			}
			bwCliente.close();
			
		} catch (IOException e) {
			System.out.println("Ocorreu um erro ao salvar o arquivo cliente.csv: " + e.getMessage());
		}
	}
	
	public static void salvarTransacao() {
		for(Transacao tran : Caixa.caixa) {
			String caminho = System.getProperty("user.home")+File.separatorChar+"Documents"+File.separatorChar+"teste"+ File.separatorChar+"transacoes"+ File.separatorChar+ tran.getData()+".csv";
			if (IdentificadorSO.sistema() == "linux"){
				caminho = System.getProperty("user.home")+File.separatorChar+"Documentos"+File.separatorChar+"teste"+ File.separatorChar+"transacoes"+ File.separatorChar+ tran.getData()+".csv";
			}
			File arquivoTransacao = new File(caminho);
			try(BufferedWriter bwTransacao = new BufferedWriter(new FileWriter(arquivoTransacao))) {
				for(Transacao tran2 : Caixa.caixa) {
					if(tran2.getData() == tran.getData()) {
						bwTransacao.write(tran2.getId() + ";" + 
								  tran2.getValor()+ ";" + 
								  String.valueOf(tran2.getData()) + ";" + 
								  String.valueOf(tran2.getCliente()) + ";" +
								  String.valueOf(tran2.getAtendente()) + ";" +
								  tran2.getFormaPagamento()+ "\n");
					}
				}
			}	
			catch(IOException e) {
				System.out.println("Ocorreu um erro ao salvar um arquivo de transação: " + e.getMessage());
			}
		}
	}
	
	public static void salvar() {
		salvarCliente();
		salvarFuncionario();
		salvarTransacao();
	}
	
	
}
