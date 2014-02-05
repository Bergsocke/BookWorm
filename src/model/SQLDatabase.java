package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Properties;

import view.InfoError;

/**
 * Die Klasse "SQLDatabase" stellte eine Verbindung zur MySQL-Datenbank
 * "bookworm_database" her und führt die erforderlichen SQL-Befehle aus
 * 
 * @author Bergsocke
 * 
 */
public class SQLDatabase {

	private static Connection connect = null;
	private static PreparedStatement myPreparedStatement = null;
	private static ResultSet myResultSet = null;
	// Variable, die anzeigen soll, ob das Speichern, Updaten oder Löschen eines
	// Datensatzes erfolgreich war
	private static int successful = 0;

	/**
	 * Diese Methode baut die Datenbankverbindung zur Datenbank
	 * "bookworm_database" auf
	 * 
	 * @return connect
	 */
	public static Connection connectDB() {

		try {
			Properties myProperties = new Properties();

			try {
				// die Datei "database.properties" wird geladen
				myProperties.load(SQLDatabase.class.getClassLoader()
						.getResourceAsStream("database.properties"));

			} catch (Exception e) {
				System.out.println(e.toString());

				String errorText = "Die Datei \"database.properties\" konnte nicht gefunden werden!";
				InfoError.showMessage(errorText);
			}

			String driver = myProperties.getProperty("jdbc.driver");
			String connectionURL = myProperties.getProperty("jdbc.url");
			String username = myProperties.getProperty("jdbc.username");
			String password = myProperties.getProperty("jdbc.password");

			Class.forName(driver);

			connect = DriverManager.getConnection(connectionURL, username,
					password);

		} catch (Exception e) {
			System.out.println(e.toString());

			// Ein Dialogfenster mit entsprechender Meldung soll erzeugt werden
			String errorText = "Datenbankverbindung konnte nicht hergestellt werden. "
					+ "Bitte prüfen Sie, ob der MySQL-Server läuft.";
			InfoError.showMessage(errorText);
		}

		return connect;
	}

	/**
	 * Diese Methode führt die SQL-Befehle zum Anzeigen von Datensätzen aus
	 * 
	 * @param sqlStatement
	 * @return myResultSet
	 */
	public static ResultSet executeSQLQuery(String sqlStatement) {

		try {
			// Datenbankverbindung herstellen
			connect = connectDB();

			// PreparedStatement für den SQL-Befehl
			myPreparedStatement = connect.prepareStatement(sqlStatement);

			// SQL-Befehl wird ausgeführt
			myResultSet = myPreparedStatement.executeQuery();

		} catch (Exception e) {
			System.out.println(e.toString());
			// Ein Dialogfenster mit entsprechender Meldung soll erzeugt werden
			String errorText = "Datenbankabfrage Fehler!";
			InfoError.showMessage(errorText);
		}

		return myResultSet;
	}

	/**
	 * Diese Methode führt die SQL-Befehle zum Einfügen, Ändern oder Löschen
	 * eines Datensatzes aus
	 * 
	 * @param sqlStatement
	 * @return successful
	 */
	public static int executeSQLUpdate(String sqlStatement) {

		try {
			// Datenbankverbindung herstellen
			connect = connectDB();

			// PreparedStatement für den SQL-Befehl
			myPreparedStatement = connect.prepareStatement(sqlStatement);

			// SQL-Befehl wird ausgeführt
			successful = myPreparedStatement.executeUpdate(sqlStatement);

		} catch (Exception e) {
			System.out.println(e.toString());
			// Ein Dialogfenster mit entsprechender Meldung soll erzeugt werden
			String errorText = "Datenbankabfrage Fehler!";
			InfoError.showMessage(errorText);
		}

		return successful;
	}

	/**
	 * Methode zum Schließen aller offenen Verbindungen
	 */
	public static void closeConnections() {
		try {

			if (myResultSet != null) {
				myResultSet.close();
			}

			if (myPreparedStatement != null) {
				myPreparedStatement.close();
			}

			if (connect != null) {
				connect.close();
			}

		} catch (Exception e) {
			System.out.println(e.toString());

			// Ein Dialogfenster mit entsprechender Meldung soll erzeugt werden
			String errorText = "Verbindungen konnten nicht geschlossen werden.";
			InfoError.showMessage(errorText);
		}
	}
}
