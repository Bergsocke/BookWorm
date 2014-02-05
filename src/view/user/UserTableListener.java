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

	private UserGUI guiUser;

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

					// da in der Tabelle die Autosortierung mit
					// setAutoCreateRowSorter(true) gesetzt wurde, ist die
					// Convertierung notwendig, damit die korrekte Zeile/Spalte
					// ausgewählt wird
					String id = String
							.valueOf(guiUser
									.getUserTable()
									.getModel()
									.getValueAt(
											guiUser.getUserTable()
													.convertRowIndexToModel(
															selectedRow), 0));

					User myUser = UserDB.findByID(id);

					guiUser.getUserIDText().setText(
							String.valueOf(myUser.getUserID()));

					guiUser.getUserNameText().setText(
							String.valueOf(myUser.getUserName()));

					// Das Passwort-Feld soll nicht angezeigt werden, um zu
					// vermeiden, dass das bereits gehashte Passwort, nochmals
					// gehasht in die Datenbank gespeichert wird
					guiUser.getUserPasswordText().setVisible(false);

					guiUser.getUserRoleCombo().setSelectedItem(
							String.valueOf(myUser.getUserRole()));

					// Button "löschen" und "Neues Passwort" werden sichtbar
					// gesetzt
					guiUser.getDeleteButton().setEnabled(true);
					guiUser.getCreatePWButton().setEnabled(true);

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
