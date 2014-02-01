package view.book;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import model.SQLDatabase;
import model.book.Book;
import model.book.BookDB;
import view.InfoError;
import view.InfoSuccess;

/**
 * Mit der Klasse "BookActionListener" werden die Aktionen für die Buttons
 * "alle anzeigen", "suchen", "neu", "speichern", "löschen", "Programm beenden"
 * sowie für die MenüBarItems "Neuen Datensatz anlegen", "Datensatz speichern",
 * "Datensatz löschen" und "Über das Programm" der Klasse BookGUI festgelegt.
 * 
 * @author Bergsocke
 * 
 */
public class BookGUIActionListener implements ActionListener {

	BookGUI guiBook;

	/**
	 * Konstruktor
	 * 
	 * @param guiBook
	 */
	public BookGUIActionListener(BookGUI guiBook) {
		this.guiBook = guiBook;
	}

	@Override
	public void actionPerformed(ActionEvent event) {

		// Wenn auf den Button "alle anzeigen" geklickt wird, werden
		// alle Datensätze der Datenbank angezeigt (mit Hilfe der Methode
		// "createBookTable()" aus der Klasse BookGUI) und der Text im Suchfeld
		// wird zurückgesetzt
		if (event.getSource() instanceof JButton
				&& event.getActionCommand().contains("alle anzeigen")) {
			// Suchbegriff wird zurückgesetzt
			guiBook.getSearchText().setText("");
			guiBook.createBookTable();
			
			// EastTable wird zurückgesetzt
			guiBook.resetTableEast();
			
			guiBook.getDeleteButton().setEnabled(false);
		}

		// Wenn auf den Button "suchen" geklickt wird, wird in der Datenbank
		// nach dem entsprechenden Buchtitel gesucht (mit Hilfe der Methode
		// "createBookTable()" aus der Klasse BookGUI)

		// eigenes AL Objekt
		// instanceof Jbutton weg
		if (event.getSource() instanceof JButton
				&& event.getActionCommand().contains("suchen")) {
			guiBook.createBookTable();

			// EastTable wird zurückgesetzt
			guiBook.resetTableEast();
			
			guiBook.getDeleteButton().setEnabled(false);

			// Wenn kein Datensatz gefunden wurde, wird eine entsprechende
			// Meldung ausgegeben
			int row = guiBook.getBookTable().getModel().getRowCount();
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
	 * der Klasse "BookGUI"). Ein neuer Datensatz wird erst beim Klick auf den
	 * Button "speichern" in die Datenbank eingefügt. Der Button "löschen" wird
	 * deaktiviert.
	 */
	public void actionClear() {
		// Alle Textfelder werden zurückgesetzt
		guiBook.resetTableEast();
		// Löschen-Button wird deaktiviert
		guiBook.getDeleteButton().setEnabled(false);
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
		if (guiBook.getBookIdText().getText().matches("")) {

			Book myBook = new Book(
					String.valueOf(guiBook.getIsbnText().getText()),
					String.valueOf(guiBook.getTitleText().getText()),
					String.valueOf(guiBook.getAuthorText().getText()),
					String.valueOf(guiBook.getPublicationDateText().getText()),
					String.valueOf(guiBook.getFormatCombo().getSelectedItem()),
					String.valueOf(guiBook.getShortDescriptionArea().getText()),
					String.valueOf(guiBook.getCategoryCombo().getSelectedItem()),
					String.valueOf(guiBook.getCommentArea().getText()), String
							.valueOf(guiBook.getReadCombo().getSelectedItem()));

			// Eine Verbindung zur Datenbank wird aufgebaut und der neue
			// Datensatz wird in die Datenbank gespeichert
			BookDB.saveBook(myBook);

		} else {
			// Die eingegebenen Daten des bereits vorhandenen Datensatzes
			// werden eingelesen
			String BookID = guiBook.getBookIdText().getText();

			Book myBook = BookDB.findByID(BookID);

			myBook.setIsbn(guiBook.getIsbnText().getText());
			myBook.setTitle(guiBook.getTitleText().getText());
			myBook.setAuthor(guiBook.getAuthorText().getText());
			myBook.setPublicationDate(guiBook.getPublicationDateText()
					.getText());
			myBook.setFormat(String.valueOf(guiBook.getFormatCombo()
					.getSelectedItem()));
			myBook.setShortDescription(guiBook.getShortDescriptionArea()
					.getText());
			myBook.setCategory(String.valueOf(guiBook.getCategoryCombo()
					.getSelectedItem()));
			myBook.setComment(guiBook.getCommentArea().getText());
			myBook.setRead(String.valueOf(guiBook.getReadCombo()
					.getSelectedItem()));

			// Eine Verbindung zur Datenbank wird aufgebaut und der
			// Datensatz wird in die Datenbank gespeichert
			BookDB.updateBook(myBook);
		}

		// Wenn der Datensatz erfolgreich gespeichert wurde, wird eine
		// entsprechende Meldung ausgegeben
		if (BookDB.successful == 1) {
			// Die Tabelle im WestPanel wird neu aufgebaut, damit der
			// neu angelegte Datensatz gleich angezeigt wird
			guiBook.reloadWestTable();

			// Folgende Meldung wird ausgegeben
			String successText = "Datensatz wurde erfolgreich gespeichert!";
			InfoSuccess.showMessage(successText);

			// Alle Textfelder werden zurückgesetzt, damit weitere
			// Datensätze eingegeben werden können
			guiBook.resetTableEast();

			// Suchbegriff wird zurückgesetzt
			guiBook.getSearchText().setText("");

			// Wenn der Datensatz nicht gespeichert werden konnte, wird eine
			// entsprechende Meldung ausgegeben
		} else {
			// Ein Dialogfenster mit folgender Meldung soll erzeugt werden
			String errorText = "Datensatz konnte nicht gespeichert werden!";
			InfoError.showMessage(errorText);

			// Alle Textfelder werden zurückgesetzt, damit der Datensatz
			// erneut eingegeben werden kann
			guiBook.resetTableEast();

			// Suchbegriff wird zurückgesetzt
			guiBook.getSearchText().setText("");
		}
	}

	/**
	 * Wenn auf den Button "löschen" oder in der Menübar auf "Datensatz löschen"
	 * geklickt wird, wird der Datensatz aus der Datenbank gelöscht
	 */
	public void actionDelete() {
		// Die Buch-ID des bereits vorhandenen Datensatzes wird ausgelesen
		String BookID = guiBook.getBookIdText().getText();

		// Es wird gefragt, ob der Datensatz wirklich gelöscht werden soll
		int check = JOptionPane.showConfirmDialog(guiBook,
				"Soll der Datensatz wirklich gelöscht werden?");

		// Wird mit "Ja" bestätigt, wird der Datensatz gelöscht
		if (check == 0) {
			// Eine Verbindung zur Datenbank wird aufgebaut und der
			// Datensatz wird aus der Datenbank gelöscht
			BookDB.deleteBook(BookID);

			// Wenn der Datensatz erfolgreich gelöscht wurde, wird eine
			// entsprechende Meldung ausgegeben
			if (BookDB.successful == 1) {
				// Die Tabelle im WestPanel wird neu aufgebaut, damit der
				// gelöschte Datensatz nicht mehr angezeigt wird
				guiBook.reloadWestTable();

				// Folgende Meldung wird ausgegeben
				String successText = "Datensatz wurde erfolgreich gelöscht!";
				InfoSuccess.showMessage(successText);

				// Der Text im Suchfeld wird zurückgesetzt
				guiBook.getSearchText().setText("");
				// Alle Textfelder werden zurückgesetzt
				guiBook.resetTableEast();

				// Wenn der Datensatz nicht gelöscht werden konnte, wird eine
				// entsprechende Meldung ausgegebe
			} else {
				// Ein Dialogfenster mit folgender Meldung soll erzeugt werden
				String errorText = "Datensatz konnte nicht gelöscht werden!";
				InfoError.showMessage(errorText);

				// Suchbegriff wird zurückgesetzt
				guiBook.getSearchText().setText("");
				// Alle Textfelder werden zurückgesetzt
				guiBook.resetTableEast();
			}
		}
	}
}
