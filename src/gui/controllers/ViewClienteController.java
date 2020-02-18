package gui.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import org.controlsfx.control.textfield.TextFields;

import gui.utils.AlertsUtils;
import gui.utils.NotificacoesUtils;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import model.collections.Colecao;
import model.collections.entities.Cliente;
import model.daos.DaoCliente;
import model.daos.DaoFuncionario;

public class ViewClienteController implements Initializable {

	private ObservableList<Cliente> obCliente;
	private ArrayList<Cliente> clientesTemp;

	private boolean parada;

	@FXML
	private Button btCriaCliente;

	@FXML
	private Tab tabTabela;
	
	@FXML
	private Tab tabReplace;

	@FXML
	private Tab tabCliente;

	@FXML
	private TabPane tpCentral;

	@FXML
	private AnchorPane apCentral;

	@FXML
	private RadioButton rbId;

	@FXML
	private RadioButton rbNome;

	@FXML
	private RadioButton rbRedeSocial;

	@FXML
	private RadioButton rbEmail;

	@FXML
	private RadioButton rbTelefone;

	@FXML
	private RadioButton rbTodos;

	@FXML
	private TextField tfBusca;

	@FXML
	private Button btPesquisar;
	
	@FXML
	private Button btLimpar;

	@FXML
	private Button btAtualizaCliente;

	@FXML
	private Button btExcluiCliente;

	@FXML
	private TextField txtNomeCliente;
	
	@FXML
	private TextField txtNomeClienteAtualizacao;

	@FXML
	private TextField txtRedeSocialCliente;
	
	@FXML
	private TextField txtRedeSocialClienteAtualizacao;

	@FXML
	private TextField txtEmailCliente;
	
	@FXML
	private TextField txtEmailClienteAtualizacao;

	@FXML
	private TextField txtTelefoneCliente;
	
	@FXML
	private TextField txtTelefoneClienteAtualizacao;
	
	@FXML
	private TextField txtIdCliente;

	@FXML
	private TableView<Cliente> tvCliente = new TableView<>();
	
	@FXML
	private TableView<Cliente> tvClienteAtualizacao = new TableView<>();

	@FXML
	private TableColumn<Cliente, String> colunaNome;
	
	@FXML
	private TableColumn<Cliente, String> colunaNomeAtualizacao;

	@FXML
	private TableColumn<Cliente, Integer> colunaId;
	
	@FXML
	private TableColumn<Cliente, Integer> colunaIdAtualizacao;

	@FXML
	private TableColumn<Cliente, String> colunaRedeSocial;
	
	@FXML
	private TableColumn<Cliente, String> colunaRedeSocialAtualizacao;

	@FXML
	private TableColumn<Cliente, String> colunaEmail;
	
	@FXML
	private TableColumn<Cliente, String> colunaEmailAtualizacao;

	@FXML
	private TableColumn<Cliente, String> colunaTelefone;
	
	@FXML
	private TableColumn<Cliente, String> colunaTelefoneAtualizacao;

	@FXML
	private TableColumn<Cliente, CheckBox> colunaSelect;

	@FXML
	private ProgressIndicator piStatus;

