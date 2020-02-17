package gui.controllers;

import java.net.URL;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

import org.controlsfx.control.textfield.AutoCompletionBinding;
import org.controlsfx.control.textfield.TextFields;

import com.jfoenix.controls.JFXComboBox;

import gui.utils.AlertsUtils;
import gui.utils.NotificacoesUtils;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import model.collections.Colecao;
import model.collections.entities.Cliente;
import model.collections.entities.Pacote;
import model.collections.entities.PacoteAssociado;
import model.daos.DaoOperacao;
import model.daos.DaoPacote;
import model.daos.DaoTransacao;

public class ViewPacoteController implements Initializable {

	private ObservableList<Pacote> obPacote;
	private static AutoCompletionBinding<Cliente> bindAutoCompleteCliente;
	private boolean parada;

	@FXML
	private TableView<Pacote> tvPacotes;

	@FXML
	private TableColumn<Pacote, Integer> colunaId;

	@FXML
	private TableColumn<Pacote, String> colunaPacote;

	@FXML
	private TableColumn<Pacote, Double> colunaValor;

	@FXML
	private TableColumn<Pacote, Integer> colunaQuantMao;

	@FXML
	private TableColumn<Pacote, Integer> colunaQuantPe;

	@FXML
	private TableColumn<Pacote, Double> colunaValorMao;

	@FXML
	private TableColumn<Pacote, Double> colunaValorPe;

	@FXML
	private TableColumn<Pacote, CheckBox> colunaExcluirPacote;

	@FXML
	private Button btAddPacote;

	@FXML
	private Button btExcluiPacote;

	@FXML
	private Button btAtualizaPacote;

	@FXML
	private Button btAddAssociacao;

	@FXML
	private Button btExcluiAssociacao;

	@FXML
	private TextField tfNome;

	@FXML
	private TextField tfValor;

	@FXML
	private TextField tfIdPacote;

	@FXML
	private TextField tfValorMao;

	@FXML
	private TextField tfQuantMao;

	@FXML
	private TextField tfQuantPe;

	@FXML
	private TextField tfValorPe;

	@FXML
	private TextField tfNomeAtualizado;

	@FXML
	private TextField tfValorAtualizado;

	@FXML
	private TextField tfValorMaoAtualizado;

	@FXML
	private TextField tfValorPeAtualizado;

	@FXML
	private TextField tfQuantPeAtualizado;

	@FXML
	private TextField tfQuantMaoAtualizado;

	@FXML
	private TableView<PacoteAssociado> tvPacotesAssociados;

	@FXML
	private TableColumn<PacoteAssociado, Integer> colunaIdPacoteAssociado;

	@FXML
	private TableColumn<PacoteAssociado, String> colunaCliente;

	@FXML
	private TableColumn<PacoteAssociado, String> colunaPacoteAssociado;

	@FXML
	private TableColumn<PacoteAssociado, Integer> colunaQuantMaoAssociado;

	@FXML
	private TableColumn<PacoteAssociado, Integer> colunaQuantPeAssociado;

	@FXML
	private TableColumn<PacoteAssociado, CheckBox> colunaExcluirPacoteAssociado;

	@FXML
	private TextField tfCliente;

	@FXML
	private JFXComboBox<Pacote> cbPacote;

	@FXML
	private JFXComboBox<String> cbFormaDePagamento;

	@FXML
	private ProgressIndicator piStatus;

	public static AutoCompletionBinding<Cliente> getBindAutoCompleteCliente() {
		return bindAutoCompleteCliente;
	}

	@FXML
	public void onBtCriaPacoteAction() {
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

		if (tfNome.getText().isEmpty() || tfValor.getText().isEmpty() || tfValorMao.getText().isEmpty()
				|| tfQuantMao.getText().isEmpty() || tfQuantPe.getText().isEmpty() || tfValorPe.getText().isEmpty()) {
			NotificacoesUtils.mostraNotificacoes("Aviso!", "Preencha todos os campos!");
		} else {
			piStatus.setVisible(true);		
			parada = true;
			Task<Void> acaoSalvaPacote = new Task<Void>() {
				@Override
				protected Void call() throws Exception {
					Platform.runLater(() -> {
						Thread t = new Thread(tarefa);
						t.start();
					});

					DaoPacote.salvarPacote(tfNome, tfValor, tfValorMao, tfQuantMao, tfQuantPe, tfValorPe);				
					parada = false;
					Platform.runLater(new Runnable() {
						@Override
						public void run() {
							carregaTabelaPacote();
							cbPacote.setItems(obPacote);
							tfValor.setText("");
							tfNome.setText("");
							tfValorMao.setText("");
							tfValorPe.setText("");
							tfQuantMao.setText("");
							tfQuantPe.setText("");
						}
					});
					return null;
				}
			};

			Platform.runLater(() -> {
				Thread t = new Thread(acaoSalvaPacote);
				t.start();
			});
		}
	}

