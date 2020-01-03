package gui.controllers;

import java.io.IOException;
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
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Paint;
import model.collection.Colecao;
import model.collection.entities.Caixa;
import model.collection.entities.Categoria;
import model.collection.entities.Cliente;
import model.collection.entities.Funcionario;
import model.collection.entities.PacoteAssociado;
import model.collection.entities.Servico;
import model.collection.entities.Transacao;
import model.dao.DaoTransacao;

public class ViewCaixaController implements Initializable {

	private static double fundoDeTroco;
	private static double valorTotal;
	private static double valorDinheiro;
	private static double valorCartao;
	private static int statusCaixa;
	final Image caixaAberto = new Image((getClass().getResourceAsStream("/model/images/icons8_open_sign_96px.png")));
	final Image caixaFechado = new Image((getClass().getResourceAsStream("/model/images/icons8_close_sign_160px.png")));

	ObservableList<Cliente> obCliente;

	ObservableList<Transacao> obTable;

	ObservableList<Transacao> obTableTemp;

	ObservableList<String> obFormaPagamento;

	public static TextField tfClienteTemp;

	public static ChoiceBox<Funcionario> cbFuncionarioTemp;

	public static AutoCompletionBinding<Cliente> bindAutoCompleteCliente;

	private boolean parada;

	@FXML
	private Button btAjuda;
	
	@FXML
	private TreeView<String> trvServicos;

	@FXML
	private ImageView ivCaixa;

	@FXML
	private Button btExcluir;

	@FXML
	private Button btAbrirFecharCaixa;

	@FXML
	private Button btEnviarTransacao;

	@FXML
	private Button btCalTroco;
	
	@FXML
	private Button btBuscar;

	@FXML
	private TextField tfValor;

	@FXML
	private TextField tfValorServico;
	
	@FXML
	private CheckBox cbBloqueiaPreco;
	
	@FXML
	private ChoiceBox<PacoteAssociado> cbPacoteAssociado;

	@FXML
	private TextField tfDinheiroDado;

	@FXML
	private TextField tfCliente;
	
	@FXML
	private TextField tfObs;

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
	private TableColumn<Transacao, String> colunaServico;
	
	@FXML
	private TableColumn<Transacao, String> colunaObs;

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

	// GETS & SETS

	public static void setFundoDeTroco(double fundoDeTroco) {
		ViewCaixaController.fundoDeTroco = fundoDeTroco;
	}

	public static void setValorTotal(double valorTotal) {
		ViewCaixaController.valorTotal = valorTotal;
	}

	public static void setValorDinheiro(double valorDinheiro) {
		ViewCaixaController.valorDinheiro = valorDinheiro;
	}

	public static void setValorCartao(double valorCartao) {
		ViewCaixaController.valorCartao = valorCartao;
	}

	public static double getFundoDeTroco() {
		return fundoDeTroco;
	}

	public static double getValorTotal() {
		return valorTotal;
	}

	public static double getValorDinheiro() {
		return valorDinheiro;
	}

	public static double getValorCartao() {
		return valorCartao;
	}

	public static void setStatusCaixa(int statusCaixa) {
		ViewCaixaController.statusCaixa = statusCaixa;
	}

	// Botão AJUDA
	@FXML
	public void onBtAjudaAction() {
		PopOver popOver = new PopOver();
		popOver.show(btAjuda);
	}

	
	public void carregaTreeView() {
		TreeItem<String> servicos = new TreeItem<String>("Serviços");
		servicos.setExpanded(true);
		
		for (Categoria catg : Colecao.categorias) {
			catg.getSelect().setIndeterminate(true);
			catg.getSelect().setAllowIndeterminate(true);
			catg.getSelect().setDisable(true);
			
			TreeItem<String> categoria = new TreeItem<String>(catg.getNome());
			servicos.getChildren().add(categoria);
			for (Servico serv : Colecao.servicos) {
				if(serv.getCategoria().equals(catg.getNome())) {
					TreeItem<String> servico = new TreeItem<String>(serv.getNome());
					categoria.getChildren().add(servico);
				}
			}
		}
		
		trvServicos.setRoot(servicos);
	}
	
