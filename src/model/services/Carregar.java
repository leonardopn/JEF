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
import model.dao.DaoTransacao;
import model.entities.Caixa;
import model.entities.Transacao;

public class Carregar {
	static Statement st = null;
	static ResultSet rs = null;
	
	public static void carregarBase() {
		DaoCliente.carregaCliente();
		DaoFuncionario.carregaFuncionario();
		DaoTransacao.carregaCaixa();
	}
}