	@FXML
	public void onBtExcluiPacoteAction() {
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
		if (AlertsUtils.showAlertGenerico("ATENÇÃO!", "Deseja mesmo excluír um pacote?",
				"Pacotes só serão desativados, para excluí-los no " + "banco de dados, contate o ADMINISTRADOR!")) {
			piStatus.setVisible(true);
			parada = true;
			Task<Void> acaoExcluiPacote = new Task<Void>() {
				@Override
				protected Void call() throws Exception {
					Platform.runLater(() -> {
						Thread t = new Thread(tarefa);
						t.start();
					});

					for (Pacote pacote : Colecao.pacotes) {
						if (pacote.getSelect().isSelected()) {
							DaoPacote.excluiPacote(pacote);
						}
					}

					DaoPacote.carregaPacote();

					Platform.runLater(new Runnable() {
						@Override
						public void run() {
							carregaTabelaPacote();
							cbPacote.setItems(obPacote);
						}
					});
					parada = false;
					return null;
				}
			};

			Platform.runLater(() -> {
				Thread t = new Thread(acaoExcluiPacote);
				t.start();
			});
		} else {
			NotificacoesUtils.mostraNotificacoes("Aviso!", "Operação cancelada");
		}
	}

	@FXML
	public void selecionaPacote() {
		Pacote pacote = tvPacotes.getSelectionModel().getSelectedItem();
		tfNomeAtualizado.setText(pacote.getPacote());
		tfQuantMaoAtualizado.setText(String.valueOf(pacote.getQuantMao()));
		tfQuantPeAtualizado.setText(String.valueOf(pacote.getQuantPe()));
		tfValorAtualizado.setText(String.valueOf(pacote.getValor()));
		tfValorMaoAtualizado.setText(String.valueOf(pacote.getPrecoMao()));
		tfIdPacote.setText(String.valueOf(pacote.getId()));
		tfValorPeAtualizado.setText(String.valueOf(pacote.getPrecoPe()));
	}

	@FXML
	public void onBtAtualizaPacoteAction() {
		if (tfIdPacote.getText().isEmpty()) {
			NotificacoesUtils.mostraNotificacoes("AVISO!", "Selecione um pacote primeiro!");
		} else {
			if (AlertsUtils.showAlertGenerico("AVISO!", "Deseja mesmo atualizar um pacote?",
					"ATENÇÃO\n\nAtualizar um pacote não vai alterar pacotes antigos associados a um cliente!")) {
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

				parada = true;
				Task<Void> acaoAtualizaPacote = new Task<Void>() {
					@Override
					protected Void call() throws Exception {
						Platform.runLater(() -> {
							Thread t = new Thread(tarefa);
							t.start();
						});
						DaoPacote.atualizaPacote(tfNomeAtualizado, tfValorAtualizado, tfValorMaoAtualizado,
								tfQuantMaoAtualizado, tfQuantPeAtualizado, tfValorPeAtualizado, tfIdPacote);
						DaoPacote.carregaPacoteAssociado();
						parada = false;
						Platform.runLater(new Runnable() {
							@Override
							public void run() {
								carregaTabelaPacote();
								carregaTabelaPacoteAssociado();
								cbPacote.setItems(obPacote);
								tfValorAtualizado.setText("");
								tfNomeAtualizado.setText("");
								tfValorMaoAtualizado.setText("");
								tfValorPeAtualizado.setText("");
								tfQuantMaoAtualizado.setText("");
								tfQuantPeAtualizado.setText("");
								tfIdPacote.setText("");
							}
						});
						return null;
					}
				};

				Platform.runLater(() -> {
					Thread t = new Thread(acaoAtualizaPacote);
					t.start();
				});
			} else {
				NotificacoesUtils.mostraNotificacoes("AVISO!", "Operação cancelada!");
			}
		}
	}

	public void carregaTabelaPacote() {
		tvPacotes.setItems(null);
		obPacote = FXCollections.observableArrayList(Colecao.pacotes);
		tvPacotes.setItems(obPacote);
		tvPacotes.refresh();
	}

