package gui.util;

import org.controlsfx.control.Notifications;

import javafx.geometry.Pos;
import javafx.util.Duration;

public class Notificacoes {

	public static void mostraNotificacao(String titulo, String texto ) {
		Notifications not = Notifications.create().title(titulo).text(texto).graphic(null)
				.hideAfter(Duration.seconds(4))
				.position(Pos.CENTER);
		not.showInformation();
	}
}
