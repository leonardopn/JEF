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
import javafx.scene.control.Alert.AlertType;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.entities.Cliente;
import model.services.Cadastro;
import model.services.Carregar;

public class ViewClienteController implements Initializable{
	
	ObservableList<Cliente> obCliente;
	
	@FXML
	private Button btCriaCliente;
	
	@FXML
	private Button btAtualizaCliente;
	
	@FXML
	private Button btVoltar;
	
	@FXML
	private Button btDesfazer;
	
	@FXML
	private Button btExcluiCliente;
	
	@FXML
	private TextField txtIdCliente;
	
	@FXML
	private TextField txtNomeCliente;
	
	@FXML
	private TextField txtEmailCliente;
	
	@FXML
	private TextField txtTelefoneCliente;
	
	@FXML
	private TableView<Cliente> tvCliente = new TableView<>();
	
	@FXML
	private TableColumn<Cliente, String> colunaNome;
	
	@FXML
    private TableColumn<Cliente, Integer> colunaId;
	
	@FXML
    private TableColumn<Cliente, String> colunaEmail;
	
	@FXML
    private TableColumn<Cliente, String> colunaTelefone;
	
	@FXML
    private TableColumn<Cliente, CheckBox> colunaSelect;
	
	@FXML
	public void onBtVoltarAction() {
		Main.trocaTela("main");
	}
	
	@FXML
	public void atualizaCadastro() {
		Parent parent;
		try {
			parent = FXMLLoader.load(getClass().getResource("/gui/view/ViewAtualizaCliente.fxml"));
			Scene scene = new Scene(parent);
			Stage stage = new Stage();
	    	stage.setScene(scene);
	    	stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}
	
	@FXML
	public void onBtDesfazerAction(){
		Carregar.carregaCliente();
		carregaCliente();
	}
	
	@FXML
	public void onBtCriaClienteAction(){
		try {
			int id = Integer.parseInt(txtIdCliente.getText());
			String nome = txtNomeCliente.getText();
			String email = txtEmailCliente.getText();
			String telefone = txtTelefoneCliente.getText();
			Cliente cliente = new Cliente(id, nome, email, telefone);
			Cadastro.verificaCliente(cliente);
			Cadastro.clientes.add(cliente);
			carregaCliente();
		}
		catch (NumberFormatException e) {
			Alerts.showAlert("Error", "Parse error", e.getMessage(), AlertType.ERROR);
		}
	}
	
	public void carregaCliente() {
			obCliente = FXCollections.observableArrayList(Cadastro.clientes);
	        tvCliente.setItems(obCliente);
	}
	
	public void excluirCliente() {
		ObservableList<Cliente> obExcluirCliente = FXCollections.observableArrayList();
		
		for(Cliente cli : obCliente) {
			if(cli.getSelect().isSelected()) {
				obExcluirCliente.add(cli);
			}
		}
		obCliente.removeAll(obExcluirCliente);
		Cadastro.clientes.removeAll(obExcluirCliente);
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		colunaId.setCellValueFactory(new PropertyValueFactory<>("id"));
		colunaNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colunaEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colunaTelefone.setCellValueFactory(new PropertyValueFactory<>("telefone"));
        colunaSelect.setCellValueFactory(new PropertyValueFactory<>("select"));   
		carregaCliente();
	}
}