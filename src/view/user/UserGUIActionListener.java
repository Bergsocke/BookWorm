package view.user;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import model.SQLDatabase;
import model.user.User;
import model.user.UserDB;
import view.InfoError;
import view.InfoSuccess;
import view.book.BookGUI;
import view.login.LoginGUI;

/**
 * Mit der Klasse "UserGUIActionListener" werden die Aktionen für die Buttons
 * "alle anzeigen", "suchen", "neu", "speichern", "löschen", "Neues Passwort",
 * "Passwort setzen", "Programm beenden" sowie für die MenüBarItems
 * "Neuen Datensatz anlegen", "Datensatz speichern", "Datensatz löschen",
 * "Zur Bücherverwaltung wechseln", "Benutzer abmelden", "Programm beenden" und
 * "Über das Programm" der Klasse UserGUI festgelegt.
 * 
 * @author Bergsocke
 * 
 */

public class UserGUIActionListener implements ActionListener {

	UserGUI guiUser;

	/**
	 * Konstruktor
	 * 
	 * @param guiUser
	 */
	public UserGUIActionListener(UserGUI guiUser) {
		this.guiUser = guiUser;
	}

	@Override
	public void actionPerformed(ActionEvent event) {

		// Wenn auf den Button "alle anzeigen" geklickt wird, werden
		// alle Datensätze der Datenbank angezeigt (mit Hilfe der Methode
		// "createUserTable()" aus der Klasse UserGUI) und der Text im Suchfeld
		// wird zurückgesetzt. Die Button "löschen", "Neues Passwort" und
		// "Passwort setzen" sollen erst aktiviert werden, wenn ein Datensatz
		// ausgewählt wurde.
		if (event.getSource() instanceof JButton
				&& event.getActionCommand().contains("alle anzeigen")) {
			// Suchbegriff wird zurückgesetzt
			guiUser.getSearchText().setText("");
			guiUser.createUserTable();

			// EastTable wird zurückgesetzt
			guiUser.resetTableEast();

			guiUser.getDeleteButton().setEnabled(false);
			guiUser.getCreatePWButton().setEnabled(false);
			guiUser.getSavePWButton().setEnabled(false);
		}

		// Wenn auf den Button "suchen" geklickt wird, wird in der Datenbank
		// nach den entsprechenden Usernamen gesucht und die gefundenen
		// Datensätze werden angezeigt (mit Hilfe der Methode
		// "createUserTable()" aus der Klasse UserGUI).
		// Die Button "löschen", "Neues Passwort" und "Passwort setzen" sollen
		// erst aktiviert werden, wenn ein Datensatz ausgewählt wurde.
		if (event.getSource() instanceof JButton
				&& event.getActionCommand().contains("suchen")) {
			guiUser.createUserTable();

			// EastTable wird zurückgesetzt
			guiUser.resetTableEast();

			guiUser.getDeleteButton().setEnabled(false);
			guiUser.getCreatePWButton().setEnabled(false);
			guiUser.getSavePWButton().setEnabled(false);

			// Wenn kein Datensatz gefunden wurde, wird eine entsprechende
			// Meldung ausgegeben
			int row = guiUser.getUserTable().getModel().getRowCount();
			if (row == 0) {
				// Folgende Meldung wird ausgegeben
				String successText = "Es wurde kein Datensatz gefunden!";
				InfoSuccess.showMessage(successText);
			}
		}

		// Wenn auf den Button "neu" oder in der Menübar auf
		// "Neuen Datensatz anlegen" geklickt wird , wird der Inhalt der
		// Textfelder im EastPanel zurückgesetzt. Ein neuer Datensatz wird
		// erst beim Klick auf den Button "speichern" in die Datenbank
		// eingefügt.
		if (event.getSource() instanceof JButton
				&& event.getActionCommand().contains("neu")) {
			this.actionClear();
		}
		if (event.getSource() instanceof JMenuItem
				&& event.getActionCommand().contains("Neuen Datensatz anlegen")) {
			this.actionClear();
		}

		// Wenn auf den Button "speichern" oder in der Menübar auf
		// "Datensatz speichern" geklickt wird, wird ein bereits vorhandener
		// Datensatz in der Datenbank upgedatet.
		// Ist der Datensatz noch nicht in der Datenbank vorhanden, wird er in
		// die Datenbank eingefügt.
		if (event.getSource() instanceof JButton
				&& event.getActionCommand().contains("speichern")) {
			this.actionSave();
		}
		if (event.getSource() instanceof JMenuItem
				&& event.getActionCommand().contains("Datensatz speichern")) {
			this.actionSave();
		}

		// Wenn auf den Button "löschen" oder in der Menübar auf
		// "Datensatz löschen" geklickt wird, wird der Datensatz aus der
		// Datenbank gelöscht
		if (event.getSource() instanceof JButton
				&& event.getActionCommand().contains("löschen")) {
			this.actionDelete();
		}
		if (event.getSource() instanceof JMenuItem
				&& event.getActionCommand().contains("Datensatz löschen")) {
			this.actionDelete();
		}

		// wenn auf den Button "Neues Passwort" geklickt wird, soll für einen
		// bereits erfassten Anwender ein neues Passwort vergeben werden können;
		// Das Passwort-Textfeld wird sichtbar und der Button "Passwort setzen"
		// (zum Speichern des neuen Passworts) wird aktiv gesetzt.
		// Der Button "speichern" wird inaktiv.
		if (event.getSource() instanceof JButton
				&& event.getActionCommand().contains("Neues Passwort")) {
			guiUser.getUserPasswordText().setVisible(true);
			guiUser.getUserPasswordText().setText("");

			guiUser.getSavePWButton().setEnabled(true);
			guiUser.getSaveButton().setEnabled(false);
		}
		if (event.getSource() instanceof JMenuItem
				&& event.getActionCommand().contains("Neues Passwort")) {
			guiUser.getUserPasswordText().setVisible(true);
			guiUser.getUserPasswordText().setText("");

			guiUser.getSavePWButton().setEnabled(true);
			guiUser.getSaveButton().setEnabled(false);
		}

		// Wenn auf den Button "Passwort setzen" oder in der Menübar auf
		// "Passwort setzen" geklickt wird, wird das neu gesetzte Passwort in
		// die Datenbank gespeichert
		if (event.getSource() instanceof JButton
				&& event.getActionCommand().contains("Passwort setzen")) {
			this.actionNewPassword();

		}
		if (event.getSource() instanceof JMenuItem
				&& event.getActionCommand().contains("Passwort setzen")) {
			this.actionNewPassword();
		}

		// Wenn auf den Button "Programm beenden" oder in der Menübar auf
		// "Programm beenden" geklickt wird, wird das Programm beendet
		if (event.getSource() instanceof JButton
				&& event.getActionCommand().contains("Programm beenden")) {
			this.actionClose();
		}
		if (event.getSource() instanceof JMenuItem
				&& event.getActionCommand().contains("Programm beenden")) {
			this.actionClose();
		}

		// Wenn in der Menübar auf "Benutzer abmelden" geklickt wird, soll das
		// Programm-Fenster geschlossen und das Login-Fenster für eine
		// erneute Benutzer-Anmeldung geöffnet werden.
		if (event.getSource() instanceof JMenuItem
				&& event.getActionCommand().contains("Benutzer abmelden")) {
			guiUser.setVisible(false);
			LoginGUI.main(null);
		}

		// Wenn in der Menübar auf "Zur Bücherverwaltung wechseln" geklickt
		// wird, wird die Bücherverwaltungs-GUI aufgerufen
		if (event.getSource() instanceof JMenuItem
				&& event.getActionCommand().contains(
						"Zur Bücherverwaltung wechseln")) {
			// Userverwaltungs-GUI wird geschlossen
			guiUser.setVisible(false);
			// Bücherververwaltungs-GUI wird aufgerufen
			BookGUI.letStartedBookGUI();
		}
		// Wenn in der Menübar auf "Über das Programm" geklickt wird, wird ein
		// Dialogfenster erzeugt
		if (event.getSource() instanceof JMenuItem
				&& event.getActionCommand().contains("Über das Programm")) {
			// Folgende Meldung wird ausgegeben
			String successText = "Erstellt von Weinberger Eva, 2014";
			InfoSuccess.showMessage(successText);
		}
	}

