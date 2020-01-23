package gui.controllers;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.Date;
import java.util.ResourceBundle;

import org.controlsfx.control.textfield.AutoCompletionBinding;
import org.controlsfx.control.textfield.TextFields;

import gui.util.Alerts;
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
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import model.collection.Colecao;
import model.collection.entities.Agendamento;
import model.collection.entities.Caixa;
import model.collection.entities.Cliente;
import model.collection.entities.Funcionario;
import model.dao.DaoAgendamento;
import model.dao.DaoCliente;
import model.dao.DaoFuncionario;
import model.dao.DaoPacote;
import model.dao.DaoServico;
import model.dao.DaoTransacao;

public class ViewController implements Initializable {

	private static Scene caixa;
	private static Scene pagamento;
	private static Scene funcionario;
	private static Scene operacoes;
	private static Scene cliente;
	private static Scene sobre;
	private static Scene pacote;
	private static Stage stageAgenda = new Stage();
	private static Stage stageOperacoes = new Stage();
	private static Stage stageFuncionario = new Stage();
	private static Stage stageCliente = new Stage();
	private static Stage stageCaixa = new Stage();
	private static Stage stagePagamento = new Stage();
	private static Stage stageSobre = new Stage();
	private static Stage stagePacote = new Stage();
	private static Funcionario funTemp;
	private static LocalDate dpDataTemp;
	private static TableView<Funcionario> tvAgendaTemp;
	private static TableView<Funcionario> tvFuncionarioTemp;
	public static TextField tfClienteTemp;
	public static AutoCompletionBinding<Cliente> bindAutoCompleteCliente;
	public boolean parada;

	ObservableList<Agendamento> obAgendamento;

	@FXML
	private SplitPane splitPaneCentral;

	@FXML
	private Button btCriaFuncionario;
	
	@FXML
	private Button btOperacoes;

	@FXML
	private DatePicker dpData;

	@FXML
	private ProgressIndicator pb;

	@FXML
	private DatePicker dpDataExcluir;

	@FXML
	private TextField tfCliente;

	@FXML
	private ChoiceBox<Date> cbHorarios;

	@FXML
	private Button btCriaCliente;

	@FXML
	private Button btCaixa;

	@FXML
	private Button btPagamento;

	@FXML
	private Button btSalvar;

	@FXML
	private Button btPacote;

	@FXML
	private ImageView iVSplit;

	@FXML
	private Button btCarregar;

	@FXML
	private Label labelStatus;

	@FXML
	private TableView<Funcionario> tvFuncionario = new TableView<>();

	@FXML
	private TableColumn<Funcionario, String> colunaFuncionario;

	@FXML
	private TableView<Funcionario> tvAgenda = new TableView<>();

	@FXML
	private TableColumn<Funcionario, String> coluna8;

	@FXML
	private TableColumn<Funcionario, String> coluna8_3;

	@FXML
	private TableColumn<Funcionario, String> coluna9;

	@FXML
	private TableColumn<Funcionario, String> coluna9_3;

	@FXML
	private TableColumn<Funcionario, String> coluna10;

	@FXML
	private TableColumn<Funcionario, String> coluna10_3;

	@FXML
	private TableColumn<Funcionario, String> coluna11;

	@FXML
	private TableColumn<Funcionario, String> coluna11_3;

	@FXML
	private TableColumn<Funcionario, String> coluna12;

	@FXML
	private TableColumn<Funcionario, String> coluna12_3;

	@FXML
	private TableColumn<Funcionario, String> coluna13;

	@FXML
	private TableColumn<Funcionario, String> coluna13_3;

	@FXML
	private TableColumn<Funcionario, String> coluna14;

	@FXML
	private TableColumn<Funcionario, String> coluna14_3;

	@FXML
	private TableColumn<Funcionario, String> coluna15;

	@FXML
	private TableColumn<Funcionario, String> coluna15_3;

	@FXML
	private TableColumn<Funcionario, String> coluna16;

	@FXML
	private TableColumn<Funcionario, String> coluna16_3;

	@FXML
	private TableColumn<Funcionario, String> coluna17;

	@FXML
	private TableColumn<Funcionario, String> coluna17_3;

	@FXML
	private TableColumn<Funcionario, String> coluna18;

	@FXML
	private Button btPesquisar;

	@FXML
	private TableView<Agendamento> tvAgendamento;

	@FXML
	private TableColumn<Agendamento, String> colunaCliente;

	@FXML
	private TableColumn<Agendamento, String> colunaHorario;

	@FXML
	private TableColumn<Agendamento, CheckBox> colunaExcluir;

