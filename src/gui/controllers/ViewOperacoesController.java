package gui.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class ViewOperacoesController implements Initializable{

	@FXML
    private TableView<?> tvOperacoes;

    @FXML
    private TableColumn<?, ?> colunaId;

    @FXML
    private TableColumn<?, ?> colunaDescricao;

    @FXML
    private TableColumn<?, ?> colunaData;

    @FXML
    private TableColumn<?, ?> colunaReceita;

    @FXML
    private TableColumn<?, ?> colunaDespesa;

    @FXML
    private TableColumn<?, ?> ColunaFormaPagamento;

    @FXML
    private TableColumn<?, ?> colunaExcluir;

    @FXML
    private Label lbTotalDia;

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
    private Spinner<?> spinAno;

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

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
	}
}
