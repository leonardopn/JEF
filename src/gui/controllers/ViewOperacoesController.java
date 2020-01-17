package gui.controllers;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.ResourceBundle;

import gui.util.Alerts;
import gui.util.Notificacoes;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import model.collection.Colecao;
import model.collection.entities.Operacao;
import model.dao.DaoOperacao;

public class ViewOperacoesController implements Initializable {

	ObservableList<Operacao> obOperacao;
	ObservableList<Integer> obAnos;
	private int mes;
	
	private static Scene receita;
	private static Stage stageReceita = new Stage();
	private static Scene despesa; 
	private static Stage stageDespesa = new Stage();

	final ToggleGroup group1 = new ToggleGroup();
	final ToggleGroup group2 = new ToggleGroup();

	@FXML
	private TableView<Operacao> tvOperacoes;

	@FXML
	private TableColumn<Operacao, Integer> colunaId;

	@FXML
	private TableColumn<Operacao, String> colunaDescricao;

	@FXML
	private TableColumn<Operacao, String> colunaData;

	@FXML
	private TableColumn<Operacao, Double> colunaReceita;

	@FXML
	private TableColumn<Operacao, Double> colunaDespesa;

	@FXML
	private TableColumn<Operacao, String> ColunaFormaPagamento;

	@FXML
	private TableColumn<Operacao, CheckBox> colunaExcluir;

	@FXML
	private Label lbTotalDia;

	@FXML
	private Label lbStatus;

	@FXML
	private ProgressIndicator piStatus;

	@FXML
	private Label labelTotalMesAno;

	@FXML
	private Label labelCifrao2;

	@FXML
	private Label labelTotalDia;

	@FXML
	private Label labelCifrao;

	@FXML
	private Label lbReceitaDia;

	@FXML
	private Label lbDespesaDia;

	@FXML
	private Label lbResumoMes;

	@FXML
	private Label lbTotalMes;

	@FXML
	private Label lbReceitaMes;

	@FXML
	private Label lbDespesaMes;

	@FXML
	private DatePicker dpData;

	@FXML
	private TextField tfBusca;

	@FXML
	private Button btExcluir;

	@FXML
	private Button btPesquisar;

	@FXML
	private CheckBox cbData;

	@FXML
	private RadioButton rbId;

	@FXML
	private RadioButton rbTodos;

	@FXML
	private RadioButton rbTodos1;

	@FXML
	private RadioButton rbFormaPagamento;

	@FXML
	private RadioButton rbDespesa;

	@FXML
	private RadioButton rbReceita;

	@FXML
	private RadioButton rbDescricao;

	@FXML
	private Button btSaida;

	@FXML
	private Button btEntrada;

	@FXML
	private Spinner<Integer> spinAno;

	@FXML
	private Button btJaneiro;

	@FXML
	private Button btFevereiro;

	@FXML
	private Button btMarco;

	@FXML
	private Button btAbril;

	@FXML
	private Button btMaio;

	@FXML
	private Button btJunho;

	@FXML
	private Button btJulho;

	@FXML
	private Button btAgosto;

	@FXML
	private Button btStembro;

	@FXML
	private Button btOutubro;

	@FXML
	private Button btNovembro;

	@FXML
	private Button btDezembro;

	@FXML
	private Button btAnual;

