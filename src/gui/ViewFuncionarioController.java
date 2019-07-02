package gui;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import gui.util.Alerts;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import model.entities.Funcionario;
import model.services.Cadastro;

public class ViewFuncionarioController implements Initializable{
	
	private List<Funcionario> listExcluidos = new ArrayList<>();
	
	private List<Funcionario> listVoltar = new ArrayList<>();
	
	@FXML
	private Label labelResultado;;
	
	@FXML
	private ListView<Funcionario> listFuncionario;
	
	@FXML
	private ListView<Funcionario> listExclucaoFuncionario;
	
	@FXML
	private ObservableList<Funcionario> obsExcluirFuncionario;
	
	@FXML
	private ObservableList<Funcionario> obsFuncionario;
	
	@FXML
	private Button btCriaFuncionario;
	
	@FXML
	private TextField txtIdFuncionario;
	
	@FXML
	private TextField txtNomeFuncionario;
	
	@FXML
	private TextField txtSalarioFuncionario;
	
	@FXML
	public void onBtCriaFuncionarioAction(){
		try {
			int id = Integer.parseInt(txtIdFuncionario.getText());
			String nome = txtNomeFuncionario.getText();
			Double salario = Double.parseDouble(txtSalarioFuncionario.getText());
			Funcionario fun01 = new Funcionario(nome, id, salario);
			if(Cadastro.funcionarios.contains(fun01)) {
				Alerts.showAlert("Alerta", "Funcionário já inscrito", "Funcionário não foi adicionado, pois ID já está em uso", AlertType.INFORMATION);
			}
			Cadastro.funcionarios.add(fun01 );
			carregaFuncionarios();
		}
		catch (NumberFormatException e) {
			Alerts.showAlert("Error", "Parse error", e.getMessage(), AlertType.ERROR);
		}
		
	}
	
	public void carregaFuncionarios() {
		obsFuncionario = FXCollections.observableArrayList(Cadastro.funcionarios);
		listFuncionario.setItems(obsFuncionario);
	}
	
	public void mudaFuncionario1() {
			listExcluidos.add(listFuncionario.getSelectionModel().getSelectedItem());
			obsExcluirFuncionario = FXCollections.observableArrayList(listExcluidos);
			listExclucaoFuncionario.setItems(obsExcluirFuncionario);
			listFuncionario.getItems().remove(listFuncionario.getSelectionModel().getSelectedItem());
			Cadastro.funcionarios.remove(listFuncionario.getSelectionModel().getSelectedItem());
	}
	
	public void mudaFuncionario2() {
			Cadastro.funcionarios.add(listExclucaoFuncionario.getSelectionModel().getSelectedItem());
			obsFuncionario = FXCollections.observableArrayList(Cadastro.funcionarios);
			listFuncionario.setItems(obsFuncionario);
			listExclucaoFuncionario.getItems().remove(listExclucaoFuncionario.getSelectionModel().getSelectedItem());
	}
	
	public void excluirFuncionario() {
		Cadastro.funcionarios.remove(listFuncionario.getSelectionModel().getSelectedItem());
		carregaFuncionarios();
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		carregaFuncionarios();
	}

}
