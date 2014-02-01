package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

import view.InfoError;

/**
 * Die Klasse "DatabaseConnection" stellte eine Verbindung zur MySQL-Datenbank
 * "book_database" her
 * 
 * @author Bergsocke
 * 
 */
public class ConnectionDatabase {

	private static Connection connect = null;

	/**
	 * Diese Methode baut die Datenbankverbindung zur Datenbank "book_database"
	 * auf
	 * 
	 * @return connect
	 */
	public static Connection connectDB() {

		try {
			Properties myProperties = new Properties();

			try {
				// die Datei "database.properties" wird geladen
				myProperties.load(ConnectionDatabase.class.getClassLoader()
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
}
