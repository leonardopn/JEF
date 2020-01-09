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
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import model.collection.Colecao;
import model.collection.entities.Cliente;
import model.dao.DaoCliente;
import model.dao.DaoFuncionario;

public class ViewAtualizaClienteController implements Initializable {

	ObservableList<Cliente> obCliente;
	ObservableList<Cliente> obClienteExcluido;
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
	private TextField txtIdCliente;

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
	private TableColumn<Cliente, Integer> colunaId;

	public void carregaCliente() {
		obCliente = FXCollections.observableArrayList(Colecao.clientes);
		tvCliente.setItems(obCliente);
	}

	public void voltaScene() {
		try {
			Parent fxmlCliente = FXMLLoader.load(getClass().getResource("/gui/view/ViewCliente.fxml"));
			Scene Cliente = new Scene(fxmlCliente);
			ViewController.getStageCliente().setScene(Cliente);
			ViewController.getStageCliente().centerOnScreen();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@FXML
	public void selecionaCliente() {
		Cliente cli = tvCliente.getSelectionModel().getSelectedItem();
		txtIdCliente.setText(String.valueOf(cli.getId()));
		txtIdCliente.setDisable(true);
		txtNomeCliente.setText(cli.getNome());
		txtEmailCliente.setText(cli.getEmail());
		txtTelefoneCliente.setText(cli.getTelefone());
		txtRedeSocialCliente.setText(cli.getRedeSocial());
	}

	@FXML
	public void atualizaCliente() {
		if (Alerts.showAlertAtualizacao()) {
			String nome = txtNomeCliente.getText();
			String email = txtEmailCliente.getText();
			String telefone = txtTelefoneCliente.getText();
			String redeSocial = txtRedeSocialCliente.getText();
			int id = Integer.parseInt(txtIdCliente.getText());

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
			piStatus.setVisible(true);
			labelStatus.setVisible(true);

			Task<Void> acaoAtualizaCliente = new Task<Void>() {
				@Override
				protected Void call() throws Exception {
					javafx.application.Platform.runLater(() -> {
						Thread t = new Thread(tarefa);
						t.start();
					});
					if (DaoCliente.atualizarCliente(id, nome, email, telefone, redeSocial) == false) {
						DaoCliente.carregaCliente();
						carregaCliente();
						DaoFuncionario.carregaAgendaFuncionario(ViewController.getDpDataTemp());
						Platform.runLater(new Runnable() {
							@Override
							public void run() {
								ViewController.bindAutoCompleteCliente.dispose();
								ViewController.bindAutoCompleteCliente = TextFields
										.bindAutoCompletion(ViewController.getTfClienteTemp(), Colecao.clientes);
								ViewController.getTvAgendaTemp().refresh();
								Notificacoes.mostraNotificacao("Concluído!", "Cliente atualizado com sucesso!");
							}
						});
					} else {
						Platform.runLater(new Runnable() {
							@Override
							public void run() {
								Alerts.showAlert("Aviso", "Cliente já adicionado", "Já existe cliente com esse nome"
										+ " no programa ou o cliente não foi excluído no banco de dados\n\n"
										+ "Peça ao ADMINISTRADOR para excluir o "
										+ "registro desse cliente no BANCO ou então coloque um nome mais extenso para ocorrer a diferenciação.",
										AlertType.INFORMATION);
							}
						});
					}
					parada = false;
					return null;
				}
			};

			javafx.application.Platform.runLater(() -> {
				ViewController.getStageCaixa().hide();
				Thread t = new Thread(acaoAtualizaCliente);
				t.start();
			});
		} else {
			Notificacoes.mostraNotificacao("Operação cancelado!", "Cliente não foi atualizado!");
		}
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		colunaId.setCellValueFactory(new PropertyValueFactory<>("id"));
		colunaNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
		carregaCliente();
	}

}
