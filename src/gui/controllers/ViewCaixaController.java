package gui.controllers;

import java.net.URL;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

import org.controlsfx.control.PopOver;
import org.controlsfx.control.textfield.AutoCompletionBinding;
import org.controlsfx.control.textfield.TextFields;

import application.Main;
import gui.util.Alerts;
import gui.util.Notificacoes;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Paint;
import model.collection.Colecao;
import model.collection.entities.Caixa;
import model.collection.entities.Cliente;
import model.collection.entities.Funcionario;
import model.collection.entities.Transacao;
import model.dao.DaoTransacao;

public class ViewCaixaController implements Initializable {

	double fundoDeTroco;

	ObservableList<Cliente> obCliente;

	ObservableList<Transacao> obTable;

	ObservableList<Transacao> obTableTemp;

	ObservableList<String> obFormaPagamento;

	public static TextField tfClienteTemp;

	public static ChoiceBox<Funcionario> cbFuncionarioTemp;

	public static AutoCompletionBinding<Cliente> bindAutoCompleteCliente;

	private boolean parada;

	@FXML
	private Button btVoltar;
	
	@FXML
	private Button btAjuda;

	@FXML
	private Button btExcluir;

	@FXML
	private Button btAtualizar;

	@FXML
	private Button btAbrirFecharCaixa;

	@FXML
	private Button btEnviarTransacao;

	@FXML
	private Button btCalTroco;

	@FXML
	private TextField tfValor;

	@FXML
	private TextField tfValorServico;

	@FXML
	private TextField tfDinheiroDado;

	@FXML
	private TextField tfCliente;

	@FXML
	private ChoiceBox<Funcionario> cbFuncionario;

	@FXML
	private ChoiceBox<String> cbFormaPagamento;

	@FXML
	private Label lbStatus;

	@FXML
	private Label lbTotalEmCaixa;

	@FXML
	private Label lbTroco;

	@FXML
	private Label lbValorTotal;

	@FXML
	private Label lbValorDinheiro;

	@FXML
	private Label lbValorCartao;

	@FXML
	private DatePicker dpData;

	@FXML
	private DatePicker dpSelecao;

	@FXML
	private TableView<Transacao> tvTransacao = new TableView<>();

	@FXML
	private TableColumn<Transacao, Integer> colunaId;

	@FXML
	private TableColumn<Transacao, String> colunaData;

	@FXML
	private TableColumn<Transacao, String> colunaCliente;

	@FXML
	private TableColumn<Transacao, String> colunaAtendente;

	@FXML
	private TableColumn<Transacao, String> colunaMeioPagamento;

	@FXML
	private TableColumn<Transacao, Double> colunaValor;

	@FXML
	private TableColumn<Transacao, CheckBox> colunaSelect;

	@FXML
	private Label labelStatus;

	@FXML
	private ProgressIndicator piStatus;

	@FXML
	public void onBtVoltarAction() {
		Main.getStage().setScene(Main.getMain());
		Main.getStage().centerOnScreen();
	}

	@FXML
	public void calculaTroco() {
		if (tfValorServico.getText().isEmpty() || tfDinheiroDado.getText().isEmpty()) {
			lbTroco.setText("Troco de: ");
		} else {
			double val = Double.parseDouble(tfDinheiroDado.getText()) - Double.parseDouble(tfValorServico.getText());
			if (val < 0) {
				lbTroco.setText("Troco de: ");
			} else {
				lbTroco.setText("Troco de: R$ " + val);
			}
		}
	}

	public void bloqueio() {
		if (Caixa.isStatus() != true) {
			btEnviarTransacao.setDisable(true);
			btExcluir.setDisable(true);
			tfCliente.setDisable(true);
			cbFuncionario.setDisable(true);
			dpData.setDisable(true);
			tfValor.setDisable(true);
			cbFormaPagamento.setDisable(true);
		} else {
			btEnviarTransacao.setDisable(false);
			btExcluir.setDisable(false);
			tfCliente.setDisable(false);
			cbFuncionario.setDisable(false);
			dpData.setDisable(false);
			tfValor.setDisable(false);
			cbFormaPagamento.setDisable(false);
		}
	}

	@FXML
	public void onBtAtualizarAction() {
		carregaFuncionario();
		carregaFormaPagamento();

	}

	public void mudaCaixa() {
		if (Caixa.isStatus() == false) {
			btAbrirFecharCaixa.setTextFill(Paint.valueOf("#10bf24"));
			btAbrirFecharCaixa.setText("Abrir Caixa!");
			lbStatus.setTextFill(Paint.valueOf("#ff0606"));
			lbStatus.setText("Fechado");
		} else {
			btAbrirFecharCaixa.setTextFill(Paint.valueOf("#ff0606"));
			btAbrirFecharCaixa.setText("Fechar Caixa!");
			lbStatus.setTextFill(Paint.valueOf("#10bf24"));
			lbStatus.setText("Aberto");
		}
	}

