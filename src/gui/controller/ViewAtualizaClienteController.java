package gui.controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import model.entities.Cliente;
import model.services.Cadastro;

public class ViewAtualizaClienteController implements Initializable{
	
	ObservableList<Cliente> obCliente;
	
	@FXML
	private Button btAtualiza;
	
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
	
	public void carregaCliente() {
		obCliente = FXCollections.observableArrayList(Cadastro.clientes);
        tvCliente.setItems(obCliente);
	}
	
	@FXML
	public void selecionaCliente() {
		Cliente cli = tvCliente.getSelectionModel().getSelectedItem();
		txtIdCliente.setText(String.valueOf(cli.getId()));
		txtIdCliente.setDisable(true);
		txtNomeCliente.setText(String.valueOf(cli.getNome()));
		txtEmailCliente.setText(String.valueOf(cli.getEmail()));
		txtTelefoneCliente.setText(String.valueOf(cli.getTelefone()));
		
	}
	
	@FXML
	public void atualizaCliente() {
		int id = Integer.parseInt(txtIdCliente.getText());
		String nome = txtNomeCliente.getText();
		String email = txtEmailCliente.getText();
		String telefone = txtTelefoneCliente.getText();
		Cliente cliente = new Cliente(id, nome, email, telefone);
		Cadastro.clientes.remove(colunaId);
		Cadastro.verificaCliente(cliente);
		Cadastro.clientes.add(cliente);
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		colunaId.setCellValueFactory(new PropertyValueFactory<>("id"));
		colunaNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
		carregaCliente();
	}

}
