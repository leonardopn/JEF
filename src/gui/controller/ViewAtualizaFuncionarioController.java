package gui.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import application.Main;
import gui.util.Alerts;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import model.entities.Funcionario;
import model.services.Cadastro;

public class ViewAtualizaFuncionarioController implements Initializable{
	
	ObservableList<Funcionario> obFuncionario;
	ObservableList<Funcionario> obFuncionarioExcluido;
	
	@FXML
	private Button btAtualiza;
	
	@FXML
	private TextField txtIdFuncionario;
	
	@FXML
	private TextField txtNomeFuncionario;
	
	@FXML
	private TextField txtSalarioFuncionario;
	
	@FXML
	private TableView<Funcionario> tvFuncionario = new TableView<>();
	
	@FXML
	private TableColumn<Funcionario, String> colunaNome;
	
	@FXML
    private TableColumn<Funcionario, Integer> colunaId;
	
	@FXML
	public void selecionaFuncionario() {
		Funcionario fun = tvFuncionario.getSelectionModel().getSelectedItem();
		txtIdFuncionario.setText(String.valueOf(fun.getId()));
		txtIdFuncionario.setDisable(true);
		txtNomeFuncionario.setText(fun.getNome());
		txtSalarioFuncionario.setText(String.valueOf(fun.getSalario()));
	}
	
	public void voltaScene() {
		try {
			Parent fxmlfuncionario = FXMLLoader.load(getClass().getResource("/gui/view/ViewFuncionario.fxml"));
			Scene funcionario = new Scene(fxmlfuncionario);
			Main.getStage().setScene(funcionario);
		}
		catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public void carregaFuncionario() {
		obFuncionario = FXCollections.observableArrayList(Cadastro.funcionarios);
        tvFuncionario.setItems(obFuncionario);
        tvFuncionario.refresh();
	}
	
	@FXML
	public void onAtualizaFuncionarioAction() {
		if(Alerts.showAlertAtualizacao()) {
			int id = Integer.parseInt(txtIdFuncionario.getText());
			String nome = txtNomeFuncionario.getText();
			Double salario = Double.valueOf(txtSalarioFuncionario.getText());
			Funcionario funcionario = new Funcionario(nome, id, salario);
			Cadastro.funcionarios.removeIf((Funcionario fun) -> fun.getId() == funcionario.getId());
			Cadastro.funcionarios.add(funcionario);
			voltaScene();
		}
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		colunaNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
		colunaId.setCellValueFactory(new PropertyValueFactory<>("id"));
		carregaFuncionario();
	}
}
