package gui.controller;

import java.net.URL;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

import application.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Paint;
import model.entities.Caixa;
import model.entities.Cliente;
import model.entities.Funcionario;
import model.entities.Transacao;
import model.services.Cadastro;
import model.services.Carregar;
import model.services.Salvar;

public class ViewCaixaController implements Initializable {

	ObservableList<Cliente> obCliente;

	ObservableList<Funcionario> obFuncionario;

	ObservableList<Transacao> obTable;

	ObservableList<Transacao> obTableTemp;

	ObservableList<String> obFormaPagamento;
	
	double total = 0;
	double totalDinheiro = 0;
	double totalCartao = 0;

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
	private TextField tfId;

	@FXML
	private TextField tfValor;

	@FXML
	private TextField tfValorServico;

	@FXML
	private TextField tfDinheiroDado;

	@FXML
	private ChoiceBox<Cliente> cbCliente;

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
	private TableColumn<Transacao, LocalDate> colunaData;

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
		Main.trocaTela("main");
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
	
	public void bloqueiaAdicaoExclusao() {
		if(Caixa.isStatus() != true) {
			btEnviarTransacao.setDisable(true);
			btExcluir.setDisable(true);
		}
		else {
			btEnviarTransacao.setDisable(false);
			btExcluir.setDisable(false);
		}
	}

	@FXML
	public void onBtAtualizarAction() {
		carregaCliente();
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
			bloqueiaAdicaoExclusao();
		}
		else {
			btAbrirFecharCaixa.setTextFill(Paint.valueOf("#10bf24"));
			btAbrirFecharCaixa.setText("Abrir Caixa!");
			lbStatus.setTextFill(Paint.valueOf("#ff0606"));
			lbStatus.setText("Fechado");
			Caixa.setStatus(false);
			Salvar.salvarStatus();
			bloqueiaAdicaoExclusao();
			calculaCaixa();
		}

	}
	
	public void calculaCaixa() {
		total = 0;
		totalDinheiro = 0;
		totalCartao = 0;
		for(Transacao tran : Caixa.caixa) {
			if(tran.getData().equals(LocalDate.now())) {
				total += tran.getValor();
				lbValorTotal.setText("R$ "+String.valueOf(total));
				if(tran.getFormaPagamento().equals("Dinheiro")) {
					totalDinheiro += tran.getValor();
					lbValorDinheiro.setText("R$ "+String.valueOf(totalDinheiro));
				}
				else {
					totalCartao += tran.getValor();
					lbValorCartao.setText("R$ "+String.valueOf(totalCartao));
					
				}
			}
		}
		
	}

	public void carregaCliente() {
		obCliente = FXCollections.observableArrayList(Cadastro.clientes);
		cbCliente.setItems(obCliente);
	}

	public void carregaFuncionario() {
		obFuncionario = FXCollections.observableArrayList(Cadastro.funcionarios);
		cbFuncionario.setItems(obFuncionario);
	}

	public void carregaFormaPagamento() {
		List<String> listMetPag = Arrays.asList("Dinheiro", "Cartão");
		obFormaPagamento = FXCollections.observableArrayList(listMetPag);
		cbFormaPagamento.setItems(obFormaPagamento);
	}

	public void carregaTable() {
		obTable = FXCollections.observableArrayList(Caixa.caixaTemp);
		tvTransacao.setItems(obTable);
	}

	@FXML
	public void onBtEnviarTransacaoAction() {
		int id = Integer.parseInt(tfId.getText());
		String cliente = cbCliente.getValue().getNome();
		String fun = cbFuncionario.getValue().getNome();
		LocalDate data = dpData.getValue();
		double valor = Double.parseDouble(tfValor.getText());
		String formaPaga = cbFormaPagamento.getValue();
		Transacao tran = new Transacao(id, valor, data, cliente, fun, formaPaga);
		Caixa.verificaTransacao(tran);
		Caixa.caixa.add(tran);
		Salvar.salvarTransacao();
		carregaTransacao();
		carregaTable();
	}

	public void excluirTransacao() {
		ObservableList<Transacao> obExcluirTransacao = FXCollections.observableArrayList();
		for (Transacao tran : obTable) {
			if (tran.getSelect().isSelected()) {
				obExcluirTransacao.add(tran);
			}
		}
		Caixa.caixa.removeAll(obExcluirTransacao);
		Caixa.caixaTemp.removeAll(obExcluirTransacao);
		System.out.println("\nteste\n");
		for (Transacao tran : Caixa.caixaTemp) {
			System.out.println("\n---------\n");
			System.out.println(tran);
		}
		System.out.println("\nteste2\n");
		for (Transacao tran : Caixa.caixa) {
			System.out.println("\n---------\n");
			System.out.println(tran);
		}
		salvarTransacaoExcluidos();
		carregaTable();
	}

	public void salvarTransacaoExcluidos() {
		Salvar.salvarTransacaoExcluidos(dpSelecao.getValue());
	}

	public void carregaTransacao() {
		carregaTable();
		Carregar.carregaTransacaoExpecifica(dpSelecao.getValue());
		carregaTable();
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		carregaCliente();
		carregaFuncionario();
		carregaFormaPagamento();
		mudaCaixa();
		bloqueiaAdicaoExclusao();
		dpData.setValue(LocalDate.now());
		dpSelecao.setValue(LocalDate.now());
		colunaId.setCellValueFactory(new PropertyValueFactory<>("id"));
		colunaData.setCellValueFactory(new PropertyValueFactory<>("data"));
		colunaCliente.setCellValueFactory(new PropertyValueFactory<>("cliente"));
		colunaAtendente.setCellValueFactory(new PropertyValueFactory<>("atendente"));
		colunaMeioPagamento.setCellValueFactory(new PropertyValueFactory<>("formaPagamento"));
		colunaValor.setCellValueFactory(new PropertyValueFactory<>("valor"));
		colunaSelect.setCellValueFactory(new PropertyValueFactory<>("select"));
		carregaTransacao();
	}
}
