package gui.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import gui.util.Notificacoes;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import model.collection.Colecao;
import model.collection.entities.Funcionario;
import model.dao.DaoFuncionario;

public class ViewSalarioController implements Initializable {

	ObservableList<Funcionario> obFuncionario;

	@FXML
	private TextField tfSalario;

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
	void onBtPagarAction(ActionEvent event) {
		if (tfSalario.getText().equals("0.0")) {
			Notificacoes.mostraNotificacao("Valor vazio", "Esse funcion�rio n�o tem o qu� receber");
		} else {
			Double salarioAtualizado = Double.parseDouble(tfSalario.getText());
			DaoFuncionario.atualizarSalario(tfCpf.getText(), (-salarioAtualizado));
			DaoFuncionario.carregaFuncionario();
			tfFuncionario.clear();
			tfCpf.clear();
			tfSalario.clear();
			populaTabela();
			Notificacoes.mostraNotificacao("Notificação", "Pagamento efetuado!!");
		}
	}

	public void populaTabela() {
		Task<Void> acaoPopulaTabela = new Task<Void>() {
			@Override
			protected Void call() throws Exception {
				DaoFuncionario.carregaFuncionario();
				obFuncionario = FXCollections.observableArrayList(Colecao.funcionarios);
				tvFuncionario.setItems(obFuncionario);
				tvFuncionario.refresh();
				return null;
			}
		};

		javafx.application.Platform.runLater(() -> {
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

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		colunaFuncionario.setCellValueFactory(new PropertyValueFactory<>("nome"));
		colunaSalario.setCellValueFactory(new PropertyValueFactory<>("salario"));
		populaTabela();

	}

}
