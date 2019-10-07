package gui.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import gui.util.Alerts;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import model.entities.Funcionario;
import model.services.Cadastro;
import model.services.Excluir;
import model.services.Salvar;

public class ViewFuncionarioController implements Initializable{
	
	ObservableList<Funcionario> obFuncionario;
	Scene main;
	
	@FXML
	private Button btAtualizar;
	
	@FXML
	private Button btCriaFuncionario;
	
	@FXML
	private Button btExcluiFuncionario;
	
	@FXML
	private TextField txtCpfFuncionario;
	
	@FXML
	private TextField txtNomeFuncionario;
	
	@FXML
	private TableView<Funcionario> tvFuncionario = new TableView<>();
	
	@FXML
	private TableColumn<Funcionario, String> colunaNome;
	
	@FXML
    private TableColumn<Funcionario, String> colunaCpf;
	
	@FXML
    private TableColumn<Funcionario, CheckBox> colunaSelect;
	
	@FXML
	public void atualizaFuncionario() {
		Parent parent;
		try {
			parent = FXMLLoader.load(getClass().getResource("/gui/view/ViewAtualizaFuncionario.fxml"));
			Scene scene = new Scene(parent);
			ViewController.getStageFuncionario().setScene(scene);
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}
	
	public void carregaFuncionario() {
		obFuncionario = FXCollections.observableArrayList(Cadastro.funcionarios);
		for(Funcionario fun : obFuncionario) {
			System.out.println(fun);
		}
        tvFuncionario.setItems(obFuncionario);
        tvFuncionario.refresh();
	}

	@FXML
	public void onBtCriaFuncionarioAction(){
		if(Alerts.showAlertAtualizacao()) {
			try {
				String cpf = txtCpfFuncionario.getText();
				String nome = txtNomeFuncionario.getText();
				Double salario = 0.00;
				int status = 1;
				Funcionario fun01 = new Funcionario(cpf, nome, salario, status);
				
				if(Cadastro.verificaFuncionario(fun01)) {
					Cadastro.funcionarios.add(fun01);
					Salvar.salvarFuncionario(txtCpfFuncionario, txtNomeFuncionario);
					obFuncionario = FXCollections.observableArrayList(Cadastro.funcionarios);
		    		ViewController.getTvAgendaTemp().setItems(obFuncionario);
					ViewController.getTvFuncionarioTemp().setItems(obFuncionario);
					ViewController.getStageFuncionario().close();
					System.out.println(fun01);
				}
			}
			catch (NumberFormatException e) {
				Alerts.showAlert("Error", "Parse error", e.getMessage(), AlertType.ERROR);
			}
		}
	}
	
	public void excluirFuncionario() {
		if(Alerts.showAlertExclusao()) {
			ObservableList<Funcionario> obExcluirFuncionario = FXCollections.observableArrayList();
			
			for(Funcionario fun : obFuncionario) {
				if(fun.getSelect().isSelected()) {
					obExcluirFuncionario.add(fun);
					Excluir.excluirFuncionario(fun);
				}
			}
			obFuncionario.removeAll(obExcluirFuncionario);
			Cadastro.funcionarios.removeAll(obExcluirFuncionario);
			
			//vai atulizar no stage main se excluir funcion�rios
			obFuncionario = FXCollections.observableArrayList(Cadastro.funcionarios);
    		ViewController.getTvAgendaTemp().setItems(obFuncionario);
			ViewController.getTvFuncionarioTemp().setItems(obFuncionario);;
		}
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		colunaNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colunaCpf.setCellValueFactory(new PropertyValueFactory<>("cpf"));
        colunaSelect.setCellValueFactory(new PropertyValueFactory<>("select"));
		carregaFuncionario();
	}
}
