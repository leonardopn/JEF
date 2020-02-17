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
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import model.collections.Colecao;
import model.collections.entities.Funcionario;
import model.daos.DaoFuncionario;
import model.daos.DaoOperacao;
import model.daos.DaoTransacao;

public class ViewSalarioController implements Initializable {

	@FXML
	private TextField tfSalario;

	@FXML
	private ComboBox<String> cbFormaPagamento;

	@FXML
	private TextField tfFuncionario;

	@FXML
	private TextField tfCpf;

	@FXML
	private Button btPagar;

	@FXML
	private TableView<Funcionario> tvFuncionario;

	@FXML
	private TableColumn<Funcionario, String> colunaFuncionario;

	@FXML
	private TableColumn<Funcionario, Double> colunaSalario;

	@FXML
	public void onBtPagarAction(ActionEvent event) {
		if (tfSalario.getText().equals("0.0")) {
			NotificacoesUtils.mostraNotificacoes("Valor vazio", "Esse funcion�rio n�o tem o qu� receber");
		} else {
			if (tfSalario.getText().charAt(0) != '-') {
				tfSalario.setText("-" + tfSalario.getText());
			}
			Double salarioAtualizado = Double.parseDouble(tfSalario.getText());

			Task<Void> acaoPagaFUncionario = new Task<Void>() {
				@Override
				protected Void call() throws Exception {
					DaoFuncionario.atualizarSalario(tfCpf.getText(), salarioAtualizado);
					DaoOperacao.salvaOperacao("Pagamento de salário: " + tfFuncionario.getText(), LocalDate.now(),
							tfSalario.getText(), cbFormaPagamento.getValue());
					DaoTransacao.carregaTotalCaixa(LocalDate.now());
					DaoFuncionario.carregaFuncionario();

					Platform.runLater(new Runnable() {
						@Override
						public void run() {
							tfFuncionario.clear();
							tfCpf.clear();
							tfSalario.clear();
							populaTabela();
							NotificacoesUtils.mostraNotificacoes("Notificação", "Pagamento efetuado!!");
						}
					});
					return null;
				}
			};

			Platform.runLater(() -> {
				Thread t = new Thread(acaoPagaFUncionario);
				t.start();
			});
		}
	}

	public void populaTabela() {
		Task<Void> acaoPopulaTabela = new Task<Void>() {
			@Override
			protected Void call() throws Exception {
				DaoFuncionario.carregaFuncionario();
				ObservableList<Funcionario> obFuncionario = FXCollections.observableArrayList(Colecao.funcionarios);
				tvFuncionario.setItems(obFuncionario);
				tvFuncionario.refresh();
				return null;
			}
		};

		Platform.runLater(() -> {
			Thread t = new Thread(acaoPopulaTabela);
			t.start();
		});
	}

	@FXML
	public void selecionaFuncionario() {
		Funcionario fun = tvFuncionario.getSelectionModel().getSelectedItem();
		tfFuncionario.setText(fun.getNome());
		tfCpf.setText(String.valueOf(fun.getCpf()));
		tfSalario.setText(String.valueOf(fun.getSalario()));
	}

	public void carregaFormaPagamento() {
		List<String> listMetPag = Arrays.asList("Dinheiro", "Cartão");
		ObservableList<String> obFormaPagamento = FXCollections.observableArrayList(listMetPag);
		cbFormaPagamento.setItems(obFormaPagamento);
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		colunaFuncionario.setCellValueFactory(new PropertyValueFactory<>("nome"));
		colunaSalario.setCellValueFactory(new PropertyValueFactory<>("salario"));
		populaTabela();
		carregaFormaPagamento();

	}

}
