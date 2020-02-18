package gui.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import gui.utils.AlertsUtils;
import gui.utils.NotificacoesUtils;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import model.collections.Colecao;
import model.collections.entities.Funcionario;
import model.daos.DaoFuncionario;

public class ViewFuncionarioController implements Initializable {

	private ObservableList<Funcionario> obFuncionario;
	private boolean parada;

	@FXML
	private Button btAtualizaFuncionario;

	@FXML
	private ProgressIndicator piStatus;

	@FXML
	private Button btCriaFuncionario;

	@FXML
	private Button btExcluiFuncionario;

	@FXML
	private TextField txtCpfFuncionario;

	@FXML
	private TextField txtCpfFuncionarioAtualizado;

	@FXML
	private TextField txtNomeFuncionario;

	@FXML
	private TextField txtNomeFuncionarioAtualizado;

	@FXML
	private TextField txtTelefoneFuncionario;

	@FXML
	private TextField txtTelefoneFuncionarioAtualizado;

	@FXML
	private TableView<Funcionario> tvFuncionario = new TableView<>();

	@FXML
	private TableView<Funcionario> tvFuncionarioAtualizado = new TableView<>();

	@FXML
	private TableColumn<Funcionario, String> colunaNome;

	@FXML
	private TableColumn<Funcionario, String> colunaNomeAtualizado;

	@FXML
	private TableColumn<Funcionario, String> colunaCpf;

	@FXML
	private TableColumn<Funcionario, String> colunaCpfAtualizado;

	@FXML
	private TableColumn<Funcionario, String> colunaAtualizado;

	@FXML
	private TableColumn<Funcionario, String> colunaTelefone;

	@FXML
	private TableColumn<Funcionario, String> colunaTelefoneAtualizado;

	@FXML
	private TableColumn<Funcionario, CheckBox> colunaSelect;

	public void carregaFuncionario() {
		obFuncionario = FXCollections.observableArrayList(Colecao.funcionarios);
		tvFuncionario.setItems(obFuncionario);
		tvFuncionarioAtualizado.setItems(obFuncionario);
		tvFuncionario.refresh();
		tvFuncionarioAtualizado.refresh();
	}

	@FXML
	public void onBtCriaFuncionarioAction() {
		parada = true;
		Task<Void> tarefa = new Task<Void>() {
			@Override
			protected Void call() throws Exception {
				while (parada) {
					Thread.sleep(0);
				}
				piStatus.setVisible(false);
				return null;
			}
		};

		try {
			if (AlertsUtils.showAlertGenerico("Confirmação de inclusão", "Deseja mesmo incluir um funcionário?",
					null)) {
				if (txtCpfFuncionario.getText().isEmpty() || txtNomeFuncionario.getText().isEmpty()) {
					AlertsUtils.showAlert("Aviso", "Falta informações", "Coloco no mínimo: CPF e Nome",
							AlertType.INFORMATION);
				} else {
					piStatus.setVisible(true);
					Task<Void> acaoCriaFuncionario = new Task<Void>() {
						@Override
						protected Void call() throws Exception {
							Platform.runLater(() -> {
								Thread t = new Thread(tarefa);
								t.start();
							});
							if (!DaoFuncionario.salvarFuncionario(txtCpfFuncionario, txtTelefoneFuncionario,
									txtNomeFuncionario)) {
								DaoFuncionario.carregaFuncionario();
								DaoFuncionario.carregaAgendaFuncionario(ViewController.getDpDataTemp());

								Platform.runLater(new Runnable() {
									@Override
									public void run() {
										carregaFuncionario();
										ViewController.getTvAgendaTemp().setItems(obFuncionario);
										ViewController.getTvFuncionarioTemp().setItems(obFuncionario);
										txtCpfFuncionario.clear();
										txtNomeFuncionario.clear();
										txtTelefoneFuncionario.clear();
										NotificacoesUtils.mostraNotificacoes("Concluído!",
												"Funcionário criado com sucesso!");
									}
								});
							} else {
								Platform.runLater(new Runnable() {
									@Override
									public void run() {
										AlertsUtils.showAlert("Aviso", "Funcionário já adicionado",
												"Já existe funcionário com esse cpf"
														+ " no programa ou o funcionário não foi excluído no banco de dados\n\n"
														+ "Peça ao ADMINISTRADOR para excluir o "
														+ "registro desse funcionário no BANCO ou então cpf diferente para ocorrer a diferenciação.",
												AlertType.INFORMATION);
									}
								});
							}
							parada = false;
							return null;
						}
					};

					Platform.runLater(() -> {
						ViewController.getStageCaixa().hide();
						ViewController.getStageCliente().hide();
						ViewController.getStagePagamento().hide();
						Thread t = new Thread(acaoCriaFuncionario);
						t.start();
					});
				}
			} else {
				AlertsUtils.showAlert("Cancelado", "Você cancelou a operação", "Funcionário não incluído",
						AlertType.INFORMATION);
				txtCpfFuncionario.clear();
				txtNomeFuncionario.clear();
				txtTelefoneFuncionario.clear();
			}
		} catch (NumberFormatException e) {
			AlertsUtils.showAlert("Error", "Parse error", e.getMessage(), AlertType.ERROR);
		}
	}

