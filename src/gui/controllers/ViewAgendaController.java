package gui.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import org.controlsfx.control.textfield.TextFields;

import gui.util.Decoracao;
import gui.util.Notificacoes;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextField;
import model.collection.Colecao;
import model.collection.entities.Cliente;
import model.dao.DaoAgendamento;
import model.dao.DaoFuncionario;

public class ViewAgendaController implements Initializable{
	
 	@FXML
    private Button btVoltar;

    @FXML
    private DatePicker dpData;

    @FXML
    private TextField txtCliente;

    @FXML
    private Button btAgendar;
    
    @FXML
    private CheckBox cb8;
    
    @FXML
    private CheckBox cb8_3;
    
    @FXML
    private CheckBox cb9;
    
    @FXML
    private CheckBox cb9_3;
    
    @FXML
    private CheckBox cb10;
    
    @FXML
    private CheckBox cb10_3;
    
    @FXML
    private CheckBox cb11;
    
    @FXML
    private CheckBox cb11_3;

    @FXML
    private CheckBox cb12;

    @FXML
    private CheckBox cb12_3;

    @FXML
    private CheckBox cb13;

    @FXML
    private CheckBox cb13_3;

    @FXML
    private CheckBox cb14;

    @FXML
    private CheckBox cb15;

    @FXML
    private CheckBox cb18;

    @FXML
    private CheckBox cb17_3;

    @FXML
    private CheckBox cb14_3;

    @FXML
    private CheckBox cb17;

    @FXML
    private CheckBox cb16_3;

    @FXML
    private CheckBox cb16;

    @FXML
    private CheckBox cb15_3;

    @FXML
    private TextField txtFuncionario;
    
    @FXML
	private ProgressIndicator piCarregando;
    
    @FXML
    private Label labelStatus;
    
    private boolean parada;
	    
    
    @FXML
    public void onBtAgendarAction(){
    	if(txtCliente.getText().isEmpty()) {
    		Decoracao.setDecoracao(txtCliente);
			Notificacoes.mostraNotificacao("Campos vazios!", "Preencha o campo nome!");
    	}
    	else {
    		parada = true;
    		Task<Void> tarefa = new Task<Void>() {
    			@Override
    			protected Void call() throws Exception {
    				while (parada) {
    					Thread.sleep(0);
    				}
    				piCarregando.setVisible(false);
    				labelStatus.setVisible(false);
    				return null;
    			}
    		};

    		Task<Void> acaoAgendamento = new Task<Void>() {
				@Override
				protected Void call() throws Exception {
					javafx.application.Platform.runLater(() -> {
		    			Thread t = new Thread(tarefa);
		    			t.start();
		    		});
					piCarregando.setVisible(true);
    				labelStatus.setVisible(true);
					salvaHorario();
		    		DaoFuncionario.carregaAgendaFuncionario(dpData.getValue());
		    		ViewController.getTvAgendaTemp().refresh();
		    		parada = false;
					return null;
				}
				
				@Override
				protected void done() {
					Platform.runLater(new Runnable() {
						@Override
						public void run() {
							ViewController.getStageAgenda().close();
						}
					});	
					super.done();
				}
    		};
    		
    		javafx.application.Platform.runLater(() -> {
    			Thread t = new Thread(acaoAgendamento);
    			t.start(); 
    		});    		
    	}
    }

