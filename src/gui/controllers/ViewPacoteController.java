package gui.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import gui.util.Notificacoes;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import model.collection.Colecao;
import model.collection.entities.Pacote;
import model.dao.DaoPacote;

public class ViewPacoteController implements Initializable {

	ObservableList<Pacote> obPacote;
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
	private TableColumn<Pacote, Double> ColunaValorMao;

	@FXML
	private TableColumn<Pacote, Double> ColunaValorPe;

	@FXML
	private Button btAddPacote;

	@FXML
	private TextField tfNome;

	@FXML
	private TextField tfValor;

	@FXML
	private TextField tfValorMao;

	@FXML
	private TextField tfQuantMao;

	@FXML
	private TextField tfQuantPe;

	@FXML
	private TextField tfValorPe;

	@FXML
	private TableView<?> tvPacotesAssociados;

	@FXML
	private TableColumn<?, ?> calunaIdCliente;

	@FXML
	private TableColumn<?, ?> colunaCliente;

	@FXML
	private TableColumn<?, ?> colunaPacoteAssociado;

	@FXML
	private TableColumn<?, ?> colunaQuantMaoAssociado;

	@FXML
	private TableColumn<?, ?> colunaQuantPeAssociado;

	@FXML
	private TextField tfCliente;

	@FXML
	private ChoiceBox<?> cbPacote;

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
				|| tfQuantMao.getText().isEmpty() || tfQuantPe.getText().isEmpty()
				|| tfValorPe.getText().isEmpty()) {
			Notificacoes.mostraNotificacao("Aviso!", "Preencha todos os campos!");
		}
		else {
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

	public void carregaTabelaPacote() {
		tvPacotes.setItems(null);
		obPacote = FXCollections.observableArrayList(Colecao.pacotes);
		tvPacotes.setItems(obPacote);
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		colunaId.setCellValueFactory(new PropertyValueFactory<>("id"));
		colunaPacote.setCellValueFactory(new PropertyValueFactory<>("pacote"));
		colunaQuantMao.setCellValueFactory(new PropertyValueFactory<>("quantMao"));
		colunaQuantPe.setCellValueFactory(new PropertyValueFactory<>("quantPe"));
		colunaValor.setCellValueFactory(new PropertyValueFactory<>("valor"));
		ColunaValorMao.setCellValueFactory(new PropertyValueFactory<>("precoMao"));
		ColunaValorPe.setCellValueFactory(new PropertyValueFactory<>("precoPe"));
		carregaTabelaPacote();
	}

}
