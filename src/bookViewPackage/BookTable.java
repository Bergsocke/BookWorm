package bookViewPackage;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import bookDatabasePackage.Book;

/**
 * Die Klasse "BookTable" ist f�r den Aufbau und den Inhalt der B�chertabelle
 * zust�ndig
 * 
 * @author Bergsocke
 * 
 */
public class BookTable extends AbstractTableModel {

	private static final long serialVersionUID = 7055287075166243444L;

	public List<Book> bookList;

	/**
	 * Konstruktor
	 * 
	 * @param bookList
	 */
	public BookTable(List<Book> bookList) {
		this.bookList = bookList;
	}

	/**
	 * R�ckgabe der Spaltenanzahl der B�chertabelle
	 */
	public int getColumnCount() {
		int columnCount = 9;
		return columnCount;
	}

	/**
	 * R�ckgabe der Anzahl der Datens�tze, die sich in der Datenbank befinden
	 */
	public int getRowCount() {
		return bookList.size();
	}

	/**
	 * Festlegung der Spaltenbeschriftungen
	 */
	public String getColumnName(int spaltenNr) {

		switch (spaltenNr) {
		case 0:
			return "ID-Nr";
		case 1:
			return "ISBN";
		case 2:
			return "Buch-Titel";
		case 3:
			return "Autor";
		case 4:
			return "Ausgabe-Jahr";
		case 5:
			return "Buch-Format";
		case 6:
			return "Kurzbeschreibung";
		case 7:
			return "Kommentar";
		case 8:
			return "gelesen";

		default:
			return null;
		}
	}

	/**
	 * R�ckgabe des Inhalts der jeweiligen Tabellenzeile
	 */
	public Object getValueAt(int row, int column) {

		Book myBook = (Book) bookList.get(row);

		switch (column) {
		case 0:
			return myBook.getId();
		case 1:
			return myBook.getIsbn();
		case 2:
			return myBook.getTitle();
		case 3:
			return myBook.getAuthor();
		case 4:
			return myBook.getPublicationDate();
		case 5:
			return myBook.getFormat();
		case 6:
			return myBook.getShortDescription();
		case 7:
			return myBook.getComment();
		case 8:
			return myBook.getRead();

		default:
			return null;
		}
	}
}
