package model.services;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import model.entities.Cliente;
import model.entities.Funcionario;

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
	
	public static void salvar() {
		salvarCliente();
		salvarFuncionario();
	}
	
	
}
