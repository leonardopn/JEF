package gui.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

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
import model.collection.Colecao;
import model.collection.entities.Funcionario;
import model.dao.DaoFuncionario;

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
	private TextField txtTelefoneFuncionario;
	
	@FXML
	private TableView<Funcionario> tvFuncionario = new TableView<>();
	
	@FXML
	private TableColumn<Funcionario, String> colunaNome;
	
	@FXML
    private TableColumn<Funcionario, String> colunaCpf;
	
	@FXML
	private TableColumn<Funcionario, String> colunaTelefone;
	
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
		obFuncionario = FXCollections.observableArrayList(Colecao.funcionarios);
        tvFuncionario.setItems(obFuncionario);
        tvFuncionario.refresh();
	}

	@FXML
	public void onBtCriaFuncionarioAction(){
		try {
			if(Alerts.showAlertGenerico("Confirmação de inclusão", "Deseja mesmo incluir um funcionário?", null)) {
				if(txtCpfFuncionario.getText().isEmpty() || txtNomeFuncionario.getText().isEmpty()) {
					Alerts.showAlert("Aviso", "Falta informações", "Coloco no mínimo: CPF e Nome", AlertType.INFORMATION);
				}
				else {
					if(DaoFuncionario.salvarFuncionario(txtCpfFuncionario, txtTelefoneFuncionario, txtNomeFuncionario) == false) {
						DaoFuncionario.carregaFuncionario();
						DaoFuncionario.carregaAgendaFuncionario(ViewController.getDpDataTemp());
						carregaFuncionario();
						obFuncionario = FXCollections.observableArrayList(Colecao.funcionarios);
				    	ViewController.getTvAgendaTemp().setItems(obFuncionario);
						ViewController.getTvFuncionarioTemp().setItems(obFuncionario);
						ViewController.getStageCaixa().hide();
						ViewController.getStageCliente().hide();
						ViewController.getStagePagamento().hide();
						txtCpfFuncionario.setText("");
						txtNomeFuncionario.setText("");
						txtTelefoneFuncionario.setText("");
						Notificacoes.mostraNotificacao("Concluído!", "Funcionário criado com sucesso!");
					}
					else {
						Alerts.showAlert("Aviso", "Funcionário já adicionado", "Já existe funcionário com esse cpf"
								+ " no programa ou o funcionário não foi excluído no banco de dados\n\n"
								+ "Peça ao ADMINISTRADOR para excluir o "
								+ "registro desse funcionário no BANCO ou então cpf diferente para ocorrer a diferenciação.", AlertType.INFORMATION);
					}
				}
			}
			else {
				Alerts.showAlert("Cancelado", "Você cancelou a operação", "Funcionário não incluído", AlertType.INFORMATION);
				txtCpfFuncionario.setText("");
				txtNomeFuncionario.setText("");
				txtTelefoneFuncionario.setText("");
			}
		}
		catch (NumberFormatException e) {
			Alerts.showAlert("Error", "Parse error", e.getMessage(), AlertType.ERROR);
		}
	}
	
	public void excluirFuncionario() {
		if(Alerts.showAlertExclusao()) {		
			for(Funcionario fun : obFuncionario) {
				if(fun.getSelect().isSelected()) {
					DaoFuncionario.excluirFuncionario(fun);
				}
			}
			DaoFuncionario.carregaFuncionario();
			DaoFuncionario.carregaAgendaFuncionario(ViewController.getDpDataTemp());
			carregaFuncionario();
			//vai atulizar no stage main se excluir funcion�rios
			obFuncionario = FXCollections.observableArrayList(Colecao.funcionarios);
    		ViewController.getTvAgendaTemp().setItems(obFuncionario);
			ViewController.getTvFuncionarioTemp().setItems(obFuncionario);
			ViewController.getStageCaixa().hide();
			ViewController.getStageCliente().hide();
			ViewController.getStagePagamento().hide();
			Notificacoes.mostraNotificacao("Concluído!", "Funcionário excluído com sucesso!");
		}
		else {
			Alerts.showAlert("Cancelado", "Você cancelou a operação", "Funcionário não excluído", AlertType.INFORMATION);
		}
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		colunaNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colunaCpf.setCellValueFactory(new PropertyValueFactory<>("cpf"));
        colunaTelefone.setCellValueFactory(new PropertyValueFactory<>("telefone"));
        colunaSelect.setCellValueFactory(new PropertyValueFactory<>("select"));
		carregaFuncionario();
	}
}
