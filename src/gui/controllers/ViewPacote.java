package gui.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import model.collection.entities.Pacote;

public class ViewPacote implements Initializable{
	
	@FXML
    private TableView<Pacote> tvPacotes;

    @FXML
    private TableColumn<Pacote, Integer> colunaId;

    @FXML
    private TableColumn<Pacote, String> colunaPacote;

    @FXML
    private TableColumn<Pacote, Double> colunaValor;

    @FXML
    private TableColumn<Pacote, Integer> colunaQuantMao;

    @FXML
    private TableColumn<Pacote, Integer> colunaQuantPe;

    @FXML
    private TableColumn<Pacote, Double> ColunaValorMao;

    @FXML
    private TableColumn<Pacote, Double> ColunaValorPe;

    @FXML
    private Button btAddPacote;

    @FXML
    private TextField tfNome;

    @FXML
    private TextField tfValor;

    @FXML
    private TextField tfValorMao;

    @FXML
    private TextField tfQuantMao;

    @FXML
    private TextField tfQuantPe;

    @FXML
    private TextField tfValorPe;

    @FXML
    private TableView<?> tvPacotesAssociados;

    @FXML
    private TableColumn<?, ?> calunaIdCliente;

    @FXML
    private TableColumn<?, ?> colunaCliente;

    @FXML
    private TableColumn<?, ?> colunaPacoteAssociado;

    @FXML
    private TableColumn<?, ?> colunaQuantMaoAssociado;

    @FXML
    private TableColumn<?, ?> colunaQuantPeAssociado;

    @FXML
    private TextField tfCliente;

    @FXML
    private ChoiceBox<?> cbPacote;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		colunaId.setCellValueFactory(new PropertyValueFactory<>("id"));
		colunaPacote.setCellValueFactory(new PropertyValueFactory<>("pacote"));
		colunaQuantMao.setCellValueFactory(new PropertyValueFactory<>("quantMao"));
		colunaQuantPe.setCellValueFactory(new PropertyValueFactory<>("quantPe"));
		colunaValor.setCellValueFactory(new PropertyValueFactory<>("valor"));
		ColunaValorMao.setCellValueFactory(new PropertyValueFactory<>("precoMao"));
		ColunaValorPe.setCellValueFactory(new PropertyValueFactory<>("precoPe"));
	}

}
