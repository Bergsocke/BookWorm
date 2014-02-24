package view;

import javax.swing.JOptionPane;

/**
 * Die Klasse "InfoSuccess" soll ein Dialogfenster ausgeben, das erscheint, wenn
 * eine Aktion erfolgreich durchgeführt werden konnte
 * 
 * @author Bergsocke
 * 
 */

public class InfoSuccess extends JOptionPane {

	private static final long serialVersionUID = -8551295904949148610L;

	/**
	 * Dialogfenster mit der entsprechenden Meldung
	 * 
	 * @param successText
	 */
	public void showMessage(String successText) {
		showMessageDialog(null, successText, "",
				JOptionPane.INFORMATION_MESSAGE);
	}
}
