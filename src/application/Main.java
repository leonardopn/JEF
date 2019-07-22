package application;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

	private static Stage stage;
	private static Scene login;
	private static Scene main;
	
	
	@Override
	public void start(Stage primaryStage) {
		try {
			Parent fxmlMain = FXMLLoader.load(getClass().getResource("/gui/view/ViewTeste.fxml"));
			main = new Scene(fxmlMain);
			stage = primaryStage;
			Parent fxmlLogin = FXMLLoader.load(getClass().getResource("/gui/view/ViewLogin.fxml"));
			login = new Scene(fxmlLogin);
			stage.setScene(login);
			stage.show();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static Scene getMain() {
		return main;
	}
	public static Stage getStage() {
		return stage;
	}
	
	public static Scene getScene() {
		return login;
	}
	
	public static void fechaProgram() {
		stage.close();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
