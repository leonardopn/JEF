package gui.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import org.controlsfx.control.textfield.TextFields;

import gui.util.Alerts;
import gui.util.Notificacoes;
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
import model.entities.Cliente;
import model.services.Cadastro;
import model.services.Carregar;
import model.services.Excluir;
import model.services.Salvar;

public class ViewClienteController implements Initializable{
	
	ObservableList<Cliente> obCliente;
	
	@FXML
	private Button btCriaCliente;
	
	@FXML
	private Button btAtualizaCliente;
	
	@FXML
	private Button btExcluiCliente;
	
	@FXML
	private TextField txtNomeCliente;
	
	@FXML
	private TextField txtRedeSocialCliente;
	
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
    private TableColumn<Cliente, String> colunaRedeSocial;
	
	@FXML
    private TableColumn<Cliente, String> colunaEmail;
	
	@FXML
    private TableColumn<Cliente, String> colunaTelefone;
	
	@FXML
    private TableColumn<Cliente, CheckBox> colunaSelect;
	
	@FXML
	public void atualizaCadastro() {
		Parent parent;
		try {
			parent = FXMLLoader.load(getClass().getResource("/gui/view/ViewAtualizaCliente.fxml"));
			Scene scene = new Scene(parent);
			ViewController.getStageCliente().setScene(scene);
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}
	
	public void carregaCliente() {
		obCliente = FXCollections.observableArrayList(Cadastro.clientes);
        tvCliente.setItems(obCliente);
	}
	
	@FXML
	public void onBtCriaClienteAction(){
		try {
			if(Alerts.showAlertGenerico("Confirmação de Inclusão", "Deseja mesmo adicionar um cliente?", null)) {
				if(txtNomeCliente.getText().isEmpty()) {
					Alerts.showAlert("Aviso", "Falta informações", "Coloco no mínimo: Nome e CPF", AlertType.INFORMATION);
				}
				else {
					if(Salvar.salvarCliente(txtNomeCliente, txtEmailCliente, txtTelefoneCliente, txtRedeSocialCliente) == false) {
						Carregar.carregaCliente();
						carregaCliente();
						ViewController.bindAutoCompleteCliente.dispose();
						ViewController.bindAutoCompleteCliente = TextFields.bindAutoCompletion(ViewController.getTfClienteTemp(), Cadastro.clientes);
						ViewController.getStageCaixa().hide();
						txtNomeCliente.setText("");
						txtEmailCliente.setText("");
						txtTelefoneCliente.setText("");
						txtRedeSocialCliente.setText("");
						Notificacoes.mostraNotificacao("Concluído!", "Cliente criado com sucesso!");
					}
					else {
						Alerts.showAlert("Aviso", "Cliente já adicionado", "Já existe cliente com esse nome"
								+ " no programa ou o cliente não foi excluído no banco de dados\n\n"
								+ "Peça ao ADMINISTRADOR para excluir o "
								+ "registro desse cliente no BANCO ou então coloque um nome mais extenso para ocorrer a diferenciação.", AlertType.INFORMATION);
					}
				}		
			}
			else {
				Alerts.showAlert("Cancelado", "Voc� cancelou a opera��o", "Cliente n�o inclu�do", AlertType.INFORMATION);
				txtNomeCliente.setText("");
				txtEmailCliente.setText("");
				txtTelefoneCliente.setText("");
				txtRedeSocialCliente.setText("");
			}
		}
		catch (NumberFormatException e) {
			Alerts.showAlert("Erro", "Erro de convers�o, cliente n�o ser� criado!", e.getMessage(), AlertType.ERROR);
		}
	}
	
	public void excluirCliente() {
		if(Alerts.showAlertExclusao()) {
			ObservableList<Cliente> obExcluirCliente = FXCollections.observableArrayList();
			
			for(Cliente cli : obCliente) {
				if(cli.getSelect().isSelected()) {
					obExcluirCliente.add(cli);
					Excluir.excluirCliente(cli);
				}
			}
			obCliente.removeAll(obExcluirCliente);
			Cadastro.clientes.removeAll(obExcluirCliente);
			
			ViewController.bindAutoCompleteCliente.dispose();
			ViewController.bindAutoCompleteCliente = TextFields.bindAutoCompletion(ViewController.getTfClienteTemp(), Cadastro.clientes);
			if(ViewController.getStageCaixa().isShowing()) {
				ViewCaixaController.bindAutoCompleteCliente.dispose();
				ViewCaixaController.bindAutoCompleteCliente = TextFields.bindAutoCompletion(ViewCaixaController.tfClienteTemp, Cadastro.clientes);
			}
		}
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		colunaId.setCellValueFactory(new PropertyValueFactory<>("id"));
		colunaNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colunaEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colunaTelefone.setCellValueFactory(new PropertyValueFactory<>("telefone"));
        colunaRedeSocial.setCellValueFactory(new PropertyValueFactory<>("redeSocial"));
        colunaSelect.setCellValueFactory(new PropertyValueFactory<>("select"));
		carregaCliente();
	}
}