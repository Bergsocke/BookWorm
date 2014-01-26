package bookViewPackage;

import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import bookDatabasePackage.Book;
import bookDatabasePackage.BookDB;

/**
 * Die Klasse "BookTableListener" reagiert auf die Auswahl des Users in der
 * Tabelle im WestPanel. Wenn in der Tabelle ein Datensatz ausgew�hlt wurde,
 * werden die entsprechenden Daten in den Textfeldern im EastPanel angezeigt.
 * 
 * @author Bergsocke
 * 
 */
public class BookTableListener implements ListSelectionListener {

	BookGUI guiBook;

	/**
	 * Konstruktor
	 * 
	 * @param guiBook
	 */
	public BookTableListener(BookGUI guiBook) {
		this.guiBook = guiBook;
	}

	@Override
	public void valueChanged(ListSelectionEvent event) {

		if (!event.getValueIsAdjusting()) {
			ListSelectionModel lsm = (ListSelectionModel) event.getSource();
			if (lsm.isSelectionEmpty() == false) {
				try {
					BookDB.connectDB();
					int selectedRow = lsm.getMinSelectionIndex();

					String id = String.valueOf(guiBook.getBookTable()
							.getModel().getValueAt(selectedRow, 0));

					Book myBook = BookDB.findByID(id);

					guiBook.getBookIdText().setText(
							String.valueOf(myBook.getId()));
					guiBook.getIsbnText().setText(
							String.valueOf(myBook.getIsbn()));
					guiBook.getTitleText().setText(
							String.valueOf(myBook.getTitle()));
					guiBook.getAuthorText().setText(
							String.valueOf(myBook.getAuthor()));
					guiBook.getPublicationDateText().setText(
							String.valueOf(myBook.getPublicationDate()));
					guiBook.getFormatCombo().setSelectedItem(
							String.valueOf(myBook.getFormat()));
					guiBook.getShortDescriptionArea().setText(
							myBook.getShortDescription());
					guiBook.getCommentArea().setText(
							String.valueOf(myBook.getComment()));
					guiBook.getReadCombo().setSelectedItem(
							String.valueOf(myBook.getRead()));

					// Button "l�schen" wird sichtbar gesetzt
					guiBook.getDeleteButton().setEnabled(true);

				} catch (Exception e) {
					System.out.println(e.toString());
					JOptionPane
							.showMessageDialog(
									guiBook,
									"Der ausgew�hlter Datensatz kann nicht angezeigt werden",
									"Fehler", JOptionPane.ERROR_MESSAGE);
				}
			}
		}
	}
}