	@FXML
	public void atualizaCadastro() {
		Parent parent;
		try {
			parent = FXMLLoader.load(getClass().getResource("/gui/views/ViewAtualizaCliente.fxml"));
			Scene scene = new Scene(parent);
			ViewController.getStageCliente().setScene(scene);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void carregaCliente() {
		obCliente = FXCollections.observableArrayList(Colecao.clientes);
		tvCliente.setItems(obCliente);
		tvClienteAtualizacao.setItems(obCliente);
		tvClienteAtualizacao.refresh();
		tvCliente.refresh();
	}
	
	@FXML
	public void selecionaCliente() {
		if(tvClienteAtualizacao.getSelectionModel().getSelectedItem() != null) {
			Cliente cli = tvClienteAtualizacao.getSelectionModel().getSelectedItem();
			txtIdCliente.setText(String.valueOf(cli.getId()));
			txtNomeClienteAtualizacao.setText(cli.getNome());
			txtEmailClienteAtualizacao.setText(cli.getEmail());
			txtTelefoneClienteAtualizacao.setText(cli.getTelefone());
			txtRedeSocialClienteAtualizacao.setText(cli.getRedeSocial());
		}
	}
	
	@FXML
	public void atualizaCliente() {
		if (AlertsUtils.showAlertAtualizacao()) {
			String nome = txtNomeClienteAtualizacao.getText();
			String email = txtEmailClienteAtualizacao.getText();
			String telefone = txtTelefoneClienteAtualizacao.getText();
			String redeSocial = txtRedeSocialClienteAtualizacao.getText();
			int id = Integer.parseInt(txtIdCliente.getText());

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

			Task<Void> acaoAtualizaCliente = new Task<Void>() {
				@Override
				protected Void call() throws Exception {
					Platform.runLater(() -> {
						Thread t = new Thread(tarefa);
						t.start();
					});
					if (!DaoCliente.atualizarCliente(id, nome, email, telefone, redeSocial)) {
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
								NotificacoesUtils.mostraNotificacoes("Concluído!", "Cliente atualizado com sucesso!");
								limpaCampos();
							}
						});
					} else {
						Platform.runLater(new Runnable() {
							@Override
							public void run() {
								AlertsUtils.showAlert("Aviso", "Cliente já adicionado", "Já existe cliente com esse nome"
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

			Platform.runLater(() -> {
				ViewController.getStageCaixa().hide();
				Thread t = new Thread(acaoAtualizaCliente);
				t.start();
			});
		} else {
			NotificacoesUtils.mostraNotificacoes("Operação cancelado!", "Cliente não foi atualizado!");
		}
	}

	@FXML
	public void onBtCriaClienteAction() {
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
			if (AlertsUtils.showAlertGenerico("Confirmação de Inclusão", "Deseja mesmo adicionar um cliente?", null)) {
				if (txtNomeCliente.getText().isEmpty()) {
					AlertsUtils.showAlert("Aviso", "Falta informações", "Coloco no mínimo: Nome", AlertType.INFORMATION);
				} else {
					piStatus.setVisible(true);
					Task<Void> acaoCriarCliente = new Task<Void>() {
						@Override
						protected Void call() throws Exception {
							Platform.runLater(() -> {
								Thread t = new Thread(tarefa);
								t.start();
							});

							if (!DaoCliente.salvarCliente(txtNomeCliente, txtEmailCliente, txtTelefoneCliente,
									txtRedeSocialCliente)) {
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
										NotificacoesUtils.mostraNotificacoes("Concluído!", "Cliente criado com sucesso!");
									}
								});
							} else {
								parada = false;
								Platform.runLater(new Runnable() {
									@Override
									public void run() {
										AlertsUtils.showAlert("Aviso", "Cliente já adicionado",
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

					Platform.runLater(() -> {
						ViewController.getStageCaixa().hide();
						ViewController.getStageFuncionario().hide();
						ViewController.getStagePagamento().hide();
						Thread t = new Thread(acaoCriarCliente);
						t.start();
					});
				}
			} else {
				AlertsUtils.showAlert("Cancelado", "Você cancelou a operação", "Cliente não incluído",
						AlertType.INFORMATION);
				limpaCampos();
			}
		} catch (NumberFormatException e) {
			AlertsUtils.showAlert("Erro", "Erro de conversão, cliente não será criado!", e.getMessage(), AlertType.ERROR);
		}
	}

	public void excluirCliente() {
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

		if (AlertsUtils.showAlertExclusao()) {
			piStatus.setVisible(true);
			Task<Void> acaoExcluirCliente = new Task<Void>() {
				@Override
				protected Void call() throws Exception {
					Platform.runLater(() -> {
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
							NotificacoesUtils.mostraNotificacoes("Concluído!", "Cliente excluído com sucesso!");
							
						}
					});
					return null;
				}
			};

			Platform.runLater(() -> {
				Thread t = new Thread(acaoExcluirCliente);
				t.start();
			});

		} else {
			AlertsUtils.showAlert("Cancelado", "Você cancelou a operação", "Cliente não excluído", AlertType.INFORMATION);
		}
	}
	
	public void limpaCampos() {
		txtEmailClienteAtualizacao.setText("");
		txtNomeClienteAtualizacao.setText("");
		txtIdCliente.setText("");
		txtRedeSocialClienteAtualizacao.setText("");
		txtTelefoneClienteAtualizacao.setText("");
	}
	
	public void setaRadioGrups() {
		ToggleGroup group1 = new ToggleGroup();
		rbTodos.setToggleGroup(group1 );
		rbEmail.setToggleGroup(group1);
		rbId.setToggleGroup(group1);
		rbNome.setToggleGroup(group1);
		rbRedeSocial.setToggleGroup(group1);
		rbTelefone.setToggleGroup(group1);
	}
	
	public void buscaOperacoes() {
		int grupo;
		piStatus.setVisible(true);

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

		if (rbTodos.isSelected()) {
			grupo = 1;
		} else {
			if (rbId.isSelected()) {
				grupo = 2;
			} else {
				if (rbNome.isSelected()) {
					grupo = 3;
				} else {
					if (rbEmail.isSelected()) {
						grupo = 4;
					}
					else {
						if(rbTelefone.isSelected()) {
							grupo = 5;
						}
						else {
							grupo = 6;
						}
					}
				}
			}
		}

		Task<Void> taskBuscaCliente = new Task<Void>() {
			@Override
			protected Void call() throws Exception {
				parada = true;
				Platform.runLater(() -> {
					Thread t = new Thread(tarefa);
					t.start();
				});
				
				clientesTemp = DaoCliente.buscaCliente(tfBusca.getText(), grupo);
					
				Platform.runLater(new Runnable() {
					@Override
					public void run() {
						obCliente = FXCollections.observableArrayList(clientesTemp);
						tvCliente.setItems(obCliente);
						tvCliente.refresh();
					}
				});

				parada = false;
				return null;
			}
		};

		Platform.runLater(() -> {
			Thread t = new Thread(taskBuscaCliente);
			t.start();
		});
	}
	
	public void eventTab() {
		EventHandler<Event> eventResize = new EventHandler<Event>() {
            @Override
            public void handle(Event t) {
                if (tabCliente.isSelected()) {
                	ViewController.getStageCliente().setWidth(460.0);
                	ViewController.getStageCliente().setHeight(508.0);
                	ViewController.getStageCliente().centerOnScreen();
                }
                else {
                	if(tabTabela.isSelected()) {
                		ViewController.getStageCliente().setWidth(880.0);
                    	ViewController.getStageCliente().setHeight(690.0);
                    	ViewController.getStageCliente().centerOnScreen();
                	}
                	else {
                		ViewController.getStageCliente().setWidth(880.0);
                    	ViewController.getStageCliente().setHeight(730.0);
                    	ViewController.getStageCliente().centerOnScreen();
                	}
                }
            }
        };
		
		tabCliente.setOnSelectionChanged(eventResize);
		tabTabela.setOnSelectionChanged(eventResize);
		tabReplace.setOnSelectionChanged(eventResize);
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		colunaId.setCellValueFactory(new PropertyValueFactory<>("id"));
		colunaNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
		colunaEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
		colunaTelefone.setCellValueFactory(new PropertyValueFactory<>("telefone"));
		colunaRedeSocial.setCellValueFactory(new PropertyValueFactory<>("redeSocial"));
		colunaSelect.setCellValueFactory(new PropertyValueFactory<>("select"));
		
		colunaIdAtualizacao.setCellValueFactory(new PropertyValueFactory<>("id"));
		colunaNomeAtualizacao.setCellValueFactory(new PropertyValueFactory<>("nome"));
		colunaEmailAtualizacao.setCellValueFactory(new PropertyValueFactory<>("email"));
		colunaTelefoneAtualizacao.setCellValueFactory(new PropertyValueFactory<>("telefone"));
		colunaRedeSocialAtualizacao.setCellValueFactory(new PropertyValueFactory<>("redeSocial"));
		
		carregaCliente();
		
		setaRadioGrups();
		
		eventTab();
		
	}

}