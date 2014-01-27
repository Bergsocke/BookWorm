package bookworm.loginPackage;

/**
 * Die Klasse "LoginUser" repr�sentiert den Benutzer, der sich einloggen m�chte
 * 
 * @author Bergsocke
 * 
 */
public class LoginUser {

	private String username;
	private String userpassword;

	/**
	 * Konstruktor
	 * 
	 * @param username
	 * @param userpassword
	 */
	public LoginUser(String username, String userpassword) {
		this.username = username;
		this.userpassword = userpassword;
	}

	/**
	 * Definition der Getter und Setter
	 */
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getUserpassword() {
		return userpassword;
	}

	public void setUserpassword(String userpassword) {
		this.userpassword = userpassword;
	}
}