	@FXML
	public void onBtAbrirFecharCaixaAction() {
		if (Caixa.isStatus() == false) {
			btAbrirFecharCaixa.setTextFill(Paint.valueOf("#ff0606"));
			btAbrirFecharCaixa.setText("Fechar Caixa!");
			lbStatus.setTextFill(Paint.valueOf("#10bf24"));
			lbStatus.setText("Aberto");
			Caixa.setStatus(true);
			DaoTransacao.salvarStatus();
			
				lbTotalEmCaixa.setText(Alerts.showAlertGenericoTextField("AVISO!",
						"Informe quanto dinheiro tem no caixa\n*UTILIZE SÓ NÚMEROS", "Valor: "));
				fundoDeTroco = Double.parseDouble(lbTotalEmCaixa.getText().replaceAll(",", "."));
			
			DaoTransacao.salvarCaixa(dpData.getValue(), fundoDeTroco);
			carregaCaixa();
			bloqueio();
		} else {
			btAbrirFecharCaixa.setTextFill(Paint.valueOf("#10bf24"));
			btAbrirFecharCaixa.setText("Abrir Caixa!");
			lbStatus.setTextFill(Paint.valueOf("#ff0606"));
			lbStatus.setText("Fechado");
			Caixa.setStatus(false);
			DaoTransacao.salvarStatus();
			bloqueio();
		}

	}

	public void carregaCaixa() {
		DaoTransacao.carregaTotalCaixa(lbValorCartao, lbValorDinheiro, lbValorTotal, lbTotalEmCaixa, dpSelecao.getValue());
//		Task<Void> taskDaoTransacao = new Task<Void>() {
//			@Override
//			protected Void call() throws Exception {
//				
//				return null;
//			}
//		};
//		javafx.application.Platform.runLater(() -> {
//			Thread t = new Thread(taskDaoTransacao);
//			t.start();
//		});
	}
	
	public void calculaCaixa(TextField tfValor, ChoiceBox<String> cbPagamento) {
		double valotTotal = Double.parseDouble(lbValorTotal.getText().replaceAll(",", "."));
		double valorDinheiro = Double.parseDouble(lbValorDinheiro.getText().replaceAll(",", "."));
		double valorCartao = Double.parseDouble(lbValorCartao.getText().replaceAll(",", "."));
		if(cbPagamento.getValue().equals("Dinheiro")) {
			valorDinheiro += Double.parseDouble(tfValor.getText().replaceAll(",", "."));
			fundoDeTroco += Double.parseDouble(tfValor.getText().replaceAll(",", "."));
		}
		else {
			valorCartao += Double.parseDouble(tfValor.getText().replaceAll(",", "."));
		}
		
		DaoTransacao.calculaCaixa(dpData.getValue(), fundoDeTroco, valotTotal, valorDinheiro, valorCartao);
		DaoTransacao.carregaTotalCaixa(lbValorCartao, lbValorDinheiro, lbValorTotal, lbTotalEmCaixa, dpSelecao.getValue());
//		Task<Void> taskDaoTransacao = new Task<Void>() {
//			@Override
//			protected Void call() throws Exception {
//				
//				return null;
//			}
//		};
//		javafx.application.Platform.runLater(() -> {
//			Thread t = new Thread(taskDaoTransacao);
//			t.start();
//		});
	}

	public void carregaFuncionario() {
		ObservableList<Funcionario> obFuncionario = FXCollections.observableArrayList(Colecao.funcionarios);
		cbFuncionario.setItems(obFuncionario);
	}

	public void carregaFormaPagamento() {
		List<String> listMetPag = Arrays.asList("Dinheiro", "Cartão");
		obFormaPagamento = FXCollections.observableArrayList(listMetPag);
		cbFormaPagamento.setItems(obFormaPagamento);
	}

	public void carregaTable() {
		Task<Void> acaoCarregaTable = new Task<Void>() {
			@Override
			protected Void call() throws Exception {
				obTable = FXCollections.observableArrayList(Caixa.caixa);
				tvTransacao.setItems(obTable);
				return null;
			}
		};

		javafx.application.Platform.runLater(() -> {
			Thread t = new Thread(acaoCarregaTable);
			t.start();
		});

	}

	@FXML
	public void onBtAjudaAction() {
		PopOver popOver = new PopOver();
		popOver.show(btAjuda);
	}
	
