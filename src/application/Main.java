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
	
	
	@Override
	public void start(Stage primaryStage) {
		try {
			stage = primaryStage;
			Parent fxmlLogin = FXMLLoader.load(getClass().getResource("/gui/view/ViewLogin.fxml"));
			login = new Scene(fxmlLogin);
			stage.setScene(login);
			stage.show();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static Stage getStage() {
		return stage;
	}
	
	public static Scene getScene() {
		return login;
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
