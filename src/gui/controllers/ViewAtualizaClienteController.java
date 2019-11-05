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
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import model.entities.Cliente;
import model.entities.Funcionario;
import model.services.Atualizar;
import model.services.Cadastro;
import model.services.Carregar;

public class ViewAtualizaClienteController implements Initializable{
	
	ObservableList<Cliente> obCliente;
	ObservableList<Cliente> obClienteExcluido;
	
	@FXML
	private Button btAtualiza;
	
	@FXML
	private Button btVoltar;
	
	@FXML
	private TextField txtCpfCliente;
	
	@FXML
	private TextField txtNomeCliente;
	
	@FXML
	private TextField txtEmailCliente;
	
	@FXML
	private TextField txtTelefoneCliente;
	
	@FXML
	private TextField txtRedeSocialCliente;
	
	@FXML
	private TableView<Cliente> tvCliente = new TableView<>();
	
	@FXML
	private TableColumn<Cliente, String> colunaNome;
	
	@FXML
    private TableColumn<Cliente, String> colunaCpf;
	
	public void carregaCliente() {
		obCliente = FXCollections.observableArrayList(Cadastro.clientes);
        tvCliente.setItems(obCliente);
        
	}
	
	public void voltaScene() {
		try {
			Parent fxmlCliente = FXMLLoader.load(getClass().getResource("/gui/view/ViewCliente.fxml"));
			Scene Cliente = new Scene(fxmlCliente);
			ViewController.getStageCliente().setScene(Cliente);
			ViewController.getStageCliente().centerOnScreen();
		}
		catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	@FXML
	public void selecionaCliente() {
		Cliente cli = tvCliente.getSelectionModel().getSelectedItem();
		txtCpfCliente.setText(cli.getCpf());
		txtCpfCliente.setDisable(true);
		txtNomeCliente.setText(cli.getNome());
		txtEmailCliente.setText(cli.getEmail());
		txtTelefoneCliente.setText(cli.getTelefone());
		txtRedeSocialCliente.setText(cli.getRedeSocial());
	}
	
	@FXML
	public void atualizaCliente() {
		if(Alerts.showAlertAtualizacao()) {
			Cliente clienteTemp = tvCliente.getSelectionModel().getSelectedItem();
			String nome = txtNomeCliente.getText();
			String email = txtEmailCliente.getText();
			String telefone = txtTelefoneCliente.getText();
			String redeSocial = txtRedeSocialCliente.getText();
			for(Cliente cli : Cadastro.clientes) {
				if(cli.getCpf().equals(clienteTemp.getCpf())) {
					cli.setNome(nome);
					cli.setEmail(email);
					cli.setRedeSocial(redeSocial);
					cli.setTelefone(telefone);
					tvCliente.refresh();
				}
			}
			Atualizar.atualizarCliente(clienteTemp);
			obCliente = FXCollections.observableArrayList(Cadastro.clientes);
			Carregar.carregaAgendaFuncionario(ViewController.getDpDataTemp());
			ViewController.getTvAgendaTemp().refresh();
			ViewController.getStageCaixa().hide();
			ViewController.getStageCliente().hide();
			ViewController.getStagePagamento().hide();
		}
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		colunaCpf.setCellValueFactory(new PropertyValueFactory<>("cpf"));
		colunaNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
		carregaCliente();
	}

}