	public void excluirFuncionario() {
		if (AlertsUtils.showAlertExclusao()) {
			parada = true;
			Task<Void> tarefa = new Task<Void>() {
				@Override
				protected Void call() throws Exception {
					while (parada) {
						Thread.sleep(0);
					}
					piStatus.setVisible(false);
					return null;
				}
			};

			Task<Void> acaoExcluirFuncionario = new Task<Void>() {

				@Override
				protected Void call() throws Exception {
					Platform.runLater(() -> {
						piStatus.setVisible(true);
						Thread t = new Thread(tarefa);
						t.start();
					});

					for (Funcionario fun : obFuncionario) {
						if (fun.getSelect().isSelected()) {
							DaoFuncionario.excluirFuncionario(fun);
						}
					}
					DaoFuncionario.carregaFuncionario();
					DaoFuncionario.carregaAgendaFuncionario(ViewController.getDpDataTemp());

					Platform.runLater(new Runnable() {
						@Override
						public void run() {
							carregaFuncionario();
							ViewController.getTvAgendaTemp().setItems(obFuncionario);
							ViewController.getTvFuncionarioTemp().setItems(obFuncionario);
							NotificacoesUtils.mostraNotificacoes("Concluído!", "Funcionário excluído com sucesso!");
						}
					});
					parada = false;
					return null;
				}
			};

			Platform.runLater(() -> {
				ViewController.getStageCaixa().hide();
				ViewController.getStageCliente().hide();
				ViewController.getStagePagamento().hide();
				Thread t = new Thread(acaoExcluirFuncionario);
				t.start();
			});

		} else {
			AlertsUtils.showAlert("Cancelado", "Você cancelou a operação", "Funcionário não excluído",
					AlertType.INFORMATION);
		}
	}

	@FXML
	public void selecionaFuncionario() {
		Funcionario fun = tvFuncionarioAtualizado.getSelectionModel().getSelectedItem();
		if (fun != null) {
			txtCpfFuncionarioAtualizado.setText(fun.getCpf());
			txtTelefoneFuncionarioAtualizado.setText(fun.getTelefone());
			txtCpfFuncionarioAtualizado.setDisable(true);
			txtNomeFuncionarioAtualizado.setText(fun.getNome());
		}
	}

	@FXML
	public void onAtualizaFuncionarioAction() {
		if (AlertsUtils.showAlertAtualizacao()) {
			String cpf = txtCpfFuncionarioAtualizado.getText();
			String nome = txtNomeFuncionarioAtualizado.getText();
			String telefone = txtTelefoneFuncionarioAtualizado.getText();
			if (cpf.isEmpty() || nome.isEmpty()) {
				NotificacoesUtils.mostraNotificacoes("Atenção!", "Campos de nome ou cpf estão vazios!");
			} else {
				parada = true;
				Task<Void> tarefa = new Task<Void>() {
					@Override
					protected Void call() throws Exception {
						while (parada) {
							Thread.sleep(0);
						}
						piStatus.setVisible(false);
						return null;
					}
				};
				piStatus.setVisible(true);

				Task<Void> acaoAtualizaFuncionario = new Task<Void>() {

					@Override
					protected Void call() throws Exception {
						Platform.runLater(() -> {
							Thread t = new Thread(tarefa);
							t.start();
						});

						DaoFuncionario.atualizarFuncionario(nome, telefone, cpf);
						DaoFuncionario.carregaFuncionario();
						DaoFuncionario.carregaAgendaFuncionario(ViewController.getDpDataTemp());

						Platform.runLater(new Runnable() {
							@Override
							public void run() {
								carregaFuncionario();
								ViewController.getTvAgendaTemp().setItems(obFuncionario);
								ViewController.getTvFuncionarioTemp().setItems(obFuncionario);
								ViewController.getTvAgendaTemp().refresh();
								ViewController.getTvFuncionarioTemp().refresh();
								NotificacoesUtils.mostraNotificacoes("Concluído!",
										"Funcionário atualizado com sucesso!");
								txtCpfFuncionarioAtualizado.clear();
								txtTelefoneFuncionarioAtualizado.clear();
								txtNomeFuncionarioAtualizado.clear();
							}
						});
						parada = false;
						return null;
					}
				};

				Platform.runLater(() -> {
					ViewController.getStageCaixa().hide();
					ViewController.getStageCliente().hide();
					ViewController.getStagePagamento().hide();
					Thread t = new Thread(acaoAtualizaFuncionario);
					t.start();
				});
			}
		} else {
			NotificacoesUtils.mostraNotificacoes("Operação cancelada!", "Funcionário não foi atualizado!");
		}
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		colunaNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
		colunaCpf.setCellValueFactory(new PropertyValueFactory<>("cpf"));
		colunaTelefone.setCellValueFactory(new PropertyValueFactory<>("telefone"));
		colunaSelect.setCellValueFactory(new PropertyValueFactory<>("select"));

		colunaNomeAtualizado.setCellValueFactory(new PropertyValueFactory<>("nome"));
		colunaCpfAtualizado.setCellValueFactory(new PropertyValueFactory<>("cpf"));
		colunaTelefoneAtualizado.setCellValueFactory(new PropertyValueFactory<>("telefone"));

		carregaFuncionario();
	}
}