	/**
	 * Wenn auf den Button "neu" oder in der Menübar auf
	 * "Neuen Datensatz anlegen" geklickt wird, wird der Inhalt der Textfelder
	 * im EastPanel zurückgesetzt (mit Hilfe der Methode "resetTableEast()" aus
	 * der Klasse "UserGUI"). Ein neuer Datensatz wird erst beim Klick auf den
	 * Button "speichern" in die Datenbank eingefügt. Die Button "speichern",
	 * "löschen", "Neues Passwort" und "Passwort setzen" werden deaktiviert.
	 */
	public void actionClear() {
		// Alle Textfelder werden zurückgesetzt
		guiUser.resetTableEast();
		// Passwortfeld für die Eingabe aktivieren
		guiUser.getUserPasswordText().setVisible(true);
		// Löschen- und PasswortVergeben-Buttons werden deaktiviert
		guiUser.getSaveButton().setEnabled(true);
		guiUser.getDeleteButton().setEnabled(false);
		guiUser.getCreatePWButton().setEnabled(false);
		guiUser.getSavePWButton().setEnabled(false);
	}

	/**
	 * Wenn auf den Button "speichern" oder in der Menübar auf
	 * "Datensatz speichern" geklickt wird, wird ein bereits vorhandener
	 * Datensatz in der Datenbank upgedatet bzw. wenn der Datensatz noch nicht
	 * in der Datenbank vorhanden ist, wird er in die Datenbank gespeichert.
	 */
	public void actionSave() {
		// Ist die User-ID leer, wird ein neuer Datensatz angelegt und in die
		// Datenbank gespeichert
		if (guiUser.getUserIDText().getText().matches("")) {

			User myUser = new User(String.valueOf(guiUser.getUserNameText()
					.getText()), String.valueOf(guiUser.getUserPasswordText()
					.getText()), String.valueOf(guiUser.getUserRoleCombo()
					.getSelectedItem()));

			// Eine Verbindung zur Datenbank wird aufgebaut und der neue
			// Datensatz wird in die Datenbank gespeichert
			UserDB.saveUser(myUser);

		} else {
			// Die eingegebenen Daten des bereits vorhandenen Datensatzes
			// werden eingelesen. Der Button "Neues Passwort" wird aktiviert.
			guiUser.getCreatePWButton().setEnabled(true);

			String userID = guiUser.getUserIDText().getText();

			User myUser = UserDB.findByID(userID);

			myUser.setUserName(guiUser.getUserNameText().getText());
			myUser.setUserRole(String.valueOf(guiUser.getUserRoleCombo()
					.getSelectedItem()));

			// Eine Verbindung zur Datenbank wird aufgebaut und der
			// Datensatz wird in die Datenbank gespeichert
			UserDB.updateUser(myUser);
		}

		// Wenn der Datensatz erfolgreich gespeichert wurde, wird eine
		// entsprechende Meldung ausgegeben
		if (UserDB.successful == 1) {
			// Die Tabelle im WestPanel wird neu aufgebaut, damit der
			// neu angelegte Datensatz gleich angezeigt wird
			guiUser.reloadWestTable();

			// Folgende Meldung wird ausgegeben
			String successText = "Datensatz wurde erfolgreich gespeichert!";
			InfoSuccess.showMessage(successText);

			// Alle Textfelder werden zurückgesetzt, damit weitere
			// Datensätze eingegeben werden können
			guiUser.resetTableEast();

			// Suchbegriff wird zurückgesetzt
			guiUser.getSearchText().setText("");

			// Wenn der Datensatz nicht gespeichert werden konnte, wird eine
			// entsprechende Meldung ausgegeben
		} else {
			// Ein Dialogfenster mit folgender Meldung soll erzeugt werden
			String errorText = "Datensatz konnte nicht gespeichert werden!";
			InfoError.showMessage(errorText);

			// Alle Textfelder werden zurückgesetzt, damit der Datensatz
			// erneut eingegeben werden kann
			guiUser.resetTableEast();

			// Suchbegriff wird zurückgesetzt
			guiUser.getSearchText().setText("");
		}
	}

