package gui.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;

public class GeraLogUtils {

	public static void gravarLogQuery(String txt) {
		String caminho = System.getProperty("user.home") + File.separatorChar + "Documents" + File.separatorChar
				+ "JEF_DATA" + File.separatorChar + "logs" + File.separatorChar + "logQuery.txt";
		if (IdentificadorSoUtils.sistema().equals("linux")) {
			caminho = System.getProperty("user.home") + File.separatorChar + "Documentos" + File.separatorChar
					+ "JEF_DATA" + File.separatorChar + "logs" + File.separatorChar + "logQuery.txt";
		}

		try (BufferedWriter bwLogQuery = new BufferedWriter(new FileWriter(caminho, true))) {
			bwLogQuery.write(LocalDateTime.now()+" - "+txt+";\n");
			bwLogQuery.newLine();
			bwLogQuery.flush();
			bwLogQuery.close();

		} catch (IOException e) {
			System.out.println("Ocorreu um erro ao salvar o arquivo cursos.csv: " + e.getMessage());
		}
	}
}
