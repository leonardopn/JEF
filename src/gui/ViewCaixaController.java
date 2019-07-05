package gui;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
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
import model.entities.Caixa;
import model.entities.Cliente;
import model.entities.Funcionario;
import model.entities.Transacao;
import model.services.Cadastro;
import model.services.IdentificadorSO;
import model.services.Salvar;

public class ViewCaixaController implements Initializable {

	ObservableList<Cliente> obCliente;

	ObservableList<Funcionario> obFuncionario;

	ObservableList<Transacao> obTable;

	ObservableList<Transacao> obTableTemp;

	ObservableList<String> obFormaPagamento;

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
	private Label lbValorCartão;

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

	@FXML
	public void onBtAtualizarAction() {
		carregaCliente();
		carregaFuncionario();
		carregaFormaPagamento();

	}

	@FXML
	public void onBtAbrirFecharCaixaAction() {
		btAbrirFecharCaixa.setText("Fechar Caixa");

		carregaTable();
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
			System.out.println();
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
		File arquivoTransacao;

		String caminho = System.getProperty("user.home") + File.separatorChar + "Documents" + File.separatorChar
				+ "teste" + File.separatorChar + "transacoes" + File.separatorChar + "transacoes.csv";
		if (IdentificadorSO.sistema() == "linux") {
			caminho = System.getProperty("user.home") + File.separatorChar + "Documentos" + File.separatorChar + "teste"
					+ File.separatorChar + "transacoes" + File.separatorChar + "transacoes.csv";
		}
		arquivoTransacao = new File(caminho);

		try (BufferedWriter bwTransacao = new BufferedWriter(new FileWriter(arquivoTransacao))) {
			for (Transacao tran2 : Caixa.caixa) {
				bwTransacao.write(tran2.getId() + ";" + tran2.getValor() + ";" + String.valueOf(tran2.getData()) + ";"
						+ tran2.getCliente() + ";" + tran2.getAtendente() + ";" + tran2.getFormaPagamento() + "\n");
			}
		} catch (IOException e) {
			System.out.println("Ocorreu um erro ao salvar um arquivo de transação: " + e.getMessage());
		}

		caminho = System.getProperty("user.home") + File.separatorChar + "Documents" + File.separatorChar + "teste"
				+ File.separatorChar + "transacoes" + File.separatorChar + dpSelecao.getValue() + ".csv";
		if (IdentificadorSO.sistema() == "linux") {
			caminho = System.getProperty("user.home") + File.separatorChar + "Documentos" + File.separatorChar + "teste"
					+ File.separatorChar + "transacoes" + File.separatorChar + dpSelecao.getValue() + ".csv";
		}
		arquivoTransacao = new File(caminho);
		if (arquivoTransacao.exists()) {
			arquivoTransacao.delete();
		}
		try (BufferedWriter bwTransacao = new BufferedWriter(new FileWriter(arquivoTransacao))) {
			for (Transacao tran : Caixa.caixaTemp) {
				bwTransacao.write(tran.getId() + ";" + tran.getValor() + ";" + String.valueOf(tran.getData()) + ";"
						+ tran.getCliente() + ";" + tran.getAtendente() + ";" + tran.getFormaPagamento() + "\n");

			}
		} catch (IOException e) {
			System.out.println("Ocorreu um erro ao salvar um arquivo de transação: " + e.getMessage());
		}
	}

	public void carregaTransacao() {
		Caixa.caixaTemp.clear();
		carregaTable();
		String linha = "";
		String caminho = System.getProperty("user.home") + File.separator + "Documents" + File.separator + "teste"
				+ File.separator + "transacoes" + File.separator + dpSelecao.getValue() + ".csv";
		if (IdentificadorSO.sistema() == "linux") {
			caminho = System.getProperty("user.home") + File.separatorChar + "Documentos" + File.separatorChar + "teste"
					+ File.separator + "transacoes" + File.separator + dpSelecao.getValue() + ".csv";
		}
		File file = new File(caminho);
		if (file.exists()) {
			try (BufferedReader brTransacao = new BufferedReader(new FileReader(caminho));) {
				while ((linha = brTransacao.readLine()) != null) {
					String[] linhaTransacao = linha.split(";");
					Transacao tran = new Transacao(Integer.parseInt(linhaTransacao[0]),
							Double.parseDouble(linhaTransacao[1]), LocalDate.parse(linhaTransacao[2]),
							linhaTransacao[3], linhaTransacao[4], linhaTransacao[5]);
					Caixa.caixaTemp.add(tran);
					carregaTable();
				}
				brTransacao.close();
			} catch (IOException e) {
				System.out.println("N�o existe arquivo com esse nome: " + e.getMessage());
			}
		}
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		carregaCliente();
		carregaFuncionario();
		carregaFormaPagamento();
		dpData.setValue(LocalDate.now());
		dpSelecao.setValue(LocalDate.now());
		carregaTransacao();
		colunaId.setCellValueFactory(new PropertyValueFactory<>("id"));
		colunaData.setCellValueFactory(new PropertyValueFactory<>("data"));
		colunaCliente.setCellValueFactory(new PropertyValueFactory<>("cliente"));
		colunaAtendente.setCellValueFactory(new PropertyValueFactory<>("atendente"));
		colunaMeioPagamento.setCellValueFactory(new PropertyValueFactory<>("formaPagamento"));
		colunaValor.setCellValueFactory(new PropertyValueFactory<>("valor"));
		colunaSelect.setCellValueFactory(new PropertyValueFactory<>("select"));
		carregaTable();
	}
}
