package model.services;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import model.entities.Funcionario;

public class Carregar {
	public static void carregaFuncionario() {
		String linha = "";
		String caminho = System.getProperty("user.home") + File.separator + "Documents" + File.separator + "teste" + File.separator + "funcionario.csv";
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
}
