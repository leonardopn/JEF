package gui.util;

import java.util.Optional;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextInputDialog;

public class Alerts {
	static boolean teste;
	
	public static void showAlert(String title, String header, String content, AlertType type) {
		Alert alert = new Alert(type);
		alert.setResizable(true);
		alert.setTitle(title);
		alert.setHeaderText(header);
		alert.setContentText(content);
		alert.show();
	}
	
	public static boolean showAlertExclusao() {
		ButtonType btnSim = new ButtonType("Sim");
        ButtonType btnNao = new ButtonType("Não");
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setResizable(true);
		alert.setTitle("Confirmação de exclusão");
		alert.setHeaderText("Você confirma essa exclusão?");
		alert.setContentText("Exclusões feitas no programa não excluirão clientes e nem funcionários no banco de dados!\n\nSe deseja eliminar tudo relacionado"
				+ " a esse cliente ou funcionário, entre em contato com o ADMINISTRADOR!");
		alert.getButtonTypes().setAll(btnSim, btnNao);
		alert.showAndWait().ifPresent(b -> {
			if(b == btnSim) {
				teste = true;
			}
			else if(b == btnNao) {
				teste = false;
			}
		});
		return teste;
	}
	
	public static boolean showAlertAtualizacao() {
		ButtonType btnSim = new ButtonType("Sim");
        ButtonType btnNao = new ButtonType("Não");
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setResizable(true);
		alert.setTitle("Confirmação de atualização");
		alert.setHeaderText("Você confirma essa atualização?");
		alert.setContentText("Atualizações são reversíveis!");
		alert.getButtonTypes().setAll(btnSim, btnNao);
		alert.showAndWait().ifPresent(b -> {
			if(b == btnSim) {
				teste = true;
			}
			else if(b == btnNao) {
				teste = false;
			}
		});
		return teste;
	}
	
	public static boolean showAlertGenerico(String titulo, String avisoCentral, String subAviso) {
		ButtonType btnSim = new ButtonType("Sim");
        ButtonType btnNao = new ButtonType("Não");
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setResizable(true);
		alert.setTitle(titulo);
		alert.setHeaderText(avisoCentral);
		alert.setContentText(subAviso);
		alert.getButtonTypes().setAll(btnSim, btnNao);
		alert.showAndWait().ifPresent(b -> {
			if(b == btnSim) {
				teste = true;
			}
			else if(b == btnNao) {
				teste = false;
			}
		});
		return teste;
	}
	
	public static String showAlertGenericoTextField(String titulo, String avisoCentral, String subAviso) {
		TextInputDialog dialog = new TextInputDialog();
		dialog.setTitle(titulo);
		dialog.setHeaderText(avisoCentral);
		dialog.setContentText(subAviso);

		Optional<String> result = dialog.showAndWait();
		if (result.isPresent()){
		   return result.get();
		}

		return "00,00";
	}
}