	@FXML
	void onBtEntradaAction() {
		if(!(stageReceita.isShowing())) {
			try {
				Parent fxmlReceita = FXMLLoader.load(getClass().getResource("/gui/view/ViewReceita.fxml"));
				receita = new Scene(fxmlReceita);
				stageReceita.setScene(receita);
				stageReceita.centerOnScreen();
				stageReceita.getIcons().add(new Image(getClass().getResourceAsStream("/model/images/icon.png")));
				stageReceita.setTitle("JEF - Receita");
				stageReceita.showAndWait();
				buscaOperacoes();
				DaoOperacao.carregaOperacao(spinAno.getValue(), mes);
				calculaMontante();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		else {
			stageReceita.requestFocus();
		}
	}

	@FXML
	void onBtSaidaAction() {
		if(!(stageReceita.isShowing())) {
			try {
				Parent fxmlReceita = FXMLLoader.load(getClass().getResource("/gui/view/ViewDespesa.fxml"));
				despesa = new Scene(fxmlReceita);
				stageDespesa.setScene(despesa);
				stageDespesa.centerOnScreen();
				stageDespesa.getIcons().add(new Image(getClass().getResourceAsStream("/model/images/icon.png")));
				stageDespesa.setTitle("JEF - Despesa");
				stageDespesa.showAndWait();
				buscaOperacoes();
				DaoOperacao.carregaOperacao(spinAno.getValue(), mes);
				calculaMontante();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		else {
			stageReceita.requestFocus();
		}
	}

	public void liberaData() {
		if (cbData.isSelected()) {
			dpData.setDisable(false);
		} else {
			dpData.setValue(null);
			dpData.setDisable(true);
		}
	}

	public void carregaOperacoes() {
		tvOperacoes.setItems(null);
		obOperacao = FXCollections.observableArrayList(Colecao.operacoes);
		tvOperacoes.setItems(obOperacao);
	}

	public void carregaAno() {
		obAnos = FXCollections.observableArrayList(DaoOperacao.carregaAno());
		SpinnerValueFactory<Integer> valueFactory = //
				new SpinnerValueFactory.ListSpinnerValueFactory<Integer>(obAnos);

		valueFactory.setValue(LocalDate.now().getYear());
		spinAno.setValueFactory(valueFactory);

	}

	public void descoloreBotoes() {
		btJaneiro.setStyle("-fx-background-color: #dddddd");
		btFevereiro.setStyle("-fx-background-color: #dddddd");
		btMarco.setStyle("-fx-background-color: #dddddd");
		btAbril.setStyle("-fx-background-color: #dddddd");
		btMaio.setStyle("-fx-background-color: #dddddd");
		btJunho.setStyle("-fx-background-color: #dddddd");
		btJulho.setStyle("-fx-background-color: #dddddd");
		btAgosto.setStyle("-fx-background-color: #dddddd");
		btStembro.setStyle("-fx-background-color: #dddddd");
		btOutubro.setStyle("-fx-background-color: #dddddd");
		btNovembro.setStyle("-fx-background-color: #dddddd");
		btDezembro.setStyle("-fx-background-color: #dddddd");
		btAnual.setStyle("-fx-background-color: #dddddd");
	}

	public void selecionaMes() {
		descoloreBotoes();

		rbTodos.setSelected(true);
		rbTodos1.setSelected(true);
		bloqComponents();

		cbData.setSelected(false);
		liberaData();

		tfBusca.setText("");

		mes = 0;
		if (btJaneiro.isFocused()) {
			mes = 1;
			btJaneiro.setStyle("-fx-background-color:  #a6fca6");
			lbResumoMes.setText("Resumo do Mês");
			labelTotalMesAno.setText("Total do Mês:");
		}
		if (btFevereiro.isFocused()) {
			mes = 2;
			btFevereiro.setStyle("-fx-background-color:  #a6fca6");
			lbResumoMes.setText("Resumo do Mês");
			labelTotalMesAno.setText("Total do Mês:");
		}
		if (btMarco.isFocused()) {
			mes = 3;
			btMarco.setStyle("-fx-background-color:  #a6fca6");
			lbResumoMes.setText("Resumo do Mês");
			labelTotalMesAno.setText("Total do Mês:");
		}
		if (btAbril.isFocused()) {
			mes = 4;
			btAbril.setStyle("-fx-background-color:  #a6fca6");
			lbResumoMes.setText("Resumo do Mês");
			labelTotalMesAno.setText("Total do Mês:");
		}
		if (btMaio.isFocused()) {
			mes = 5;
			btMaio.setStyle("-fx-background-color:  #a6fca6");
			lbResumoMes.setText("Resumo do Mês");
			labelTotalMesAno.setText("Total do Mês:");
		}
		if (btJunho.isFocused()) {
			mes = 6;
			btJunho.setStyle("-fx-background-color:  #a6fca6");
			lbResumoMes.setText("Resumo do Mês");
			labelTotalMesAno.setText("Total do Mês:");
		}
		if (btJulho.isFocused()) {
			mes = 7;
			btJulho.setStyle("-fx-background-color:  #a6fca6");
			lbResumoMes.setText("Resumo do Mês");
			labelTotalMesAno.setText("Total do Mês:");
		}
		if (btAgosto.isFocused()) {
			mes = 8;
			btAgosto.setStyle("-fx-background-color:  #a6fca6");
			lbResumoMes.setText("Resumo do Mês");
			labelTotalMesAno.setText("Total do Mês:");
		}
		if (btStembro.isFocused()) {
			mes = 9;
			btStembro.setStyle("-fx-background-color:  #a6fca6");
			lbResumoMes.setText("Resumo do Mês");
			labelTotalMesAno.setText("Total do Mês:");
		}
		if (btOutubro.isFocused()) {
			mes = 10;
			btOutubro.setStyle("-fx-background-color:  #a6fca6");
			lbResumoMes.setText("Resumo do Mês");
			labelTotalMesAno.setText("Total do Mês:");
		}
		if (btNovembro.isFocused()) {
			mes = 11;
			btNovembro.setStyle("-fx-background-color:  #a6fca6");
			lbResumoMes.setText("Resumo do Mês");
			labelTotalMesAno.setText("Total do Mês:");
		}
		if (btDezembro.isFocused()) {
			mes = 12;
			btDezembro.setStyle("-fx-background-color:  #a6fca6");
			lbResumoMes.setText("Resumo do Mês");
			labelTotalMesAno.setText("Total do Mês:");
		}
		if (btAnual.isFocused() || spinAno.isFocused()) {
			mes = 0;
			btAnual.setStyle("-fx-background-color:  #a6fca6");
			lbResumoMes.setText("Resumo do Ano");
			labelTotalMesAno.setText("Total do Ano:");
		}

		DaoOperacao.carregaOperacao(spinAno.getValue(), mes);
		carregaOperacoes();
		calculaMontante();
	}

	public void selecionaMesPadrao(int mes) {
		descoloreBotoes();
		this.mes = mes;
		if (mes == 1) {
			btJaneiro.setStyle("-fx-background-color:  #a6fca6");
		}
		if (mes == 2) {
			btFevereiro.setStyle("-fx-background-color:  #a6fca6");
		}
		if (mes == 3) {
			btMarco.setStyle("-fx-background-color:  #a6fca6");
		}
		if (mes == 4) {
			btAbril.setStyle("-fx-background-color:  #a6fca6");
		}
		if (mes == 5) {
			btMaio.setStyle("-fx-background-color:  #a6fca6");
		}
		if (mes == 6) {
			btJunho.setStyle("-fx-background-color:  #a6fca6");
		}
		if (mes == 7) {
			btJulho.setStyle("-fx-background-color:  #a6fca6");
		}
		if (mes == 8) {
			btAgosto.setStyle("-fx-background-color:  #a6fca6");
		}
		if (mes == 9) {
			btStembro.setStyle("-fx-background-color:  #a6fca6");
		}
		if (mes == 10) {
			btOutubro.setStyle("-fx-background-color:  #a6fca6");
		}
		if (mes == 11) {
			btNovembro.setStyle("-fx-background-color:  #a6fca6");
		}
		if (mes == 12) {
			btDezembro.setStyle("-fx-background-color:  #a6fca6");
		}
	}

	public void calculaMontante() {
		double despesaMes = 0.0;
		double receitaMes = 0.0;
		double despesaDia = 0.0;
		double receitaDia = 0.0;
		double totalMes = 0.0;
		double totalDia = 0.0;

		DateTimeFormatter formataData = DateTimeFormatter.ofPattern("dd/MM/yyyy");

		for (Operacao op : Colecao.operacoes) {
			if (op.getData().equals(formataData.format(LocalDate.now()))) {
				if (op.getReceita() > 0.0) {
					receitaDia += op.getReceita();
					receitaMes += op.getReceita();
				} else {
					despesaDia += op.getDespesa();
					despesaMes += op.getDespesa();
				}
			} else {
				if (op.getReceita() > 0.0) {
					receitaMes += op.getReceita();
				} else {
					despesaMes += op.getDespesa();
				}
			}
		}

		totalMes += despesaMes + receitaMes;
		totalDia += despesaDia + receitaDia;

		lbDespesaMes.setText(String.valueOf(String.format("%.2f", despesaMes)));
		lbReceitaMes.setText(String.valueOf(String.format("%.2f", receitaMes)));
		lbTotalMes.setText(String.valueOf(String.format("%.2f", totalMes)));
		lbDespesaDia.setText(String.valueOf(String.format("%.2f", despesaDia)));
		lbReceitaDia.setText(String.valueOf(String.format("%.2f", receitaDia)));
		lbTotalDia.setText(String.valueOf(String.format("%.2f", totalDia)));

		if (totalMes > 0.0) {// Verde
			lbTotalMes.setStyle("-fx-text-fill: #0b611b");
			labelCifrao.setStyle("-fx-text-fill: #0b611b");
			labelTotalMesAno.setStyle("-fx-text-fill: #0b611b");

		} else {
			if (totalMes < 0.0) {// Vermelho
				lbTotalMes.setStyle("-fx-text-fill: #f20303");
				labelCifrao.setStyle("-fx-text-fill: #f20303");
				labelTotalMesAno.setStyle("-fx-text-fill: #f20303");
			} else {// Amarelo
				lbTotalMes.setStyle("-fx-text-fill: #edae00");
				labelCifrao.setStyle("-fx-text-fill: #edae00");
				labelTotalMesAno.setStyle("-fx-text-fill: #edae00");
			}

		}

		if (totalDia > 0.0) {// Verde
			labelTotalDia.setStyle("-fx-text-fill: #0b611b");
			labelCifrao2.setStyle("-fx-text-fill: #0b611b");
			lbTotalDia.setStyle("-fx-text-fill: #0b611b");
		} else {
			if (totalDia < 0.0) {// Vermelho
				lbTotalDia.setStyle("-fx-text-fill: #f20303");
				labelCifrao2.setStyle("-fx-text-fill: #f20303");
				labelTotalDia.setStyle("-fx-text-fill: #f20303");
			} else {// Amarelo
				lbTotalDia.setStyle("-fx-text-fill: #edae00");
				labelCifrao2.setStyle("-fx-text-fill: #edae00");
				labelTotalDia.setStyle("-fx-text-fill:#edae00");
			}
		}
	}

	public void buscaOperacoes() {
		int grupo1;
		int grupo2;

		if (rbDescricao.isSelected()) {
			grupo1 = 1;
		} else {
			if (rbId.isSelected()) {
				grupo1 = 2;
			} else {
				if (rbFormaPagamento.isSelected()) {
					grupo1 = 3;
				} else {
					grupo1 = 4;
				}
			}
		}

		if (rbTodos1.isSelected()) {
			grupo2 = 1;
		} else {
			if (rbDespesa.isSelected()) {
				grupo2 = 2;
			} else {
				grupo2 = 3;
			}
		}

		ArrayList<Operacao> opTemp = new ArrayList<>();
		if (!(dpData.isDisable())) {
			opTemp = DaoOperacao.carregaBusca(tfBusca.getText(), grupo1, grupo2, dpData.getValue().getMonthValue(),
					dpData.getValue().getMonthValue(), dpData.getValue().getYear(), dpData.getValue().getDayOfMonth());
		} else {
			if (mes == 0) {
				opTemp = DaoOperacao.carregaBusca(tfBusca.getText(), grupo1, grupo2, 1, 12, spinAno.getValue(), 0);
			} else {
				opTemp = DaoOperacao.carregaBusca(tfBusca.getText(), grupo1, grupo2, mes, mes, spinAno.getValue(), 0);
			}
		}

		tvOperacoes.setItems(null);
		obOperacao = FXCollections.observableArrayList(opTemp);
		tvOperacoes.setItems(obOperacao);
	}

	public void setaRadioGrups() {
		rbDescricao.setToggleGroup(group1);
		rbFormaPagamento.setToggleGroup(group1);
		rbId.setToggleGroup(group1);
		rbTodos.setToggleGroup(group1);

		rbReceita.setToggleGroup(group2);
		rbDespesa.setToggleGroup(group2);
		rbTodos1.setToggleGroup(group2);
	}

	public void bloqComponents() {
		if (rbTodos.isSelected()) {
			tfBusca.setDisable(true);
		} else {
			tfBusca.setDisable(false);
		}

		if (rbId.isSelected()) {
			rbDespesa.setDisable(true);
			rbReceita.setDisable(true);
			rbTodos1.setSelected(true);
		} else {
			rbDespesa.setDisable(false);
			rbReceita.setDisable(false);
		}

	}

	public void excluiOperacao() {
		if (Alerts.showAlertGenerico("AVISO!", "Deseja excluir está operacao?",
				"*Sera feito um novo cálculo de valores!")) {
			for (Operacao op : tvOperacoes.getItems()) {
				if (op.getSelect().isSelected()) {
					DaoOperacao.excluiOperacao(op.getId());
				}
			}
			buscaOperacoes();
			DaoOperacao.carregaOperacao(spinAno.getValue(), mes);
			calculaMontante();
		} else {
			Notificacoes.mostraNotificacao("AVISO", "Operação cancelada!");
		}
	}

	public static Scene getDespesa() {
		return despesa;
	}

	public static Stage getStageDespesa() {
		return stageDespesa;
	}

	public static Scene getReceita() {
		return receita;
	}

	public static Stage getStageReceita() {
		return stageReceita;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		colunaId.setCellValueFactory(new PropertyValueFactory<>("id"));
		colunaData.setCellValueFactory(new PropertyValueFactory<>("data"));
		colunaDescricao.setCellValueFactory(new PropertyValueFactory<>("obs"));
		colunaDespesa.setCellValueFactory(new PropertyValueFactory<>("despesa"));
		colunaExcluir.setCellValueFactory(new PropertyValueFactory<>("select"));
		colunaReceita.setCellValueFactory(new PropertyValueFactory<>("receita"));
		ColunaFormaPagamento.setCellValueFactory(new PropertyValueFactory<>("formaPagamento"));
		selecionaMesPadrao(LocalDate.now().getMonthValue());
		DaoOperacao.carregaOperacao(LocalDate.now().getYear(), LocalDate.now().getMonthValue());
		liberaData();
		carregaOperacoes();
		calculaMontante();
		carregaAno();
		setaRadioGrups();
		bloqComponents();
	}
}
