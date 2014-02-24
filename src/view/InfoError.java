package view;

import javax.swing.JOptionPane;

/**
 * Die Klasse "InfoError" soll ein Dialogfenster ausgeben, das erscheint, wenn
 * eine Aktion nicht erfolgreich durchgeführt werden konnte.
 * 
 * @author Bergsocke
 * 
 */

public class InfoError extends JOptionPane {

	private static final long serialVersionUID = 4074118514759731523L;

	/**
	 * Dialogfenster mit der entsprechenden Meldung
	 * 
	 * @param errorText
	 */
	public void showMessage(String errorText) {
		showMessageDialog(null, errorText, "Fehler", JOptionPane.ERROR_MESSAGE);
	}

}