	/**
	 * Wenn auf den Button "löschen" oder in der Menübar auf "Datensatz löschen"
	 * geklickt wird, wird der Datensatz aus der Datenbank gelöscht
	 */
	public void actionDelete() {
		// Die User-ID des bereits vorhandenen Datensatzes wird ausgelesen
		String userID = guiUser.getUserIDText().getText();

		// Es wird gefragt, ob der Datensatz wirklich gelöscht werden soll
		int check = JOptionPane.showConfirmDialog(guiUser,
				"Soll der Datensatz wirklich gelöscht werden?");

		// Wird mit "Ja" bestätigt, wird der Datensatz gelöscht
		if (check == 0) {
			// Eine Verbindung zur Datenbank wird aufgebaut und der
			// Datensatz wird aus der Datenbank gelöscht
			UserDB.deleteUser(userID);

			// Wenn der Datensatz erfolgreich gelöscht wurde, wird eine
			// entsprechende Meldung ausgegeben
			if (UserDB.successful == 1) {
				// Die Tabelle im WestPanel wird neu aufgebaut, damit der
				// gelöschte Datensatz nicht mehr angezeigt wird
				guiUser.reloadWestTable();

				// Folgende Meldung wird ausgegeben
				String successText = "Datensatz wurde erfolgreich gelöscht!";
				InfoSuccess.showMessage(successText);

				// Der Text im Suchfeld wird zurückgesetzt
				guiUser.getSearchText().setText("");
				// Alle Textfelder werden zurückgesetzt
				guiUser.resetTableEast();

				// Die Button "löschen", "Neues Passwort" und "Passwort setzen"
				// werden deaktiviert
				guiUser.getDeleteButton().setEnabled(false);
				guiUser.getCreatePWButton().setEnabled(false);
				guiUser.getSavePWButton().setEnabled(false);

				// Wenn der Datensatz nicht gelöscht werden konnte, wird eine
				// entsprechende Meldung ausgegebe
			} else {
				// Ein Dialogfenster mit folgender Meldung soll erzeugt werden
				String errorText = "Datensatz konnte nicht gelöscht werden!";
				InfoError.showMessage(errorText);

				// Suchbegriff wird zurückgesetzt
				guiUser.getSearchText().setText("");
				// Alle Textfelder werden zurückgesetzt
				guiUser.resetTableEast();

				// Die Button "löschen", "Neues Passwort" und "Passwort setzen"
				// werden deaktiviert
				guiUser.getDeleteButton().setEnabled(false);
				guiUser.getCreatePWButton().setEnabled(false);
				guiUser.getSavePWButton().setEnabled(false);
			}
		}
	}

