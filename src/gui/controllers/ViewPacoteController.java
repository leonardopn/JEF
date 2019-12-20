package gui.controllers;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import org.controlsfx.control.textfield.AutoCompletionBinding;
import org.controlsfx.control.textfield.TextFields;

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
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import model.collection.Colecao;
import model.collection.entities.Cliente;
import model.collection.entities.Pacote;
import model.collection.entities.PacoteAssociado;
import model.dao.DaoPacote;

public class ViewPacoteController implements Initializable {

	ObservableList<Pacote> obPacote;
	ObservableList<PacoteAssociado> obPacotesAssociados;
	ArrayList<Cliente> teste = new ArrayList<>();
	public static AutoCompletionBinding<Cliente> bindAutoCompleteCliente;
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
	private ChoiceBox<Pacote> cbPacote;

	@FXML
	private ProgressIndicator piStatus;

	@FXML
	private Label labelStatus;

	@FXML
	public void onBtCriaPacoteAction() {
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

		if (tfNome.getText().isEmpty() || tfValor.getText().isEmpty() || tfValorMao.getText().isEmpty()
				|| tfQuantMao.getText().isEmpty() || tfQuantPe.getText().isEmpty() || tfValorPe.getText().isEmpty()) {
			Notificacoes.mostraNotificacao("Aviso!", "Preencha todos os campos!");
		} else {
			piStatus.setVisible(true);
			labelStatus.setVisible(true);
			parada = true;
			Task<Void> acaoSalvaPacote = new Task<Void>() {
				@Override
				protected Void call() throws Exception {
					javafx.application.Platform.runLater(() -> {
						Thread t = new Thread(tarefa);
						t.start();
					});

					DaoPacote.salvarPacote(tfNome, tfValor, tfValorMao, tfQuantMao, tfQuantPe, tfValorPe);
					carregaTabelaPacote();
					cbPacote.setItems(obPacote);
					parada = false;
					Platform.runLater(new Runnable() {
						@Override
						public void run() {
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

			javafx.application.Platform.runLater(() -> {
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
				while (parada == true) {
					Thread.sleep(0);
				}
				piStatus.setVisible(false);
				labelStatus.setVisible(false);
				return null;
			}
		};
		if (Alerts.showAlertGenerico("ATENÇÃO!", "Deseja mesmo excluír um pacote?",
				"Pacotes só serão desativados, para excluí-los no " + "banco de dados, contate o ADMINISTRADOR!")) {
			piStatus.setVisible(true);
			labelStatus.setVisible(true);
			labelStatus.setText("Excluíndo pacote!");
			parada = true;
			Task<Void> acaoExcluiPacote = new Task<Void>() {
				@Override
				protected Void call() throws Exception {
					javafx.application.Platform.runLater(() -> {
						Thread t = new Thread(tarefa);
						t.start();
					});

					for (Pacote pacote : Colecao.pacotes) {
						if (pacote.getSelect().isSelected()) {
							DaoPacote.excluiPacote(pacote);
						}
					}
					carregaTabelaPacote();
					cbPacote.setItems(obPacote);
					parada = false;
					return null;
				}
			};

			javafx.application.Platform.runLater(() -> {
				Thread t = new Thread(acaoExcluiPacote);
				t.start();
			});
		} else {
			Notificacoes.mostraNotificacao("Aviso!", "Operação cancelada");
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
			Notificacoes.mostraNotificacao("AVISO!", "Selecione um pacote primeiro!");
		} else {
			if (Alerts.showAlertGenerico("AVISO!", "Deseja mesmo atualizar um pacote?",
					"ATENÇÃO\n\nAtualizar um pacote não vai alterar pacotes antigos associados a um cliente!")) {
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
				labelStatus.setText("Atualizando pacote!");

				parada = true;
				Task<Void> acaoAtualizaPacote = new Task<Void>() {
					@Override
					protected Void call() throws Exception {
						javafx.application.Platform.runLater(() -> {
							Thread t = new Thread(tarefa);
							t.start();
						});
						DaoPacote.atualizaPacote(tfNomeAtualizado, tfValorAtualizado, tfValorMaoAtualizado,
								tfQuantMaoAtualizado, tfQuantPeAtualizado, tfValorPeAtualizado, tfIdPacote);
						DaoPacote.carregaPacoteAssociado();
						carregaTabelaPacote();
						carregaTabelaPacoteAssociado();
						cbPacote.setItems(obPacote);
						parada = false;
						Platform.runLater(new Runnable() {
							@Override
							public void run() {
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

				javafx.application.Platform.runLater(() -> {
					Thread t = new Thread(acaoAtualizaPacote);
					t.start();
				});
			} else {
				Notificacoes.mostraNotificacao("AVISO!", "Operação cancelada!");
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
		obPacotesAssociados = FXCollections.observableArrayList(Colecao.pacoteAssociados);
		tvPacotesAssociados.setItems(obPacotesAssociados);
		tvPacotesAssociados.refresh();
	}

	@FXML
	public void onBtCriaAssociacaoAction() {
		DaoPacote.salvarPacoteAssociado(tfCliente, cbPacote);
		carregaTabelaPacoteAssociado();
	}

	@FXML
	public void onBtExcluiAssociacaoAction() {
		// Preencher
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
		
		carregaTabelaPacote();
		carregaTabelaPacoteAssociado();
		cbPacote.setItems(obPacote);
	}

}