	@FXML
	private TableColumn<Agendamento, CheckBox> colunaFuncionarioAgendamento;

	@FXML
	private Button btExcluir;

	// get's e set's

	public static Stage getStageFuncionario() {
		return stageFuncionario;
	}

	public static Stage getStageCliente() {
		return stageCliente;
	}

	public static Scene getCaixa() {
		return caixa;
	}

	public static Funcionario getFunTemp() {
		return funTemp;
	}

	public static LocalDate getDpDataTemp() {
		return dpDataTemp;
	}

	public static TableView<Funcionario> getTvAgendaTemp() {
		return tvAgendaTemp;
	}

	public static Scene getFuncionario() {
		return funcionario;
	}

	public static Scene getCliente() {
		return cliente;
	}

	public static Scene getPacote() {
		return pacote;
	}

	public static Stage getStageAgenda() {
		return stageAgenda;
	}

	public static TableView<Funcionario> getTvFuncionarioTemp() {
		return tvFuncionarioTemp;
	}

	public static TextField getTfClienteTemp() {
		return tfClienteTemp;
	}

	public static Stage getStageSobre() {
		return stageSobre;
	}

	public static Stage getStageCaixa() {
		return stageCaixa;
	}

	public static Stage getStagePagamento() {
		return stagePagamento;
	}

	public static Stage getStagePacote() {
		return stagePacote;
	}

	public static Stage getStageOperacoes() {
		return stageOperacoes;
	}
	
	// abre páginas

