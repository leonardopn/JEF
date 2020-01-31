package gui.utils;

import org.controlsfx.control.Notifications;

import javafx.geometry.Pos;
import javafx.util.Duration;

public class NotificacoesUtils {

	public static void mostraNotificacoes(String titulo, String texto ) {
		Notifications not = Notifications.create().title(titulo).text(texto).graphic(null)
				.hideAfter(Duration.seconds(4))
				.position(Pos.CENTER);
		not.showInformation();
	}
}
