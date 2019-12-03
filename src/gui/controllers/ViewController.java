package gui.controllers;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.Date;
import java.util.ResourceBundle;

import org.controlsfx.control.textfield.AutoCompletionBinding;
import org.controlsfx.control.textfield.TextFields;

import gui.util.Alerts;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.dao.DaoFuncionario;
import model.entities.Agendamento;
import model.entities.Funcionario;
import model.services.Cadastro;
import model.services.Carregar;
import model.services.Excluir;

public class ViewController implements Initializable {

	private static Scene caixa;
	private static Scene pagamento;
	private static Scene funcionario;
	private static Scene cliente;
	private static Scene sobre;
	private static Stage stageAgenda = new Stage();
	private static Stage stageFuncionario = new Stage();
	private static Stage stageCliente = new Stage();
	private static Stage stageCaixa = new Stage();
	private static Stage stagePagamento = new Stage();
	private static Stage stageSobre = new Stage();
	private static Funcionario funTemp;
	private static LocalDate dpDataTemp;
	private static TableView<Funcionario> tvAgendaTemp;
	private static TableView<Funcionario> tvFuncionarioTemp;
	public static TextField tfClienteTemp;
	public static AutoCompletionBinding bindAutoCompleteCliente;
	
	ObservableList<Agendamento> obAgendamento;

	@FXML
	private Button btCriaFuncionario;

	@FXML
	private DatePicker dpData;

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
	private Button btCarregar;

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
	
	// abre p�ginas

	@FXML
	public void onBtCriaFuncionarioAction() {
		try {
				retornaInformacaoAgenda();
				Parent fxmlfuncionario = FXMLLoader.load(getClass().getResource("/gui/view/ViewFuncionario.fxml"));
				funcionario = new Scene(fxmlfuncionario);
				stageFuncionario.setScene(funcionario);
				stageFuncionario.show();
				stageFuncionario.centerOnScreen();
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

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@FXML
	public void onBtAbreCaixaAction() {
		try {
			Parent fxmlCaixa = FXMLLoader.load(getClass().getResource("/gui/view/ViewCaixa.fxml"));
			caixa = new Scene(fxmlCaixa);
			stageCaixa.setScene(caixa);
			stageCaixa.show();
			stageCaixa.centerOnScreen();
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
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// fun��es para popular tabelas

	public void carregaFuncionario() {
		colunaFuncionario.setCellValueFactory(new PropertyValueFactory<>("nome"));
		tvFuncionario.refresh();
		ObservableList<Funcionario> obFuncionario = FXCollections.observableArrayList(Cadastro.funcionarios);
		tvFuncionario.setItems(obFuncionario);
		
	}
	
	@FXML
	public void carregaAgenda() {
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
		DaoFuncionario.carregaAgendaFuncionario(dpData.getValue());
		dpDataExcluir.setValue(dpData.getValue());
		tvAgenda.refresh();
		ObservableList<Funcionario> obAgenda = FXCollections.observableArrayList(Cadastro.funcionarios);
		tvAgenda.setItems(obAgenda);
	}
	
	private void populaTabela() {
		obAgendamento = FXCollections.observableArrayList(Cadastro.agendamentos);
		colunaCliente.setCellValueFactory(new PropertyValueFactory<>("cliente"));
		colunaHorario.setCellValueFactory(new PropertyValueFactory<>("horario"));
		colunaExcluir.setCellValueFactory(new PropertyValueFactory<>("select"));
		colunaFuncionarioAgendamento.setCellValueFactory(new PropertyValueFactory<>("funcionario"));
		tvAgendamento.setItems(obAgendamento);
	}

	// a��es de bot�es

	@FXML
	public void onBtPesquisaAgendamento() {
		Carregar.carregaAgendamento(dpDataExcluir.getValue(), tfCliente.getText());
		tfCliente.clear();
		populaTabela();
	}

	@FXML
	public void onBtExcluirAgendamento() {
		if (Alerts.showAlertExclusao()) {
			ObservableList<Agendamento> obExcluirAgendamento = FXCollections.observableArrayList();
			for (Agendamento age : obAgendamento) {
				if (age.getSelect().isSelected()) {
					obExcluirAgendamento.add(age);
					Excluir.excluirAgendamento(age, dpDataExcluir.getValue());
				}
			}
			obAgendamento.removeAll(obExcluirAgendamento);
			Cadastro.agendamentos.removeAll(obExcluirAgendamento);
			carregaAgenda();
		}
	}

	//m�todos avulsos
	
	public void retornaInformacaoAgenda() {
		funTemp = this.tvAgenda.getSelectionModel().getSelectedItem();
		dpDataTemp = this.dpData.getValue();
		tvAgendaTemp = this.tvAgenda;
		tvFuncionarioTemp = this.tvFuncionario;
		tfClienteTemp = this.tfCliente;
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		dpData.setValue(LocalDate.now());
		Carregar.carregarBase();
		carregaFuncionario();
		bindAutoCompleteCliente = TextFields.bindAutoCompletion(tfCliente, Cadastro.clientes);
		dpDataExcluir.setValue(LocalDate.now());
		carregaAgenda();
	}
}
