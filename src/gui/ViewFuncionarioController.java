package gui;

import java.net.URL;
import java.util.ResourceBundle;

import application.Main;
import gui.util.Alerts;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import model.entities.Funcionario;
import model.services.Cadastro;

public class ViewFuncionarioController implements Initializable{
	
	ObservableList<Funcionario> obFuncionario;
	
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
		Main.trocaTela("main");
	}
	
	@FXML
	public void onBtCriaFuncionarioAction(){
		try {
			int id = Integer.parseInt(txtIdFuncionario.getText());
			String nome = txtNomeFuncionario.getText();
			Double salario = Double.parseDouble(txtSalarioFuncionario.getText());
			Funcionario fun01 = new Funcionario(nome, id, salario);
			Cadastro.verificaFuncionario(fun01);
			Cadastro.funcionarios.add(fun01);
			carregaFuncionario();
		}
		catch (NumberFormatException e) {
			Alerts.showAlert("Error", "Parse error", e.getMessage(), AlertType.ERROR);
		}
		
	}
	
	public void carregaFuncionario() {
			obFuncionario = FXCollections.observableArrayList(Cadastro.funcionarios);
	        tvFuncionario.setItems(obFuncionario);
	}
	
	public void excluirFuncionario() {
		ObservableList<Funcionario> obExcluirFuncionario = FXCollections.observableArrayList();
		
		for(Funcionario fun : obFuncionario) {
			if(fun.getSelect().isSelected()) {
				obExcluirFuncionario.add(fun);
			}
		}
		obFuncionario.removeAll(obExcluirFuncionario);
		Cadastro.funcionarios.removeAll(obExcluirFuncionario);
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
