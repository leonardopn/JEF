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
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import model.entities.Funcionario;
import model.services.Cadastro;
import model.services.Carregar;
import model.services.Salvar;

public class ViewFuncionarioController implements Initializable{
	
	ObservableList<Funcionario> obFuncionario;
	
	@FXML
	private Button btRecarregar;
	
	@FXML
	private Button btAtualizar;
	
	@FXML
	private Button btCriaFuncionario;
	
	@FXML
	private Button btVoltar;
	
	@FXML
	private Button btExcluiFuncionario;
	
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
    private TableColumn<Funcionario, Double> colunaSalario;
	
	@FXML
    private TableColumn<Funcionario, CheckBox> colunaSelect;
	
	@FXML
	public void onBtVoltarAction() {
		Main.getStage().setScene(Main.getMain());
	}
	
	@FXML
	public void atualizaFuncionario() {
		Parent parent;
		try {
			parent = FXMLLoader.load(getClass().getResource("/gui/view/ViewAtualizaFuncionario.fxml"));
			Scene scene = new Scene(parent);
			Main.getStage().setScene(scene);
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}
	
	@FXML
	public void onBtDesfazerAction(){
		Carregar.carregaFuncionario();
		carregaFuncionario();
	}
	
	public void carregaFuncionario() {
		obFuncionario = FXCollections.observableArrayList(Cadastro.funcionarios);
        tvFuncionario.setItems(obFuncionario);
        tvFuncionario.refresh();
	}

	@FXML
	public void onBtCriaFuncionarioAction(){
		if(Alerts.showAlertAtualizacao()) {
			try {
				int id = Integer.parseInt(txtIdFuncionario.getText());
				String nome = txtNomeFuncionario.getText();
				Double salario = Double.parseDouble(txtSalarioFuncionario.getText());
				Funcionario fun01 = new Funcionario(nome, id, salario);
				Cadastro.verificaFuncionario(fun01);
				Cadastro.funcionarios.add(fun01);
				Salvar.salvarFuncionario(txtIdFuncionario, txtNomeFuncionario, txtSalarioFuncionario);
				carregaFuncionario();
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
					Salvar.excluirFuncionario(fun);
				}
			}
			obFuncionario.removeAll(obExcluirFuncionario);
			Cadastro.funcionarios.removeAll(obExcluirFuncionario);
		}
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		colunaNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colunaId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colunaSalario.setCellValueFactory(new PropertyValueFactory<>("salario"));
        colunaSelect.setCellValueFactory(new PropertyValueFactory<>("select"));
		carregaFuncionario();
	}
}
