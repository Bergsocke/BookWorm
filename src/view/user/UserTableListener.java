package view.user;

import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import model.user.User;
import model.user.UserDB;
import view.InfoError;

/**
 * Die Klasse "UserTableListener" reagiert auf die Auswahl des Anwenders in der
 * Tabelle im WestPanel. Wenn in der Tabelle ein Datensatz ausgewählt wurde,
 * werden die entsprechenden Daten in den Textfeldern im EastPanel angezeigt.
 * 
 * @author Bergsocke
 * 
 */
public class UserTableListener implements ListSelectionListener {

	UserGUI guiUser;

	/**
	 * Konstruktor
	 * 
	 * @param guiUser
	 */
	public UserTableListener(UserGUI guiUser) {
		this.guiUser = guiUser;
	}

	@Override
	public void valueChanged(ListSelectionEvent event) {

		if (!event.getValueIsAdjusting()) {
			ListSelectionModel lsm = (ListSelectionModel) event.getSource();
			if (lsm.isSelectionEmpty() == false) {
				try {
					int selectedRow = lsm.getMinSelectionIndex();

					String id = String.valueOf(guiUser.getUserTable()
							.getModel().getValueAt(selectedRow, 0));

					User myUser = UserDB.findByID(id);

					guiUser.getUserIDText().setText(
							String.valueOf(myUser.getUserID()));

					guiUser.getUserNameText().setText(
							String.valueOf(myUser.getUserName()));

					guiUser.getUserPasswordText().setText(
							String.valueOf(myUser.getUserPassword()));

					// Button "löschen" wird sichtbar gesetzt
					guiUser.getDeleteButton().setEnabled(true);

				} catch (Exception e) {
					System.out.println(e.toString());
					// Ein Dialogfenster mit entsprechender Meldung soll erzeugt
					// werden
					String errorText = "Der ausgewählte Datensatz kann nicht angezeigt werden.";
					InfoError.showMessage(errorText);

				}
			}
		}
	}
}
