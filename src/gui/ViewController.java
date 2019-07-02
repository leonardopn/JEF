package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import model.services.Carregar;
import model.services.Salvar;

public class ViewController implements Initializable{

	@FXML
	private Button btCriaFuncionario;	
	
	@FXML
	private Button btExcluiFuncionario;
	
	@FXML
	private Button btSalvar;
	
	@FXML
	private Button btCarregar;
	
	@FXML
	public void onBtCriaFuncionarioAction(){
		try {
			Parent parent = FXMLLoader.load(getClass().getResource("/gui/ViewFuncionario.fxml"));
	    		Scene scene = new Scene(parent);
	    		Stage stage = new Stage();
	        	stage.setScene(scene);
	        	stage.show();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void onBtExcluiFuncionarioAction(){
		try {
			Parent parent2 = FXMLLoader.load(getClass().getResource("/gui/ViewListaFuncionario.fxml"));
	    		Scene scene2 = new Scene(parent2);
	    		Stage stage2 = new Stage();
	        	stage2.setScene(scene2);
	        	stage2.show();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void onBtSalvarAction(){
		Salvar.salvarFuncionario();
	}
	
	public void onBtCarregarAction(){
		Carregar.carregaFuncionario();
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		Carregar.carregaFuncionario();
	}

}
