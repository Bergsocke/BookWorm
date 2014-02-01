package view;

import javax.swing.JOptionPane;

/**
 * Die Klasse "ErrorMessage" soll ein Dialogfenster ausgeben, das erscheint,
 * wenn eine Aktion nicht erfolgreich durchgeführt werden konnte.
 * 
 * @author Bergsocke
 * 
 */

public class ErrorMessage extends JOptionPane {

	private static final long serialVersionUID = 4074118514759731523L;

	public static void showMessage(String errorText) {
		showMessageDialog(null, errorText, "Fehler", JOptionPane.ERROR_MESSAGE);
	}

}
