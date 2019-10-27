package gui.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import org.controlsfx.control.textfield.TextFields;

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
import model.services.Atualizar;
import model.services.Cadastro;

public class ViewAtualizaFuncionarioController implements Initializable{
	
	ObservableList<Funcionario> obFuncionario;
	
	@FXML
	private Button btAtualiza;
	
	@FXML
	private Button btVoltar;
	
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
	public void selecionaFuncionario() {
		Funcionario fun = tvFuncionario.getSelectionModel().getSelectedItem();
		txtCpfFuncionario.setText(fun.getCpf());
		txtCpfFuncionario.setDisable(true);
		txtNomeFuncionario.setText(fun.getNome());
	}
	
	public void voltaScene() {
		try {
			Parent fxmlfuncionario = FXMLLoader.load(getClass().getResource("/gui/view/ViewFuncionario.fxml"));
			Scene funcionario = new Scene(fxmlfuncionario);
			ViewController.getStageFuncionario().setScene(funcionario);
			ViewController.getStageFuncionario().centerOnScreen();
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
			Funcionario funTemp = tvFuncionario.getSelectionModel().getSelectedItem();
			String cpf = txtCpfFuncionario.getText();
			String nome = txtNomeFuncionario.getText();
			Double salario = funTemp.getSalario();
			Funcionario funcionario = new Funcionario(cpf, nome, salario, 1);
			for(Funcionario fun : Cadastro.funcionarios) {
				if(fun.getCpf().equals(funcionario.getCpf())) {
					fun.setNome(nome);
					tvFuncionario.refresh();
				}
			}
			Atualizar.atualizarFuncionario(funcionario);
			obFuncionario = FXCollections.observableArrayList(Cadastro.funcionarios);
			ViewController.getTvAgendaTemp().refresh();
			ViewController.getTvFuncionarioTemp().refresh();
			ViewController.getStageCaixa().hide();
			ViewController.getStageCliente().hide();
			ViewController.getStagePagamento().hide();
			
		}
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		colunaNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
		colunaCpf.setCellValueFactory(new PropertyValueFactory<>("cpf"));
		carregaFuncionario();
	}
}