    public void salvaHorario() {
    	int idCliente = 0;
    	for(Cliente cli : Colecao.clientes) {
			if(txtCliente.getText().equals(cli.getNome())) {
				idCliente = cli.getId();
				txtCliente.setText("");
			}
		}
    	if(txtCliente.getText().equals("")) {
    		JOptionPane.showMessageDialog(null,"Primeiro crie um novo cliente com todos os dados"
					+ " ou então crie um cliente com pelo menos o dado: NOME", "Cliente não encontrado", JOptionPane.ERROR_MESSAGE);
    		ViewController.getStageAgenda().close();
    	}
    	else {
    		if(cb8.isSelected() && !(cb8.isDisable())) {
        		DaoAgendamento.salvarAgendamento(txtFuncionario.getText(), idCliente, dpData.getValue(), "08:00:00");       			
        	}
        	if(cb8_3.isSelected() && !(cb8_3.isDisable())) {
        		DaoAgendamento.salvarAgendamento(txtFuncionario.getText(), idCliente, dpData.getValue(), "08:30:00");
        	}
        	if(cb9.isSelected() && !(cb9.isDisable())) {
        		DaoAgendamento.salvarAgendamento(txtFuncionario.getText(), idCliente, dpData.getValue(), "09:00:00");
        	}
        	if(cb9_3.isSelected() && !(cb9_3.isDisable())) {
        		DaoAgendamento.salvarAgendamento(txtFuncionario.getText(), idCliente, dpData.getValue(), "09:30:00");
        	}
        	if(cb10.isSelected() && !(cb10.isDisable())) {
        		DaoAgendamento.salvarAgendamento(txtFuncionario.getText(), idCliente, dpData.getValue(), "10:00:00");
        	}
        	if(cb10_3.isSelected() && !(cb10_3.isDisable())) {
        		DaoAgendamento.salvarAgendamento(txtFuncionario.getText(), idCliente, dpData.getValue(), "10:30:00");
        	}
        	if(cb11.isSelected() && !(cb11.isDisable())) {
        		DaoAgendamento.salvarAgendamento(txtFuncionario.getText(), idCliente, dpData.getValue(), "11:00:00");
        	}
        	if(cb11_3.isSelected() && !(cb11_3.isDisable())) {
        		DaoAgendamento.salvarAgendamento(txtFuncionario.getText(), idCliente, dpData.getValue(), "11:30:00");
        	}
        	if(cb12.isSelected() && !(cb12.isDisable())) {
        		DaoAgendamento.salvarAgendamento(txtFuncionario.getText(), idCliente, dpData.getValue(), "12:00:00");
        	}
        	if(cb12_3.isSelected() && !(cb12_3.isDisable())) {
        		DaoAgendamento.salvarAgendamento(txtFuncionario.getText(), idCliente, dpData.getValue(), "12:30:00");
        	}
        	if(cb13.isSelected() && !(cb13.isDisable())) {
        		DaoAgendamento.salvarAgendamento(txtFuncionario.getText(), idCliente, dpData.getValue(), "13:00:00");
        	}
        	if(cb13_3.isSelected() && !(cb13_3.isDisable())) {
        		DaoAgendamento.salvarAgendamento(txtFuncionario.getText(), idCliente, dpData.getValue(), "13:30:00");
        	}
        	if(cb14.isSelected() && !(cb14.isDisable())) {
        		DaoAgendamento.salvarAgendamento(txtFuncionario.getText(), idCliente, dpData.getValue(), "14:00:00");
        	}
        	if(cb14_3.isSelected() && !(cb14_3.isDisable())) {
        		DaoAgendamento.salvarAgendamento(txtFuncionario.getText(), idCliente, dpData.getValue(), "14:30:00");
        	}
        	if(cb15.isSelected() && !(cb15.isDisable())) {
        		DaoAgendamento.salvarAgendamento(txtFuncionario.getText(), idCliente, dpData.getValue(), "15:00:00");
        	}
        	if(cb15_3.isSelected() && !(cb15_3.isDisable())) {
        		DaoAgendamento.salvarAgendamento(txtFuncionario.getText(), idCliente, dpData.getValue(), "15:30:00");
        	}
        	if(cb16.isSelected() && !(cb16.isDisable())) {
        		DaoAgendamento.salvarAgendamento(txtFuncionario.getText(), idCliente, dpData.getValue(), "16:00:00");
        	}
        	if(cb16_3.isSelected() && !(cb16_3.isDisable())) {
        		DaoAgendamento.salvarAgendamento(txtFuncionario.getText(), idCliente, dpData.getValue(), "16:30:00");
        	}
        	if(cb17.isSelected() && !(cb17.isDisable())) {
        		DaoAgendamento.salvarAgendamento(txtFuncionario.getText(), idCliente, dpData.getValue(), "17:00:00");
        	}
        	if(cb17_3.isSelected() && !(cb17_3.isDisable())) {
        		DaoAgendamento.salvarAgendamento(txtFuncionario.getText(), idCliente, dpData.getValue(), "17:30:00");
        	}
        	if(cb18.isSelected() && !(cb18.isDisable())) {
        		DaoAgendamento.salvarAgendamento(txtFuncionario.getText(), idCliente, dpData.getValue(), "18:00:00");
        	}
    	}
    }
    
