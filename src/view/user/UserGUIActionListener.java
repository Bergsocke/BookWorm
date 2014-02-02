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

/**
 * Mit der Klasse "UserGUIActionListener" werden die Aktionen für die Buttons
 * "alle anzeigen", "suchen", "neu", "speichern", "löschen", "Programm beenden"
 * sowie für die MenüBarItems "Neuen Datensatz anlegen", "Datensatz speichern",
 * "Datensatz löschen" und "Über das Programm" der Klasse UserGUI festgelegt.
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
		// wird zurückgesetzt
		if (event.getSource() instanceof JButton
				&& event.getActionCommand().contains("alle anzeigen")) {
			// Suchbegriff wird zurückgesetzt
			guiUser.getSearchText().setText("");
			guiUser.createUserTable();

			// EastTable wird zurückgesetzt
			guiUser.resetTableEast();

			guiUser.getDeleteButton().setEnabled(false);
		}

		// Wenn auf den Button "suchen" geklickt wird, wird in der Datenbank
		// nach dem entsprechenden Usernamen gesucht (mit Hilfe der Methode
		// "createUserTable()" aus der Klasse UserGUI)
		if (event.getSource() instanceof JButton
				&& event.getActionCommand().contains("suchen")) {
			guiUser.createUserTable();

			// EastTable wird zurückgesetzt
			guiUser.resetTableEast();

			guiUser.getDeleteButton().setEnabled(false);

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

		// Wenn auf den Button "Programm beenden" geklickt wird, werden alle
		// offenen Verbindungen und das Fenster geschlossen
		if (event.getSource() instanceof JButton
				&& event.getActionCommand().contains("Programm beenden")) {
			// Offene Datenbank-Verbindungen werden geschlossen
			SQLDatabase.closeConnections();

			System.exit(0);
		}

		// Wenn in der Menübar auf "Zur Bücherverwaltung wechseln" geklickt
		// wird, wird die Bücherverwaltungs-GUI aufgerufen
		if (event.getSource() instanceof JMenuItem
				&& event.getActionCommand().contains(
						"Zur Bücherverwaltung wechseln")) {
			// UserverwaltungsGUI wird geschlossen
			guiUser.setVisible(false);
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
	 * Button "speichern" in die Datenbank eingefügt. Der Button "löschen" wird
	 * deaktiviert.
	 */
	public void actionClear() {
		// Alle Textfelder werden zurückgesetzt
		guiUser.resetTableEast();
		// Löschen-Button wird deaktiviert
		guiUser.getDeleteButton().setEnabled(false);
	}

	/**
	 * Wenn auf den Button "speichern" oder in der Menübar auf
	 * "Datensatz speichern" geklickt wird, wird ein bereits vorhandener
	 * Datensatz in der Datenbank upgedatet bzw. wenn der Datensatz noch nicht
	 * in der Datenbank vorhanden ist, wird er in die Datenbank gespeichert.
	 */
	public void actionSave() {
		// Ist die ID leer, wird ein neuer Datensatz angelegt und in die
		// Datenbank gespeichert
		if (guiUser.getUserIDText().getText().matches("")) {

			User myUser = new User(String.valueOf(guiUser.getUserNameText()
					.getText()), String.valueOf(guiUser.getUserPasswordText()
					.getText()));

			// Eine Verbindung zur Datenbank wird aufgebaut und der neue
			// Datensatz wird in die Datenbank gespeichert
			UserDB.saveUser(myUser);

		} else {
			// Die eingegebenen Daten des bereits vorhandenen Datensatzes
			// werden eingelesen
			String userID = guiUser.getUserIDText().getText();

			User myUser = UserDB.findByID(userID);

			myUser.setUserName(guiUser.getUserNameText().getText());
			myUser.setUserPassword(guiUser.getUserPasswordText().getText());

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
		// Die Buch-ID des bereits vorhandenen Datensatzes wird ausgelesen
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
			}
		}
	}

}
