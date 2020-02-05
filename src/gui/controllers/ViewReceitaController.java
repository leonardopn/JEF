package gui.controllers;

import java.net.URL;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

import gui.utils.NotificacoesUtils;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextField;
import model.daos.DaoOperacao;

public class ViewReceitaController implements Initializable {

	private boolean parada;

	@FXML
	private TextField tfDescricao;

	@FXML
	private DatePicker dpData;

	@FXML
	private TextField tfEntrada;

	@FXML
	private Button btEnviar;

	@FXML
	private ComboBox<String> cbFormaPagamento;

	@FXML
	private ProgressIndicator piStatus;

	@FXML
	public void geraReceita() {
		if(tfDescricao.getText().isEmpty() || tfEntrada.getText().isEmpty() || cbFormaPagamento.getValue() == null) {
			NotificacoesUtils.mostraNotificacoes("AVISO", "Preencha todos os campos!");
		}
		else {
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
			
			Task<Void> taskReceita = new Task<Void>() {
				@Override
				protected Void call() throws Exception {
					parada = true;
					Platform.runLater(() -> {
						Thread t = new Thread(tarefa);
						t.start();
					});
					DaoOperacao.salvaOperacao(tfDescricao.getText(), dpData.getValue(), tfEntrada.getText().replaceAll(",","."), cbFormaPagamento.getValue());
					
					parada = false;
					Platform.runLater(new Runnable() {
						@Override
						public void run() {
							ViewOperacoesController.getStageReceita().close();
						}
					});
					return null;
				}
			};
			
			Platform.runLater(() -> {
				Thread t = new Thread(taskReceita);
				t.start();
			});
		}
		
	}

	public void carregaFormaPagamento() {
		List<String> listMetPag = Arrays.asList("Dinheiro", "Cartão");
		ObservableList<String>obFormaPagamento = FXCollections.observableArrayList(listMetPag);
		cbFormaPagamento.setItems(obFormaPagamento);
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		dpData.setValue(LocalDate.now());
		carregaFormaPagamento();
	}
}
