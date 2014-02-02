package model.user;

/**
 * Die Klasse "User" repräsentiert den Anwender
 * 
 * @author Bergsocke
 * 
 */

public class User {

	private String userID;
	private String userName;
	private String userPassword;
	private String userRole;

	/**
	 * Konstruktor für Login von Anwendern
	 * 
	 * @param userName
	 * @param userPassword
	 */
	public User(String userName, String userPassword) {
		this.userName = userName;
		this.userPassword = userPassword;
	}

	/**
	 * Konstruktor für die Neuerfassung von Anwendern
	 * 
	 * @param userName
	 * @param userPassword
	 * @param userRole
	 */
	public User(String userName, String userPassword, String userRole) {
		this.userName = userName;
		this.userPassword = userPassword;
		this.userRole = userRole;
	}

	/**
	 * Konstruktur für den Zugriff auf alle Tabellenfelder
	 * 
	 * @param userID
	 * @param userName
	 * @param userPassword
	 * @param userRole
	 */
	public User(String userID, String userName, String userPassword,
			String userRole) {
		this.userID = userID;
		this.userName = userName;
		this.userPassword = userPassword;
		this.userRole = userRole;
	}

	/**
	 * Definition der Getter und Setter
	 */
	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserPassword() {
		return userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

	public String getUserRole() {
		return userRole;
	}

	public void setUserRole(String userRole) {
		this.userRole = userRole;
	}
}
