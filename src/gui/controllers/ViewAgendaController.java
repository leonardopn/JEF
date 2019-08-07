package gui.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

public class ViewAgendaController implements Initializable{
	
 	@FXML
    private Button btVoltar;

    @FXML
    private DatePicker dpData;

    @FXML
    private TextField txtCliente;

    @FXML
    private Button btBuscar;

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
    public void setHorarios() {
    	if(ViewController.getFunTemp().getH12() != "Livre") {
			cb12.setSelected(true);
			cb12.setDisable(true);
		}
    	if(ViewController.getFunTemp().getH12_3() != "Livre") {
			cb12_3.setSelected(true);
			cb12_3.setDisable(true);
		}
    	if(ViewController.getFunTemp().getH13() != "Livre") {
			cb13.setSelected(true);
			cb13.setDisable(true);
		}
    	if(ViewController.getFunTemp().getH13_3() != "Livre") {
			cb13_3.setSelected(true);
			cb13_3.setDisable(true);
		}
    	if(ViewController.getFunTemp().getH14() != "Livre") {
			cb14.setSelected(true);
			cb14.setDisable(true);
		}
    	if(ViewController.getFunTemp().getH14_3() != "Livre") {
			cb14_3.setSelected(true);
			cb14_3.setDisable(true);
		}
    	if(ViewController.getFunTemp().getH15() != "Livre") {
			cb15.setSelected(true);
			cb15.setDisable(true);
		}
    	if(ViewController.getFunTemp().getH15_3() != "Livre") {
			cb15_3.setSelected(true);
			cb15_3.setDisable(true);
		}
    	if(ViewController.getFunTemp().getH16() != "Livre") {
			cb16.setSelected(true);
			cb16.setDisable(true);
		}
    	if(ViewController.getFunTemp().getH16_3() != "Livre") {
			cb16_3.setSelected(true);
			cb16_3.setDisable(true);
		}
    	if(ViewController.getFunTemp().getH17() != "Livre") {
			cb17.setSelected(true);
			cb17.setDisable(true);
		}
    	if(ViewController.getFunTemp().getH17_3() != "Livre") {
			cb17_3.setSelected(true);
			cb17_3.setDisable(true);
		}
    	if(ViewController.getFunTemp().getH18() != "Livre") {
			cb18.setSelected(true);
			cb18.setDisable(true);
		}
    }
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {	
		txtFuncionario.setText(ViewController.getFunTemp().getNome());
		txtFuncionario.setEditable(false);
		dpData.setValue(ViewController.getDpDataTemp());
//		dpData.setDisable(true);
		setHorarios();
	}
}
