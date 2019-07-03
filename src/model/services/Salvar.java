package model.services;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import model.entities.Funcionario;

public class Salvar {
	public static void salvarFuncionario() {
		String caminho = System.getProperty("user.home")+File.separatorChar+"Documents"+File.separatorChar+"teste"+ File.separatorChar+"funcionario.csv";
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
}
