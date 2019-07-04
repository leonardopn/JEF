package model.services;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import model.entities.Cliente;
import model.entities.Funcionario;

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
	
	public static void carregaTransacao() {
		
	}
	
	public static void carregar() {
		carregaCliente();
		carregaFuncionario();
	}
}
