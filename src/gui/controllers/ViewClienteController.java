package gui.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import org.controlsfx.control.textfield.TextFields;

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
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import model.collection.Colecao;
import model.collection.entities.Cliente;
import model.dao.DaoCliente;

public class ViewClienteController implements Initializable {

	ObservableList<Cliente> obCliente;

	boolean parada;

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
	private Label labelStatus;

	@FXML
	private ProgressIndicator piStatus;

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
		obCliente = FXCollections.observableArrayList(Colecao.clientes);
		tvCliente.setItems(obCliente);
		tvCliente.refresh();
	}

	@FXML
	public void onBtCriaClienteAction() {
		parada = true;
		Task<Void> tarefa = new Task<Void>() {
			@Override
			protected Void call() throws Exception {
				while (parada == true) {
					Thread.sleep(0);
				}
				piStatus.setVisible(false);
				labelStatus.setVisible(false);
				return null;
			}
		};

		try {
			if (Alerts.showAlertGenerico("Confirmação de Inclusão", "Deseja mesmo adicionar um cliente?", null)) {
				if (txtNomeCliente.getText().isEmpty()) {
					Alerts.showAlert("Aviso", "Falta informações", "Coloco no mínimo: Nome", AlertType.INFORMATION);
				} else {
					piStatus.setVisible(true);
					labelStatus.setVisible(true);
					labelStatus.setText("Criando cliente!");
					Task<Void> acaoCriarCliente = new Task<Void>() {
						@Override
						protected Void call() throws Exception {
							javafx.application.Platform.runLater(() -> {
								Thread t = new Thread(tarefa);
								t.start();
							});

							if (DaoCliente.salvarCliente(txtNomeCliente, txtEmailCliente, txtTelefoneCliente,
									txtRedeSocialCliente) == false) {
								DaoCliente.carregaCliente();
								carregaCliente();
								parada = false;
								Platform.runLater(new Runnable() {
									@Override
									public void run() {
										ViewController.bindAutoCompleteCliente.dispose();
										ViewController.bindAutoCompleteCliente = TextFields.bindAutoCompletion(
												ViewController.getTfClienteTemp(), Colecao.clientes);
										txtNomeCliente.setText("");
										txtEmailCliente.setText("");
										txtTelefoneCliente.setText("");
										txtRedeSocialCliente.setText("");
										Notificacoes.mostraNotificacao("Concluído!", "Cliente criado com sucesso!");
									}
								});
							} else {
								parada = false;
								Platform.runLater(new Runnable() {
									@Override
									public void run() {
										Alerts.showAlert("Aviso", "Cliente já adicionado",
												"Já existe cliente com esse nome"
														+ " no programa ou o cliente não foi excluído no banco de dados\n\n"
														+ "Peça ao ADMINISTRADOR para excluir o "
														+ "registro desse cliente no BANCO ou então coloque um nome mais extenso para ocorrer a diferenciação.",
												AlertType.INFORMATION);
									}
								});
							}
							return null;
						}
					};

					javafx.application.Platform.runLater(() -> {
						ViewController.getStageCaixa().hide();
						ViewController.getStageFuncionario().hide();
						ViewController.getStagePagamento().hide();
						Thread t = new Thread(acaoCriarCliente);
						t.start();
					});
				}
			} else {
				Alerts.showAlert("Cancelado", "Você cancelou a operação", "Cliente não incluído",
						AlertType.INFORMATION);
				txtNomeCliente.setText("");
				txtEmailCliente.setText("");
				txtTelefoneCliente.setText("");
				txtRedeSocialCliente.setText("");
			}
		} catch (NumberFormatException e) {
			Alerts.showAlert("Erro", "Erro de conversão, cliente não será criado!", e.getMessage(), AlertType.ERROR);
		}
	}

	public void excluirCliente() {
		parada = true;
		Task<Void> tarefa = new Task<Void>() {
			@Override
			protected Void call() throws Exception {
				while (parada == true) {
					Thread.sleep(0);
				}
				piStatus.setVisible(false);
				labelStatus.setVisible(false);
				return null;
			}
		};
		
		if (Alerts.showAlertExclusao()) {
			piStatus.setVisible(true);
			labelStatus.setVisible(true);
			labelStatus.setText("Excluíndo cliente!");
			Task<Void> acaoExcluirCliente = new Task<Void>() {
				@Override
				protected Void call() throws Exception {
					javafx.application.Platform.runLater(() -> {
						Thread t = new Thread(tarefa);
						t.start();
					});
					
					for (Cliente cli : obCliente) {
						if (cli.getSelect().isSelected()) {
							DaoCliente.excluirCliente(cli);
						}
					}
					DaoCliente.carregaCliente();
					carregaCliente();
					parada = false;
					Platform.runLater(new Runnable() {
						@Override
						public void run() {
							ViewController.bindAutoCompleteCliente.dispose();
							ViewController.bindAutoCompleteCliente = TextFields
									.bindAutoCompletion(ViewController.getTfClienteTemp(), Colecao.clientes);
							ViewController.getStageCaixa().hide();
							Notificacoes.mostraNotificacao("Concluído!", "Cliente excluído com sucesso!");
						}
					});
					return null;
				}
			};
			
			javafx.application.Platform.runLater(() -> {
				Thread t = new Thread(acaoExcluirCliente);
				t.start();
			});
			
		} else {
			Alerts.showAlert("Cancelado", "Você cancelou a operação", "Cliente não excluído", AlertType.INFORMATION);
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