package bookworm.viewPackage;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import bookworm.databasePackage.Book;
import bookworm.databasePackage.BookDB;

/**
 * Mit der Klasse "BookActionListener" werden die Aktionen f�r die Buttons
 * "alle anzeigen", "suchen", "neu", "speichern", "l�schen", "Programm beenden"
 * sowie f�r die Men�BarItems "Neuen Datensatz anlegen", "Datensatz speichern",
 * "Datensatz l�schen" und "�ber das Programm" der Klasse BookGUI festgelegt.
 * 
 * @author Bergsocke
 * 
 */
public class BookWormGUIActionListener implements ActionListener {

	BookWormGUI guiBook;

	/**
	 * Konstruktor
	 * 
	 * @param guiBook
	 */
	public BookWormGUIActionListener(BookWormGUI guiBook) {
		this.guiBook = guiBook;
	}

	@Override
	public void actionPerformed(ActionEvent event) {

		// Wenn auf den Button "alle anzeigen" geklickt wird, werden
		// alle Datens�tze der Datenbank angezeigt (mit Hilfe der Methode
		// "createBookTable()" aus der Klasse BookGUI) und der Text im Suchfeld
		// wird zur�ckgesetzt
		if (event.getSource() instanceof JButton
				&& event.getActionCommand().contains("alle anzeigen")) {
			guiBook.createBookTable();
			guiBook.getSearchText().setText("");
		}

		// Wenn auf den Button "suchen" geklickt wird, wird in der Datenbank
		// nach dem entsprechenden Buchtitel gesucht (mit Hilfe der Methode
		// "createBookTable()" aus der Klasse BookGUI)
		if (event.getSource() instanceof JButton
				&& event.getActionCommand().contains("suchen")) {
			guiBook.createBookTable();
			// Suchbegriff wird zur�ckgesetzt
			guiBook.getSearchText().setText("");

			// Wenn kein Datensatz gefunden wurde, wird eine entsprechende
			// Meldung ausgegeben
			int row = guiBook.getBookTable().getModel().getRowCount();
			if (row == 0) {
				// Folgende Meldung wird ausgegeben
				JOptionPane.showMessageDialog(guiBook,
						"Es wurde kein Datensatz gefunden!", "",
						JOptionPane.INFORMATION_MESSAGE);
			}
		}

		// Wenn auf den Button "neu" oder in der Men�bar auf
		// "Neuen Datensatz anlegen" geklickt wird , wird der Inhalt der
		// Textfelder im EastPanel zur�ckgesetzt. Ein neuer Datensatz wird
		// erst beim Klick auf den Button "speichern" in die Datenbank
		// eingef�gt.
		if (event.getSource() instanceof JButton
				&& event.getActionCommand().contains("neu")) {
			this.actionClear();
		}
		if (event.getSource() instanceof JMenuItem
				&& event.getActionCommand().contains("Neuen Datensatz anlegen")) {
			this.actionClear();
		}

		// Wenn auf den Button "speichern" oder in der Men�bar auf
		// "Datensatz speichern" geklickt wird, wird ein bereits vorhandener
		// Datensatz in der Datenbank upgedatet.
		// Ist der Datensatz noch nicht in der Datenbank vorhanden, wird er in
		// die Datenbank eingef�gt.
		if (event.getSource() instanceof JButton
				&& event.getActionCommand().contains("speichern")) {
			this.actionSave();
		}
		if (event.getSource() instanceof JMenuItem
				&& event.getActionCommand().contains("Datensatz speichern")) {
			this.actionSave();
		}

		// Wenn auf den Button "l�schen" oder in der Men�bar auf
		// "Datensatz l�schen" geklickt wird, wird der Datensatz aus der
		// Datenbank gel�scht
		if (event.getSource() instanceof JButton
				&& event.getActionCommand().contains("l�schen")) {
			this.actionDelete();
		}
		if (event.getSource() instanceof JMenuItem
				&& event.getActionCommand().contains("Datensatz l�schen")) {
			this.actionDelete();
		}

		// Wenn auf den Button "Programm beenden" geklickt wird, werden alle
		// offenen Verbindungen und das Fenster geschlossen
		if (event.getSource() instanceof JButton
				&& event.getActionCommand().contains("Programm beenden")) {
			// Offene Datenbank-Verbindungen werden geschlossen
			BookDB.closeConnections();

			System.exit(0);
		}

		// Wenn in der Men�bar auf "�ber das Programm" geklickt wird, wird ein
		// Dialogfenster erzeugt
		if (event.getSource() instanceof JMenuItem
				&& event.getActionCommand().contains("�ber das Programm")) {
			// Folgende Meldung wird ausgegeben
			JOptionPane.showMessageDialog(guiBook,
					"Erstellt von Weinberger Eva, 2014", "",
					JOptionPane.INFORMATION_MESSAGE);
		}
	}