	@FXML
	public void onBtEnviarTransacaoAction() {
		if (tfCliente.getText().isEmpty() || tfValor.getText().isEmpty()
				|| cbFormaPagamento.getSelectionModel().isEmpty() || cbFuncionario.getSelectionModel().isEmpty()) {
			Notificacoes.mostraNotificacao("Aviso!", "Preencha todos os campos!");
		} else {
			parada = true;
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					piStatus.setVisible(true);
					labelStatus.setVisible(true);
					labelStatus.setText("Gerando transação!");
				}
			});

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

			Task<Void> taskEnviaTransacao = new Task<Void>() {
				@Override
				protected Void call() throws Exception {
					javafx.application.Platform.runLater(() -> {
						Thread t = new Thread(tarefa);
						t.start();
					});
					DaoTransacao.salvarTransacao(tfCliente, cbFuncionario, dpData.getValue(), tfValor,
							cbFormaPagamento);
					
					Platform.runLater(new Runnable() {
						@Override
						public void run() {
							calculaCaixa(tfValor, cbFormaPagamento);
							carregaTransacao();
							tfValor.setText("");
							cbFuncionario.getSelectionModel().clearSelection();
							cbFormaPagamento.getSelectionModel().clearSelection();
						}
					});
					parada = false;
					return null;
				}
			};

			javafx.application.Platform.runLater(() -> {
				ViewController.getStagePagamento().hide();
				Thread t = new Thread(taskEnviaTransacao);
				t.start();
			});
		}
	}

	public void excluirTransacao() {
		if (!(Alerts.showAlertGenerico("Atenção!", "Deseja mesmo excluir algumas transações?",
				"A exclusão de transações "
						+ "Influenciam no salário do funcionário e no controle do caixa, CUIDADO!"))) {
			Notificacoes.mostraNotificacao("Aviso!", "Exclusão cancelada!");
		} else {
			parada = true;
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					piStatus.setVisible(true);
					labelStatus.setVisible(true);
					labelStatus.setText("Excluindo Transações!");
				}
			});

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

			Task<Void> acaoExcluirTransacao = new Task<Void>() {

				@Override
				protected Void call() throws Exception {
					javafx.application.Platform.runLater(() -> {
						Thread t = new Thread(tarefa);
						t.start();
					});
					for (Transacao tran : obTable) {
						if (tran.getSelect().isSelected()) {
							DaoTransacao.excluirTransacao(tran);
						}
					}
					carregaTransacao();
					Platform.runLater(new Runnable() {
						@Override
						public void run() {
							Notificacoes.mostraNotificacao("Aviso!", "Exclusão Concluída!");
						}
					});

					parada = false;
					return null;
				}
			};

			javafx.application.Platform.runLater(() -> {
				Thread t = new Thread(acaoExcluirTransacao);
				t.start();
			});
		}
	}

	public void carregaTransacao() {
		parada = true;
		dpData.setValue(dpSelecao.getValue());
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				piStatus.setVisible(true);
				labelStatus.setVisible(true);
				labelStatus.setText("Carregando tabela!");
			}
		});
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

		Task<Void> acaoCarregaTransacao = new Task<Void>() {
			@Override
			protected Void call() throws Exception {
				javafx.application.Platform.runLater(() -> {
					Thread t = new Thread(tarefa);
					t.start();
				});
				DaoTransacao.carregaTransacaoExpecifica(dpSelecao.getValue());
				carregaTable();
				Platform.runLater(new Runnable() {
					@Override
					public void run() {
						carregaCaixa();
					}
				});
				parada = false;
				return null;
			}
		};

		javafx.application.Platform.runLater(() -> {
			Thread t = new Thread(acaoCarregaTransacao);
			t.start();
		});
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		carregaFuncionario();
		carregaFormaPagamento();
		mudaCaixa();
		bloqueio();
		dpData.setValue(LocalDate.now());
		dpSelecao.setValue(LocalDate.now());
		colunaId.setCellValueFactory(new PropertyValueFactory<>("id"));
		colunaData.setCellValueFactory(new PropertyValueFactory<>("data"));
		colunaCliente.setCellValueFactory(new PropertyValueFactory<>("cliente"));
		colunaAtendente.setCellValueFactory(new PropertyValueFactory<>("atendente"));
		colunaMeioPagamento.setCellValueFactory(new PropertyValueFactory<>("formaPagamento"));
		colunaValor.setCellValueFactory(new PropertyValueFactory<>("valor"));
		colunaSelect.setCellValueFactory(new PropertyValueFactory<>("select"));
		tfClienteTemp = this.tfCliente;
		cbFuncionarioTemp = this.cbFuncionario;
		bindAutoCompleteCliente = TextFields.bindAutoCompletion(tfCliente, Colecao.clientes);
		carregaTransacao();
	}
}
