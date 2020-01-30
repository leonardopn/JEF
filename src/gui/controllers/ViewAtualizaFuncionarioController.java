package gui.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import gui.util.Alerts;
import gui.util.Notificacoes;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import model.collection.Colecao;
import model.collection.entities.Funcionario;
import model.dao.DaoFuncionario;

public class ViewAtualizaFuncionarioController implements Initializable{
	
	ObservableList<Funcionario> obFuncionario;
	boolean parada;
	
	@FXML
	private Button btAtualiza;
	
	@FXML
	private ProgressIndicator piStatus;
	
	@FXML
	private Label labelStatus;
	
	@FXML
	private Button btVoltar;
	
	@FXML
	private TextField txtCpfFuncionario;
	
	@FXML
	private TextField txtTelefoneFuncionario;
	
	@FXML
	private TextField txtNomeFuncionario;
	
	@FXML
	private TableView<Funcionario> tvFuncionario = new TableView<>();
	
	@FXML
	private TableColumn<Funcionario, String> colunaNome;
	
	@FXML
    private TableColumn<Funcionario, String> colunaCpf;
	
	@FXML
	private TableColumn<Funcionario, String> colunaTelefone;
	
	@FXML
	public void selecionaFuncionario() {
		Funcionario fun = tvFuncionario.getSelectionModel().getSelectedItem();
		if(fun != null) {
			txtCpfFuncionario.setText(fun.getCpf());
			txtTelefoneFuncionario.setText(fun.getTelefone());
			txtCpfFuncionario.setDisable(true);
			txtNomeFuncionario.setText(fun.getNome());
		}	
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
		obFuncionario = FXCollections.observableArrayList(Colecao.funcionarios);
        tvFuncionario.setItems(obFuncionario);
        tvFuncionario.refresh();
	}
	
	@FXML
	public void onAtualizaFuncionarioAction() {
		if(Alerts.showAlertAtualizacao()) {
			String cpf = txtCpfFuncionario.getText();
			String nome = txtNomeFuncionario.getText();
			String telefone = txtTelefoneFuncionario.getText();
			if(cpf.isEmpty() || nome.isEmpty()) {
				Notificacoes.mostraNotificacao("Atenção!", "Campos de nome ou cpf estão vazios!");
			}
			else {
				parada = true;
				Task<Void> tarefa = new Task<Void>() {
					@Override
					protected Void call() throws Exception {
						while (parada) {
							Thread.sleep(0);
						}
						piStatus.setVisible(false);
						labelStatus.setVisible(false);
						return null;
					}
				};
				piStatus.setVisible(true);
				labelStatus.setVisible(true);	
							
				Task<Void> acaoAtualizaFuncionario = new Task<Void>() {

					@Override
					protected Void call() throws Exception {
						javafx.application.Platform.runLater(() -> {
							Thread t = new Thread(tarefa);
							t.start();
						});
						
						DaoFuncionario.atualizarFuncionario(nome, telefone, cpf);
						DaoFuncionario.carregaFuncionario();
						DaoFuncionario.carregaAgendaFuncionario(ViewController.getDpDataTemp());
						carregaFuncionario();
						
						Platform.runLater(new Runnable() {
							@Override
							public void run() {
								obFuncionario = FXCollections.observableArrayList(Colecao.funcionarios);
								ViewController.getTvAgendaTemp().setItems(obFuncionario);
								ViewController.getTvFuncionarioTemp().setItems(obFuncionario);
								ViewController.getTvAgendaTemp().refresh();
								ViewController.getTvFuncionarioTemp().refresh();
								Notificacoes.mostraNotificacao("Concluído!", "Funcionário atualizado com sucesso!");
							}
						});
						parada = false;
						return null;
					}
				};
				
				javafx.application.Platform.runLater(() -> {
					ViewController.getStageCaixa().hide();
					ViewController.getStageCliente().hide();
					ViewController.getStagePagamento().hide();
					Thread t = new Thread(acaoAtualizaFuncionario);
					t.start();
				});
			}
		}
		else {
			Notificacoes.mostraNotificacao("Operação cancelada!", "Funcionário não foi atualizado!");
		}
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		colunaNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
		colunaCpf.setCellValueFactory(new PropertyValueFactory<>("cpf"));
		colunaTelefone.setCellValueFactory(new PropertyValueFactory<>("telefone"));
		carregaFuncionario();
	}
}