    @FXML
    public void carregaHorarios() {
    	if(!ViewController.getFunTemp().getH8().equals("Livre")) {
    		cb8.setSelected(true);
    		cb8.setDisable(true);
    	}
    	if(!ViewController.getFunTemp().getH8_3().equals("Livre")) {
    		cb8_3.setSelected(true);
    		cb8_3.setDisable(true);
    	}
    	if(!ViewController.getFunTemp().getH9().equals("Livre")) {
    		cb9.setSelected(true);
    		cb9.setDisable(true);
    	}
    	if(!ViewController.getFunTemp().getH9_3().equals("Livre")) {
    		cb9_3.setSelected(true);
    		cb9_3.setDisable(true);
    	}
    	if(!ViewController.getFunTemp().getH10().equals("Livre")) {
    		cb10.setSelected(true);
    		cb10.setDisable(true);
    	}
    	if(!ViewController.getFunTemp().getH10_3().equals("Livre")) {
    		cb10_3.setSelected(true);
    		cb10_3.setDisable(true);
    	}
    	if(!ViewController.getFunTemp().getH11().equals("Livre")) {
    		cb11.setSelected(true);
    		cb11.setDisable(true);
    	}
    	if(!ViewController.getFunTemp().getH11_3().equals("Livre")) {
    		cb11_3.setSelected(true);
    		cb11_3.setDisable(true);
    	}
    	if(!ViewController.getFunTemp().getH12().equals("Livre")) {
			cb12.setSelected(true);
			cb12.setDisable(true);
		}
    	if(!ViewController.getFunTemp().getH12_3().equals("Livre")) {
			cb12_3.setSelected(true);
			cb12_3.setDisable(true);
		}
    	if(!ViewController.getFunTemp().getH13().equals("Livre")) {
			cb13.setSelected(true);
			cb13.setDisable(true);
		}
    	if(!ViewController.getFunTemp().getH13_3().equals("Livre")) {
			cb13_3.setSelected(true);
			cb13_3.setDisable(true);
		}
    	if(!ViewController.getFunTemp().getH14().equals("Livre")) {
			cb14.setSelected(true);
			cb14.setDisable(true);
		}
    	if(!ViewController.getFunTemp().getH14_3().equals("Livre")) {
			cb14_3.setSelected(true);
			cb14_3.setDisable(true);
		}
    	if(!ViewController.getFunTemp().getH15().equals("Livre")) {
			cb15.setSelected(true);
			cb15.setDisable(true);
		}
    	if(!ViewController.getFunTemp().getH15_3().equals("Livre")) {
			cb15_3.setSelected(true);
			cb15_3.setDisable(true);
		}
    	if(!ViewController.getFunTemp().getH16().equals("Livre")) {
			cb16.setSelected(true);
			cb16.setDisable(true);
		}
    	if(!ViewController.getFunTemp().getH16_3().equals("Livre")) {
			cb16_3.setSelected(true);
			cb16_3.setDisable(true);
		}
    	if(!ViewController.getFunTemp().getH17().equals("Livre")) {
			cb17.setSelected(true);
			cb17.setDisable(true);
		}
    	if(!ViewController.getFunTemp().getH17_3().equals("Livre")) {
			cb17_3.setSelected(true);
			cb17_3.setDisable(true);
		}
    	if(!ViewController.getFunTemp().getH18().equals("Livre")) {
			cb18.setSelected(true);
			cb18.setDisable(true);
		}
    }
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {	
		txtFuncionario.setText(ViewController.getFunTemp().getNome());
		txtFuncionario.setEditable(false);
		dpData.setValue(ViewController.getDpDataTemp());
		dpData.setEditable(false);
		dpData.getEditor().setEditable(false);
		carregaHorarios();
		TextFields.bindAutoCompletion(txtCliente, Colecao.clientes);
	}
}