	/**
	 * Wenn auf den Button "neu" oder in der Men�bar auf
	 * "Neuen Datensatz anlegen" geklickt wird, wird der Inhalt der Textfelder
	 * im EastPanel zur�ckgesetzt (mit Hilfe der Methode "resetTableEast()" aus
	 * der Klasse "BookGUI"). Ein neuer Datensatz wird erst beim Klick auf den
	 * Button "speichern" in die Datenbank eingef�gt. Der Button "l�schen" wird
	 * deaktiviert.
	 */
	public void actionClear() {
		// Alle Textfelder werden zur�ckgesetzt
		guiBook.resetTableEast();
		// L�schen-Button wird deaktiviert
		guiBook.getDeleteButton().setEnabled(false);
	}

	/**
	 * Wenn auf den Button "speichern" oder in der Men�bar auf
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
			JOptionPane.showMessageDialog(guiBook,

			"Datensatz wurde erfolgreich gespeichert!", "",
					JOptionPane.INFORMATION_MESSAGE);

			// Alle Textfelder werden zur�ckgesetzt, damit weitere
			// Datens�tze eingegeben werden k�nnen
			guiBook.resetTableEast();

			// Suchbegriff wird zur�ckgesetzt
			guiBook.getSearchText().setText("");

			// Wenn der Datensatz nicht gespeichert werden konnte, wird eine
			// entsprechende Meldung ausgegeben
		} else {
			// Folgende Meldung wird ausgegeben
			JOptionPane.showMessageDialog(guiBook,
					"Datensatz konnte nicht gespeichert werden!", "Fehler",
					JOptionPane.ERROR_MESSAGE);

			// Alle Textfelder werden zur�ckgesetzt, damit der Datensatz
			// erneut eingegeben werden kann
			guiBook.resetTableEast();

			// Suchbegriff wird zur�ckgesetzt
			guiBook.getSearchText().setText("");
		}
	}

	/**
	 * Wenn auf den Button "l�schen" oder in der Men�bar auf "Datensatz l�schen"
	 * geklickt wird, wird der Datensatz aus der Datenbank gel�scht
	 */
	public void actionDelete() {
		// Die Buch-ID des bereits vorhandenen Datensatzes wird ausgelesen
		String BookID = guiBook.getBookIdText().getText();

		// Es wird gefragt, ob der Datensatz wirklich gel�scht werden soll
		int check = JOptionPane.showConfirmDialog(guiBook,
				"Soll der Datensatz wirklich gel�scht werden?");

		// Wird mit "Ja" best�tigt, wird der Datensatz gel�scht
		if (check == 0) {
			// Eine Verbindung zur Datenbank wird aufgebaut und der
			// Datensatz wird aus der Datenbank gel�scht
			BookDB.deleteBook(BookID);

			// Wenn der Datensatz erfolgreich gel�scht wurde, wird eine
			// entsprechende Meldung ausgegeben
			if (BookDB.successful == 1) {
				// Die Tabelle im WestPanel wird neu aufgebaut, damit der
				// gel�schte Datensatz nicht mehr angezeigt wird
				guiBook.reloadWestTable();

				// Folgende Meldung wird ausgegeben
				JOptionPane.showMessageDialog(guiBook,
						"Datensatz wurde erfolgreich gel�scht!", " ",
						JOptionPane.INFORMATION_MESSAGE);

				// Der Text im Suchfeld wird zur�ckgesetzt
				guiBook.getSearchText().setText("");
				// Alle Textfelder werden zur�ckgesetzt
				guiBook.resetTableEast();

				// Wenn der Datensatz nicht gel�scht werden konnte, wird eine
				// entsprechende Meldung ausgegebe
			} else {
				// Folgende Meldung wird ausgegeben
				JOptionPane.showMessageDialog(guiBook,
						"Datensatz konnte nicht gel�scht werden!", "Fehler",
						JOptionPane.ERROR_MESSAGE);

				// Suchbegriff wird zur�ckgesetzt
				guiBook.getSearchText().setText("");
				// Alle Textfelder werden zur�ckgesetzt
				guiBook.resetTableEast();
			}
		}
	}
}
