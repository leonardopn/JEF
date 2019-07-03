package application;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

	private static Stage stage;
	private static Scene main;
	private static Scene caixa;
	private static Scene funcionario;
	private static Scene cliente;

	@Override
	public void start(Stage primaryStage) {
		try {
			stage = primaryStage;

			Parent fxmlMain = FXMLLoader.load(getClass().getResource("/gui/View.fxml"));
			main = new Scene(fxmlMain);
			Parent fxmlCaixa = FXMLLoader.load(getClass().getResource("/gui/ViewCaixa.fxml"));
			caixa = new Scene(fxmlCaixa);

			Parent fxmlfuncionario = FXMLLoader.load(getClass().getResource("/gui/ViewFuncionario.fxml"));
			funcionario = new Scene(fxmlfuncionario);

			Parent fxmlCliente = FXMLLoader.load(getClass().getResource("/gui/ViewCliente.fxml"));
			cliente = new Scene(fxmlCliente);

			stage.setScene(main);
			stage.show();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void trocaTela(String src) {
		switch (src) {
		case "main":
			stage.setScene(main);
			break;
		case "caixa":
			stage.setScene(caixa);
			break;
		case "funcionario":
			stage.setScene(funcionario);
			break;
		case "cliente":
			stage.setScene(cliente);
			break;
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}