	public String selecionaServico() {
		if(trvServicos.getSelectionModel().getSelectedItem() != null) {
			if(trvServicos.getSelectionModel().getSelectedItem().isLeaf() ) {
				String servico = trvServicos.getSelectionModel().getSelectedItem().getValue();
				for (Servico servi : Colecao.servicos) {
					if(servi.getNome().equals(servico)) {
						tfValor.setText(String.valueOf(servi.getPreco()));
						return servico;
					}
				}
			}
		}
		return null;
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
			tfValor.setDisable(true);
			dpData.setDisable(true);
			cbFormaPagamento.setDisable(true);
			cbBloqueiaPreco.setDisable(true);
			trvServicos.setDisable(true);
			tfObs.setDisable(true);
		} else {
			btEnviarTransacao.setDisable(false);
			btExcluir.setDisable(false);
			tfCliente.setDisable(false);
			cbFuncionario.setDisable(false);
			dpData.setDisable(false);
			if(!(cbBloqueiaPreco.isSelected())) {
				tfValor.setDisable(false);
			}
			cbFormaPagamento.setDisable(false);
			cbBloqueiaPreco.setDisable(false);
			trvServicos.setDisable(false);
			tfObs.setDisable(false);
		}
	}

	public void mudaCaixa() {
		if (Caixa.isStatus() == false) {
			ivCaixa.setImage(caixaAberto);
			lbStatus.setTextFill(Paint.valueOf("#ff0606"));
			lbStatus.setText("Fechado");
		} else {
			ivCaixa.setImage(caixaFechado);
			lbStatus.setTextFill(Paint.valueOf("#10bf24"));
			lbStatus.setText("Aberto");
		}
	}

	@FXML
	public void onBtAbrirFecharCaixaAction() {
		Parent parent;
		try {
			parent = FXMLLoader.load(getClass().getResource("/gui/view/ViewLoginConfirmacao.fxml"));
			Scene scene = new Scene(parent);
			Main.getStageLoginConfirmacao().setScene(scene);
			Main.getStageLoginConfirmacao().centerOnScreen();
			Main.getStageLoginConfirmacao().getIcons()
					.add(new Image(getClass().getResourceAsStream("/model/images/icon.png")));
			Main.getStageLoginConfirmacao().setTitle("JEF - Confirmação de Operação");
			Main.getStageLoginConfirmacao().showAndWait();
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (ViewLoginConfirmacaoController.status == true) {
			if (Caixa.isStatus() == false) {
				if (statusCaixa == 0) {
					Alerts.showAlert("AVISO!", "Caixa ja fechado!",
							"\nO caixa foi fechado uma vez HOJE, não existe a opção para reabri-lo, por favor, fale com o ADMINISTRADOR!",
							AlertType.WARNING);
				}
				else {
					lbTotalEmCaixa.setText(Alerts.showAlertGenericoTextField("AVISO!",
							"Informe quanto dinheiro tem no caixa\n*UTILIZE SÓ NÚMEROS", "Valor: "));
					fundoDeTroco = Double.parseDouble(lbTotalEmCaixa.getText().replaceAll(",", "."));
					lbStatus.setTextFill(Paint.valueOf("#10bf24"));
					statusCaixa = 1;
					lbStatus.setText("Aberto");
					Caixa.setStatus(true);
					DaoTransacao.abreFechaCaixa(dpData.getValue(), fundoDeTroco, true);
					carregaCaixa();
					bloqueio();
					ivCaixa.setImage(caixaFechado);
				}
			}
			else {
					lbStatus.setTextFill(Paint.valueOf("#ff0606"));
					lbStatus.setText("Fechado");
					Caixa.setStatus(false);
					bloqueio();
					statusCaixa = 0;
					DaoTransacao.abreFechaCaixa(dpData.getValue(), fundoDeTroco, false);
					ivCaixa.setImage(caixaAberto);
			}
		}
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
							cbFormaPagamento, selecionaServico(), tfObs.getText());
					Platform.runLater(new Runnable() {
						@Override
						public void run() {
							carregaTransacao();
							tfObs.setText("");
							tfValor.setText("");
							cbFuncionario.getSelectionModel().clearSelection();
							cbFormaPagamento.getSelectionModel().clearSelection();
							trvServicos.getSelectionModel().clearSelection();
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

	public void carregaCaixa() {
		zeraValores();

		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				if (dpSelecao.getValue().toString().equals(LocalDate.now().toString())) {
					btAbrirFecharCaixa.setDisable(false);
				} else {
					btAbrirFecharCaixa.setDisable(true);
				}
				piStatus.setVisible(true);
				labelStatus.setVisible(true);
				labelStatus.setText("Calculando Caixa!");
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

		Task<Void> taskDaoTransacao = new Task<Void>() {
			@Override
			protected Void call() throws Exception {
				parada = true;
				javafx.application.Platform.runLater(() -> {
					Thread t = new Thread(tarefa);
					t.start();
				});
				statusCaixa = DaoTransacao.carregaTotalCaixa(dpSelecao.getValue());
				Platform.runLater(new Runnable() {
					@Override
					public void run() {
						setaLabels();
					}
				});
				parada = false;
				return null;
			}
		};
		javafx.application.Platform.runLater(() -> {
			Thread t = new Thread(taskDaoTransacao);
			t.start();
		});
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

				Platform.runLater(new Runnable() {
					@Override
					public void run() {
						carregaCaixa();
						carregaTable();
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
					Platform.runLater(new Runnable() {
						@Override
						public void run() {
							carregaTransacao();
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
		obTable = FXCollections.observableArrayList(Caixa.caixa);
		tvTransacao.setItems(obTable);
	}

	public void setaLabels() {
		lbValorTotal.setText(String.valueOf(String.format("%.2f", valorTotal)));
		lbValorDinheiro.setText(String.valueOf(String.format("%.2f", valorDinheiro)));
		lbTotalEmCaixa.setText(String.valueOf(String.format("%.2f", fundoDeTroco)));
		lbValorCartao.setText(String.valueOf(String.format("%.2f", valorCartao)));
	}
	
	public void bloqDesbloqValor() {
		if(cbBloqueiaPreco.isSelected()) {
			tfValor.setDisable(true);
		}
		else {
			tfValor.setDisable(false);
		}
	}

	public void zeraValores() {
		setValorTotal(0.0);
		setValorDinheiro(0.0);
		setFundoDeTroco(0.0);
		setValorCartao(0.0);
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
		colunaObs.setCellValueFactory(new PropertyValueFactory<>("obs"));
		colunaServico.setCellValueFactory(new PropertyValueFactory<>("servico"));
		tfClienteTemp = this.tfCliente;
		cbFuncionarioTemp = this.cbFuncionario;
		bindAutoCompleteCliente = TextFields.bindAutoCompletion(tfCliente, Colecao.clientes);
		carregaTransacao();
		carregaTreeView();
		bloqDesbloqValor();
	}
}