	/**
	 * wenn auf den Button "Neues Passwort" geklickt wird, soll für einen
	 * bereits erfassten Anwender ein neues Passwort vergeben werden können
	 */
	public void actionNewPassword() {
		// Die User-ID des bereits vorhandenen Datensatzes wird ausgelesen
		String userID = guiUser.getUserIDText().getText();

		User myUser = UserDB.findByID(userID);

		// Die eingegebenen Daten werden eingelesen
		myUser.setUserName(guiUser.getUserNameText().getText());
		myUser.setUserPassword(guiUser.getUserPasswordText().getText());
		myUser.setUserRole(String.valueOf(guiUser.getUserRoleCombo()
				.getSelectedItem()));

		// Eine Verbindung zur Datenbank wird aufgebaut und der
		// Datensatz wird in die Datenbank gespeichert
		UserDB.newPassword(myUser);

		// Wenn der Datensatz erfolgreich gespeichert wurde, wird eine
		// entsprechende Meldung ausgegeben
		if (UserDB.successful == 1) {
			// Die Tabelle im WestPanel wird neu aufgebaut, damit der
			// neu angelegte Datensatz gleich angezeigt wird
			guiUser.reloadWestTable();

			// Folgende Meldung wird ausgegeben
			String successText = "Datensatz wurde erfolgreich gespeichert!";
			InfoSuccess.showMessage(successText);

			// Alle Textfelder werden zurückgesetzt, damit weitere
			// Datensätze eingegeben werden können
			guiUser.resetTableEast();

			// Suchbegriff wird zurückgesetzt
			guiUser.getSearchText().setText("");

			// Die Button "löschen", "Neues Passwort" und "Passwort setzen"
			// werden deaktiviert. Der Button "Speichern" wird wieder aktiviert.
			guiUser.getCreatePWButton().setEnabled(false);
			guiUser.getSavePWButton().setEnabled(false);
			guiUser.getDeleteButton().setEnabled(false);
			guiUser.getSaveButton().setEnabled(true);

			// Wenn der Datensatz nicht gespeichert werden konnte, wird eine
			// entsprechende Meldung ausgegeben
		} else {
			// Ein Dialogfenster mit folgender Meldung soll erzeugt werden
			String errorText = "Datensatz konnte nicht gespeichert werden!";
			InfoError.showMessage(errorText);

			// Alle Textfelder werden zurückgesetzt, damit der Datensatz
			// erneut eingegeben werden kann
			guiUser.resetTableEast();

			// Suchbegriff wird zurückgesetzt
			guiUser.getSearchText().setText("");

			// Die Button "löschen", "Neues Passwort" und "Passwort setzen"
			// werden deaktiviert. Der Button "Speichern" wird wieder aktiviert.
			guiUser.getCreatePWButton().setEnabled(false);
			guiUser.getSavePWButton().setEnabled(false);
			guiUser.getDeleteButton().setEnabled(false);
			guiUser.getSaveButton().setEnabled(true);
		}
	}

	/**
	 * Wenn auf den Button oder in der Menübar auf "Programm beenden" geklickt
	 * wird, werden alle offenen Verbindungen und das Fenster geschlossen
	 * 
	 */
	public void actionClose() {
		SQLDatabase.closeConnections();
		System.exit(0);
	}

}
