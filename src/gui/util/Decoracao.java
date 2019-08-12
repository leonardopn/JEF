package gui.util;

import org.controlsfx.control.decoration.Decorator;
import org.controlsfx.control.decoration.StyleClassDecoration;
import org.controlsfx.validation.ValidationSupport;
import org.controlsfx.validation.Validator;

import javafx.scene.control.TextField;

public class Decoracao {

	public static void setDecoracao(TextField txt) {
		ValidationSupport validacao = new ValidationSupport();
		validacao.registerValidator(txt, Validator.createEmptyValidator("Text is Required"));
		Decorator.addDecoration(txt, new StyleClassDecoration("warning"));
	}
}
