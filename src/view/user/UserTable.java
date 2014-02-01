package view.user;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import model.user.User;

/**
 * Die Klasse "UserTable" ist für den Aufbau und den Inhalt der Usertabelle
 * zuständig
 * 
 * @author Bergsocke
 * 
 */
public class UserTable extends AbstractTableModel {

	private static final long serialVersionUID = -6307716510674042079L;

	public List<User> userList;

	/**
	 * Konstruktor
	 * 
	 * @param userList
	 */
	public UserTable(List<User> userList) {
		this.userList = userList;
	}

	/**
	 * Rückgabe der Spaltenanzahl der Usertabelle
	 */
	public int getColumnCount() {
		int columnCount = 3;
		return columnCount;
	}

	/**
	 * Rückgabe der Anzahl der Datensätze, die sich in der Datenbank befinden
	 */
	public int getRowCount() {
		return userList.size();
	}

	/**
	 * Festlegung der Spaltenbeschriftungen
	 */
	public String getColumnName(int spaltenNr) {

		switch (spaltenNr) {
		case 0:
			return "ID-Nr";
		case 1:
			return "Username";
		case 2:
			return "Password";
		default:
			return null;
		}
	}

	/**
	 * Rückgabe des Inhalts der jeweiligen Tabellenzeile
	 */
	public Object getValueAt(int row, int column) {

		User myUser = (User) userList.get(row);

		switch (column) {
		case 0:
			return myUser.getUserID();
		case 1:
			return myUser.getUserName();
		case 2:
			return myUser.getUserPassword();
		default:
			return null;
		}
	}
}
