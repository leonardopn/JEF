package gui.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import org.controlsfx.control.textfield.TextFields;

import gui.util.Alerts;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import model.entities.Agendamento;
import model.services.Cadastro;
import model.services.Carregar;
import model.services.Excluir;

public class ViewAtualizaAgendaController implements Initializable {

	ObservableList<Agendamento> obAgendamento;
	
    @FXML
    private TextField tfCliente;

    @FXML
    private DatePicker dpData;

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
    private Button btExcluir;
    
    
   @FXML
    public void pesquisaAgendamento() {
    	Carregar.carregaAgendamento(dpData.getValue(), tfCliente.getText());
    	populaTabela();
    }
   
   private void populaTabela() {
	   obAgendamento = FXCollections.observableArrayList(Cadastro.agendamentos);
       tvAgendamento.setItems(obAgendamento);
   }
   
   @FXML
   public void excluirAgendamento() {
		if(Alerts.showAlertExclusao()) {
			ObservableList<Agendamento> obExcluirAgendamento = FXCollections.observableArrayList();
			
			for(Agendamento age : obAgendamento) {
				if(age.getSelect().isSelected()) {
					obExcluirAgendamento.add(age);
					Excluir.excluirAgendamento(age);

				}
			}
			obAgendamento.removeAll(obExcluirAgendamento);
			Cadastro.agendamentos.removeAll(obExcluirAgendamento);
		}
	}
   
   @Override
   public void initialize(URL arg0, ResourceBundle arg1) {
	   colunaCliente.setCellValueFactory(new PropertyValueFactory<>("cliente"));
	   colunaHorario.setCellValueFactory(new PropertyValueFactory<>("horario"));
	   colunaExcluir.setCellValueFactory(new PropertyValueFactory<>("select"));
	   TextFields.bindAutoCompletion(tfCliente, Cadastro.clientes);
	   dpData.setValue(ViewController.getDpDataTemp());
	}
}
