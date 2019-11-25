package gui.controllers;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

import org.controlsfx.control.textfield.AutoCompletionBinding;
import org.controlsfx.control.textfield.TextFields;

import application.Main;
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
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Paint;
import model.entities.Caixa;
import model.entities.Cliente;
import model.entities.Funcionario;
import model.entities.Transacao;
import model.services.Atualizar;
import model.services.Cadastro;
import model.services.Carregar;
import model.services.Excluir;
import model.services.Salvar;

public class ViewCaixaController implements Initializable {

	ObservableList<Cliente> obCliente;

	ObservableList<Transacao> obTable;

	ObservableList<Transacao> obTableTemp;

	ObservableList<String> obFormaPagamento;
	
	public static TextField tfClienteTemp;
	
	public static ChoiceBox<Funcionario> cbFuncionarioTemp;
	
	public static AutoCompletionBinding bindAutoCompleteCliente;

	@FXML
	private Button btVoltar;

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
		if(Caixa.isStatus() != true) {
			btEnviarTransacao.setDisable(true);
			btExcluir.setDisable(true);
			tfCliente.setDisable(true);
			cbFuncionario.setDisable(true);
			dpData.setDisable(true);
			tfValor.setDisable(true);
			cbFormaPagamento.setDisable(true);
		}
		else {
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
		if(Caixa.isStatus() == false) {
			btAbrirFecharCaixa.setTextFill(Paint.valueOf("#10bf24"));
			btAbrirFecharCaixa.setText("Abrir Caixa!");
			lbStatus.setTextFill(Paint.valueOf("#ff0606"));
			lbStatus.setText("Fechado");
		}
		else {
			btAbrirFecharCaixa.setTextFill(Paint.valueOf("#ff0606"));
			btAbrirFecharCaixa.setText("Fechar Caixa!");
			lbStatus.setTextFill(Paint.valueOf("#10bf24"));
			lbStatus.setText("Aberto");
		}
	}

	@FXML
	public void onBtAbrirFecharCaixaAction() {
		if(Caixa.isStatus() == false) {
			btAbrirFecharCaixa.setTextFill(Paint.valueOf("#ff0606"));
			btAbrirFecharCaixa.setText("Fechar Caixa!");
			lbStatus.setTextFill(Paint.valueOf("#10bf24"));
			lbStatus.setText("Aberto");
			Caixa.setStatus(true);
			Salvar.salvarStatus();
			bloqueio();
		}
		else {
			btAbrirFecharCaixa.setTextFill(Paint.valueOf("#10bf24"));
			btAbrirFecharCaixa.setText("Abrir Caixa!");
			lbStatus.setTextFill(Paint.valueOf("#ff0606"));
			lbStatus.setText("Fechado");
			Caixa.setStatus(false);
			Salvar.salvarStatus();
			bloqueio();
		}

	}
	
	public void calculaCaixa() {
		double total = 0.00;
		double totalDinheiro = 0.00;
		double totalCartao = 0.00;
		lbValorTotal.setText(String.valueOf(total));
		lbValorDinheiro.setText(String.valueOf(totalDinheiro));
		lbValorCartao.setText(String.valueOf(totalCartao));
		for(Transacao tran : Caixa.caixa) {
			total += tran.getValor();
			if(tran.getFormaPagamento().equals("Dinheiro")) {
				totalDinheiro += tran.getValor();
			}
			else {
				totalCartao += tran.getValor();
			}
		}
		lbValorTotal.setText(String.valueOf(total));
		lbValorDinheiro.setText(String.valueOf(totalDinheiro));
		lbValorCartao.setText(String.valueOf(totalCartao));
		Salvar.salvarCaixa(dpSelecao.getValue(), Double.parseDouble(lbValorTotal.getText()), Double.parseDouble(lbValorCartao.getText()), Double.parseDouble(lbValorDinheiro.getText()));
	}

	public void carregaFuncionario() {
		ObservableList<Funcionario> obFuncionario = FXCollections.observableArrayList(Cadastro.funcionarios);
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

	@FXML
	public void onBtEnviarTransacaoAction() {
		
		DateTimeFormatter localDateFormatada = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		int id;
		String cliente = tfCliente.getText();
		String fun = cbFuncionario.getValue().getCpf();
		String data = localDateFormatada.format((dpData.getValue()));
		double valor = Double.parseDouble(tfValor.getText().replaceAll(",", "."));
		String formaPaga = cbFormaPagamento.getValue();
		id = Salvar.salvarTransacao(tfCliente, cbFuncionario, dpData.getValue(), tfValor, cbFormaPagamento);
		Transacao tran = new Transacao(id, valor, cliente, fun, formaPaga, data);
		System.out.println(tran);
		Caixa.verificaTransacao(tran);
		Caixa.caixa.add(tran);
		valor = (valor*0.40);
		Atualizar.atualizarSalario(fun, (valor));
		Carregar.carregaFuncionario();
		carregaTransacao();
		calculaCaixa();	
	}

	public void excluirTransacao() {
		ObservableList<Transacao> obExcluirTransacao = FXCollections.observableArrayList();
		for (Transacao tran : obTable) {
			if (tran.getSelect().isSelected()) {
				obExcluirTransacao.add(tran);
				double valor = (tran.getValor()*0.40);			
				valor = valor - (valor*2);
				for (Funcionario fun: Cadastro.funcionarios) {
					if(fun.getNome().equals(tran.getAtendente())) {
						Atualizar.atualizarSalario(fun.getCpf(), valor);
					}
				}		
				Carregar.carregaFuncionario();
				Excluir.excluirTransacao(tran);
			}
		}
		Caixa.caixa.removeAll(obExcluirTransacao);
		carregaTable();
		calculaCaixa();
	}


	public void carregaTransacao() {
			Carregar.carregaTransacaoExpecifica(dpSelecao.getValue());
			carregaTable();
			calculaCaixa();
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
		bindAutoCompleteCliente = TextFields.bindAutoCompletion(tfCliente, Cadastro.clientes);
	}
}
