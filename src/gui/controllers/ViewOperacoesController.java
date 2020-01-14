package gui.controllers;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import model.collection.Colecao;
import model.collection.entities.Operacao;
import model.dao.DaoOperacao;

public class ViewOperacoesController implements Initializable {

	ObservableList<Operacao> obOperacao;
	ObservableList<Integer> obAnos;

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
	private Button btPesquisarNow;

	@FXML
	private Button btExcluir;

	@FXML
	private Button btPesquisar;

	@FXML
	private CheckBox cbData;

	@FXML
	private CheckBox cbId;

	@FXML
	private CheckBox cbFormaPagamento;

	@FXML
	private CheckBox cbDespesa;

	@FXML
	private CheckBox cbReceita;

	@FXML
	private CheckBox cbDescricao;

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

	}

	@FXML
	void onBtSaidaAction() {

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
		int mes = 0;
		if (btJaneiro.isFocused()) {
			mes = 1;
			btJaneiro.setStyle("-fx-background-color:  #a6fca6");
		}
		if (btFevereiro.isFocused()) {
			mes = 2;
			btFevereiro.setStyle("-fx-background-color:  #a6fca6");
		}
		if (btMarco.isFocused()) {
			mes = 3;
			btMarco.setStyle("-fx-background-color:  #a6fca6");
		}
		if (btAbril.isFocused()) {
			mes = 4;
			btAbril.setStyle("-fx-background-color:  #a6fca6");
		}
		if (btMaio.isFocused()) {
			mes = 5;
			btMaio.setStyle("-fx-background-color:  #a6fca6");
		}
		if (btJunho.isFocused()) {
			mes = 6;
			btJunho.setStyle("-fx-background-color:  #a6fca6");
		}
		if (btJulho.isFocused()) {
			mes = 7;
			btJulho.setStyle("-fx-background-color:  #a6fca6");
		}
		if (btAgosto.isFocused()) {
			mes = 8;
			btAgosto.setStyle("-fx-background-color:  #a6fca6");
		}
		if (btStembro.isFocused()) {
			mes = 9;
			btStembro.setStyle("-fx-background-color:  #a6fca6");
		}
		if (btOutubro.isFocused()) {
			mes = 10;
			btOutubro.setStyle("-fx-background-color:  #a6fca6");
		}
		if (btNovembro.isFocused()) {
			mes = 11;
			btNovembro.setStyle("-fx-background-color:  #a6fca6");
		}
		if (btDezembro.isFocused()) {
			mes = 12;
			btDezembro.setStyle("-fx-background-color:  #a6fca6");
		}
		if(btAnual.isFocused() || spinAno.isFocused()) {
			mes = 0;
			btAnual.setStyle("-fx-background-color:  #a6fca6");
		}
		
		DaoOperacao.carregaOperacao(spinAno.getValue(), mes);
		carregaOperacoes();
		calculaMontante();
	}
	
	public void selecionaMesPadrao(int mes) {
		descoloreBotoes();
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
			if(op.getData().equals(formataData.format(LocalDate.now()))) {
				if(op.getReceita()> 0.0) {
					receitaDia += op.getReceita();
					receitaMes += op.getReceita();
				}
				else {
					despesaDia += op.getDespesa();
					despesaMes += op.getDespesa();
				}
			}
			else {
				if(op.getReceita()> 0.0) {
					receitaMes += op.getReceita();
				}
				else {
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
		
		if(totalMes > 0.0) {//Verde
			lbTotalMes.setStyle("-fx-text-fill: #0b611b");
			labelCifrao.setStyle("-fx-text-fill: #0b611b");
			labelTotalMesAno.setStyle("-fx-text-fill: #0b611b");
			
		}
		else {
			if(totalMes < 0.0) {//Vermelho
				lbTotalMes.setStyle("-fx-text-fill: #f20303");	
				labelCifrao.setStyle("-fx-text-fill: #f20303");
				labelTotalMesAno.setStyle("-fx-text-fill: #f20303");
			}
			else {//Amarelo
				lbTotalMes.setStyle("-fx-text-fill: #edae00");	
				labelCifrao.setStyle("-fx-text-fill: #edae00");
				labelTotalMesAno.setStyle("-fx-text-fill: #edae00");
			}
			
		}
		
		if(totalDia > 0.0) {//Verde
			labelTotalDia.setStyle("-fx-text-fill: #0b611b");
			labelCifrao2.setStyle("-fx-text-fill: #0b611b");
			lbTotalDia.setStyle("-fx-text-fill: #0b611b");
		}
		else{
			if(totalDia< 0.0) {//Vermelho
				lbTotalDia.setStyle("-fx-text-fill: #f20303");
				labelCifrao2.setStyle("-fx-text-fill: #f20303");
				labelTotalDia.setStyle("-fx-text-fill: #f20303");
			}
			else {//Amarelo
				lbTotalDia.setStyle("-fx-text-fill: #edae00");
				labelCifrao2.setStyle("-fx-text-fill: #edae00");
				labelTotalDia.setStyle("-fx-text-fill:#edae00");
			}
		}	
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
		carregaOperacoes();
		calculaMontante();
		carregaAno();
	}
}