	public void carregaTabelaPacoteAssociado() {
		tvPacotesAssociados.setItems(null);
		ObservableList<PacoteAssociado> obPacotesAssociados = FXCollections.observableArrayList(Colecao.pacoteAssociados);
		tvPacotesAssociados.setItems(obPacotesAssociados);
		tvPacotesAssociados.refresh();
	}

	@FXML
	public void onBtCriaAssociacaoAction() {
		if (tfCliente.getText().isEmpty()) {
			NotificacoesUtils.mostraNotificacoes("AVISO!", "Preencha todos os dados!");
		} else {
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
			parada = true;
			Task<Void> acaoAdicionaPacoteAssociado = new Task<Void>() {
				@Override
				protected Void call() throws Exception {
					Platform.runLater(() -> {
						Thread t = new Thread(tarefa);
						t.start();
					});
					DaoPacote.salvarPacoteAssociado(tfCliente, cbPacote);
					DaoOperacao.salvaOperacao("Pacote de: " + tfCliente.getText(), LocalDate.now(),
							String.valueOf(cbPacote.getValue().getValor()), cbFormaDePagamento.getValue());
					DaoTransacao.carregaTotalCaixa(LocalDate.now());
					carregaTabelaPacoteAssociado();

					Platform.runLater(new Runnable() {
						@Override
						public void run() {
							tfCliente.setText("");
						}
					});
					parada = false;
					return null;
				}
			};

			Platform.runLater(() -> {
				Thread t = new Thread(acaoAdicionaPacoteAssociado);
				t.start();
			});
		}
	}

	@FXML
	public void onBtExcluiAssociacaoAction() {
		if (AlertsUtils.showAlertGenerico("ATENÇÃO!", "Deseja mesmo excluír uma associação?",
				"O cliente que estiver associado perdera o pacote!")) {

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
			parada = true;
			Task<Void> acaoExcluiPacoteAssociado = new Task<Void>() {
				@Override
				protected Void call() throws Exception {
					Platform.runLater(() -> {
						Thread t = new Thread(tarefa);
						t.start();
					});

					for (PacoteAssociado pacote : Colecao.pacoteAssociados) {
						if (pacote.getSelect().isSelected()) {
							DaoPacote.excluirPacoteAssociado(pacote);
						}
					}
					DaoPacote.carregaPacoteAssociado();
					carregaTabelaPacoteAssociado();
					parada = false;
					return null;
				}
			};

			Platform.runLater(() -> {
				Thread t = new Thread(acaoExcluiPacoteAssociado);
				t.start();
			});

		} else {
			NotificacoesUtils.mostraNotificacoes("AVISO!", "Operação cancelada!");
		}
	}

	public void carregaFormaPagamento() {
		List<String> listMetPag = Arrays.asList("Dinheiro", "Cartão");
		ObservableList<String> obFormaPagamento = FXCollections.observableArrayList(listMetPag);
		cbFormaDePagamento.setItems(obFormaPagamento);
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		bindAutoCompleteCliente = TextFields.bindAutoCompletion(tfCliente, Colecao.clientes);
		colunaId.setCellValueFactory(new PropertyValueFactory<>("id"));
		colunaPacote.setCellValueFactory(new PropertyValueFactory<>("pacote"));
		colunaQuantMao.setCellValueFactory(new PropertyValueFactory<>("quantMao"));
		colunaQuantPe.setCellValueFactory(new PropertyValueFactory<>("quantPe"));
		colunaValor.setCellValueFactory(new PropertyValueFactory<>("valor"));
		colunaValorMao.setCellValueFactory(new PropertyValueFactory<>("precoMao"));
		colunaValorPe.setCellValueFactory(new PropertyValueFactory<>("precoPe"));
		colunaExcluirPacote.setCellValueFactory(new PropertyValueFactory<>("select"));

		colunaIdPacoteAssociado.setCellValueFactory(new PropertyValueFactory<>("id"));
		colunaPacoteAssociado.setCellValueFactory(new PropertyValueFactory<>("pacote"));
		colunaCliente.setCellValueFactory(new PropertyValueFactory<>("cliente"));
		colunaQuantMaoAssociado.setCellValueFactory(new PropertyValueFactory<>("quantMao"));
		colunaQuantPeAssociado.setCellValueFactory(new PropertyValueFactory<>("quantPe"));
		colunaExcluirPacoteAssociado.setCellValueFactory(new PropertyValueFactory<>("select"));

		carregaFormaPagamento();
		carregaTabelaPacote();
		carregaTabelaPacoteAssociado();
		cbPacote.setItems(obPacote);
		if (!obPacote.isEmpty()) {
			cbPacote.setValue(obPacote.get(0));
		}
	}

}