	@FXML
	public void onBtCriaFuncionarioAction() {
		try {
			retornaInformacaoAgenda();
			Parent fxmlfuncionario = FXMLLoader.load(getClass().getResource("/gui/view/ViewFuncionario.fxml"));
			funcionario = new Scene(fxmlfuncionario);
			stageFuncionario.setScene(funcionario);
			stageFuncionario.show();
			stageFuncionario.centerOnScreen();
			stageFuncionario.getIcons().add(new Image(getClass().getResourceAsStream("/model/images/icon.png")));
			stageFuncionario.setTitle("JEF - Funcionário");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@FXML
	public void onBtAbreOperacoesAction() {
		try {
			retornaInformacaoAgenda();
			Parent fxmlOperacoes = FXMLLoader.load(getClass().getResource("/gui/view/ViewOperacoes.fxml"));
			operacoes = new Scene(fxmlOperacoes);
			stageOperacoes.setScene(operacoes);
			stageOperacoes.show();
			stageOperacoes.centerOnScreen();
			stageOperacoes.getIcons().add(new Image(getClass().getResourceAsStream("/model/images/icon.png")));
			stageOperacoes.setTitle("JEF - Operações");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@FXML
	public void onBtAbrePacoteAction() {
		try {
			retornaInformacaoAgenda();
			Parent fxmlPacote = FXMLLoader.load(getClass().getResource("/gui/view/ViewPacote.fxml"));
			pacote = new Scene(fxmlPacote);
			stagePacote.setScene(pacote);
			stagePacote.show();
			stagePacote.centerOnScreen();
			stagePacote.getIcons().add(new Image(getClass().getResourceAsStream("/model/images/icon.png")));
			stagePacote.setTitle("JEF - Pacotes");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@FXML
	public void carregaAgendamento() {
		if (!(tvAgenda.getSelectionModel().isEmpty())) {
			if (!(stageAgenda.isShowing())) {
				retornaInformacaoAgenda();
				try {
					Parent fxmlAgenda = FXMLLoader.load(getClass().getResource("/gui/view/ViewAgenda.fxml"));
					Scene agenda = new Scene(fxmlAgenda);
					stageAgenda.setScene(agenda);
					stageAgenda.show();
					stageAgenda.centerOnScreen();
					stageAgenda.getIcons().add(new Image(getClass().getResourceAsStream("/model/images/icon.png")));
					stageAgenda.setTitle("JEF - Agendamento");
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	@FXML
	public void onBtCriaClienteAction() {
		try {
			retornaInformacaoAgenda();
			Parent fxmlCliente = FXMLLoader.load(getClass().getResource("/gui/view/ViewCliente.fxml"));
			cliente = new Scene(fxmlCliente);
			stageCliente.setScene(cliente);
			stageCliente.show();
			stageCliente.centerOnScreen();
			stageCliente.getIcons().add(new Image(getClass().getResourceAsStream("/model/images/icon.png")));
			stageCliente.setTitle("JEF - Cliente");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@FXML
	public void onBtAbreCaixaAction() {
		try {
			if(stageCaixa.isShowing()) {
				stageCaixa.toFront();
			}
			else {
				Parent fxmlCaixa = FXMLLoader.load(getClass().getResource("/gui/view/ViewCaixa.fxml"));
				caixa = new Scene(fxmlCaixa);
				stageCaixa.setScene(caixa);
				stageCaixa.show();
				stageCaixa.centerOnScreen();
				stageCaixa.getIcons().add(new Image(getClass().getResourceAsStream("/model/images/icon.png")));
				stageCaixa.setTitle("JEF - Caixa");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@FXML
	public void onBtAbrePagamentoAction() {
		try {
			Parent fxmlPagamento = FXMLLoader.load(getClass().getResource("/gui/view/ViewSalario.fxml"));
			pagamento = new Scene(fxmlPagamento);
			stagePagamento.setScene(pagamento);
			stagePagamento.show();
			stagePagamento.centerOnScreen();
			stagePagamento.getIcons().add(new Image(getClass().getResourceAsStream("/model/images/icon.png")));
			stagePagamento.setTitle("JEF - Pagamento");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@FXML
	public void onBtAbreSobreAction() {
		try {
			Parent fxmlSobre = FXMLLoader.load(getClass().getResource("/gui/view/ViewSobre.fxml"));
			sobre = new Scene(fxmlSobre);
			stageSobre.setScene(sobre);
			stageSobre.show();
			stageSobre.centerOnScreen();
			stageSobre.getIcons().add(new Image(getClass().getResourceAsStream("/model/images/icon.png")));
			stageSobre.setTitle("JEF - Sobre");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// funções para popular tabelas

	public void carregaFuncionario() {
		tvFuncionario.refresh();
		ObservableList<Funcionario> obFuncionario = FXCollections.observableArrayList(Colecao.funcionarios);
		tvFuncionario.setItems(obFuncionario);
	}

	@FXML
	public void carregaAgenda() {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				pb.setVisible(true);
				labelStatus.setVisible(true);
				labelStatus.setText("Preenchendo tabela!");
			}
		});

		parada = true;
		Task<Void> tarefa = new Task<Void>() {
			@Override
			protected Void call() throws Exception {
				while (parada == true) {
					Thread.sleep(0);
				}
				pb.setVisible(false);
				labelStatus.setVisible(false);
				return null;
			}
		};

		javafx.application.Platform.runLater(() -> {
			Thread t = new Thread(tarefa);
			t.start();
		});

		Task<Void> acaoCarregarAgenda = new Task<Void>() {
			@Override
			protected Void call() throws Exception {
				tvAgenda.setItems(null);
				dpDataExcluir.setValue(dpData.getValue());
				DaoFuncionario.carregaAgendaFuncionario(dpData.getValue());
				tvAgenda.refresh();
				ObservableList<Funcionario> obAgenda = FXCollections.observableArrayList(Colecao.funcionarios);
				tvAgenda.setItems(obAgenda);
				retornaInformacaoAgenda();
				parada = false;
				return null;
			}
		};

		javafx.application.Platform.runLater(() -> {
			Thread t = new Thread(acaoCarregarAgenda);
			t.start();
		});
	}

	private void populaTabela() {
		tvAgendamento.setItems(null);
		obAgendamento = FXCollections.observableArrayList(Colecao.agendamentos);
		tvAgendamento.setItems(obAgendamento);
	}

	// ações de botões

	@FXML
	public void onBtPesquisaAgendamento() {
		parada = true;
		pb.setVisible(true);
		labelStatus.setVisible(true);
		labelStatus.setText("Preenchendo tabela!");
		Task<Void> tarefa = new Task<Void>() {
			@Override
			protected Void call() throws Exception {
				while (parada == true) {
					Thread.sleep(0);
				}
				pb.setVisible(false);
				labelStatus.setVisible(false);
				return null;
			}
		};

		javafx.application.Platform.runLater(() -> {
			Thread t = new Thread(tarefa);
			t.start();
		});

		Task<Void> acaoPesquisaAgendamento = new Task<Void>() {
			@Override
			protected Void call() throws Exception {
				DaoAgendamento.carregaAgendamento(dpDataExcluir.getValue(), tfCliente.getText());
				tfCliente.clear();
				populaTabela();
				parada = false;
				return null;
			}
		};

		javafx.application.Platform.runLater(() -> {
			Thread t = new Thread(acaoPesquisaAgendamento);
			t.start();
		});
	}

	@FXML
	public void onBtExcluirAgendamento() {
		if (Alerts.showAlertExclusao()) {
			pb.setVisible(true);
			labelStatus.setVisible(true);
			Task<Void> acaoExcluir = new Task<Void>() {
				@Override
				protected Void call() throws Exception {
					for (Agendamento age : obAgendamento) {
						if (age.getSelect().isSelected()) {
							DaoAgendamento.excluirAgendamento(age, dpDataExcluir.getValue());
						}
					}
					tvAgendamento.setItems(null);
					carregaAgenda();
					parada = false;
					return null;
				}
			};

			javafx.application.Platform.runLater(() -> {
				Thread t = new Thread(acaoExcluir);
				t.start();
			});

			parada = true;
			labelStatus.setText("Excluíndo agendamentos!");
			Task<Void> tarefa = new Task<Void>() {
				@Override
				protected Void call() throws Exception {
					while (parada == true) {
						Thread.sleep(1);
					}
					pb.setVisible(false);
					labelStatus.setVisible(false);
					return null;
				}
			};

			javafx.application.Platform.runLater(() -> {
				Thread t = new Thread(tarefa);
				t.start();
			});
		}
	}

	// métodos avulsos

	public void abreFechaSplit() {
		double[] divisor = splitPaneCentral.getDividerPositions();
		if (divisor[0] < 0.12290909090909091) {
			splitPaneCentral.setDividerPositions(0.135);
		} else {
			splitPaneCentral.setDividerPositions(0.0022);
		}
	}

	public void retornaInformacaoAgenda() {
		funTemp = this.tvAgenda.getSelectionModel().getSelectedItem();
		dpDataTemp = this.dpData.getValue();
		tvAgendaTemp = this.tvAgenda;
		tvFuncionarioTemp = this.tvFuncionario;
		tfClienteTemp = this.tfCliente;
	}

	public static void carregarBase() {
		DaoServico.carregaServicos();
		DaoServico.carregaCategoria();
		DaoCliente.carregaCliente();
		DaoFuncionario.carregaFuncionario();
		Caixa.setStatus(DaoTransacao.carregaCaixa());
		DaoPacote.carregaPacote();
		DaoPacote.carregaPacoteAssociado();
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		colunaFuncionario.setCellValueFactory(new PropertyValueFactory<>("nome"));

		coluna8.setCellValueFactory(new PropertyValueFactory<>("h8"));
		coluna8_3.setCellValueFactory(new PropertyValueFactory<>("h8_3"));
		coluna9.setCellValueFactory(new PropertyValueFactory<>("h9"));
		coluna9_3.setCellValueFactory(new PropertyValueFactory<>("h9_3"));
		coluna10.setCellValueFactory(new PropertyValueFactory<>("h10"));
		coluna10_3.setCellValueFactory(new PropertyValueFactory<>("h10_3"));
		coluna11.setCellValueFactory(new PropertyValueFactory<>("h11"));
		coluna11_3.setCellValueFactory(new PropertyValueFactory<>("h11_3"));
		coluna12.setCellValueFactory(new PropertyValueFactory<>("h12"));
		coluna12_3.setCellValueFactory(new PropertyValueFactory<>("h12_3"));
		coluna13.setCellValueFactory(new PropertyValueFactory<>("h13"));
		coluna13_3.setCellValueFactory(new PropertyValueFactory<>("h13_3"));
		coluna14.setCellValueFactory(new PropertyValueFactory<>("h14"));
		coluna14_3.setCellValueFactory(new PropertyValueFactory<>("h14_3"));
		coluna15.setCellValueFactory(new PropertyValueFactory<>("h15"));
		coluna15_3.setCellValueFactory(new PropertyValueFactory<>("h15_3"));
		coluna16.setCellValueFactory(new PropertyValueFactory<>("h16"));
		coluna16_3.setCellValueFactory(new PropertyValueFactory<>("h16_3"));
		coluna17.setCellValueFactory(new PropertyValueFactory<>("h17"));
		coluna17_3.setCellValueFactory(new PropertyValueFactory<>("h17_3"));
		coluna18.setCellValueFactory(new PropertyValueFactory<>("h18"));

		colunaCliente.setCellValueFactory(new PropertyValueFactory<>("cliente"));
		colunaHorario.setCellValueFactory(new PropertyValueFactory<>("horario"));
		colunaExcluir.setCellValueFactory(new PropertyValueFactory<>("select"));
		colunaFuncionarioAgendamento.setCellValueFactory(new PropertyValueFactory<>("funcionario"));

		pb.setVisible(false);
		dpData.setValue(LocalDate.now());
		carregarBase();
		carregaFuncionario();
		carregaAgenda();
		bindAutoCompleteCliente = TextFields.bindAutoCompletion(tfCliente, Colecao.clientes);
		dpDataExcluir.setValue(LocalDate.now());
	}
}
