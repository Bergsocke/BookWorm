package view.user;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

import model.user.User;
import model.user.UserDB;
import view.book.BookGUI;

/**
 * Die Klasse "UserGUI" ist für den Aufbau der grafischen Oberfläche der
 * User-Verwaltung zuständig. Hier werden die einzelnen Komponenten des Fensters
 * (Textfelder, Bezeichungsfelder, Buttons und die Usertabelle) festgelegt.
 * 
 * @author Bergsocke
 * 
 */

public class UserGUI extends JFrame {

	private static final long serialVersionUID = -2334577762951716368L;

	// Festlegung der Schriftart für die Bezeichnungsfelder und Buttons
	private static String labelFont = "Verdana";
	private static int labelStyle = Font.BOLD;
	private static int labelSize = 12;

	// Festlegung der Schriftart für die Textfelder und der Tabelle
	private static String textFont = "Arial";
	private static int textStyle = Font.PLAIN;
	private static int textSize = 12;

	// Deklaration der erforderlichen Komponenten
	private JPanel northPanel;
	private JPanel eastPanel;
	private JPanel westPanel;

	private JLabel searchLabel;
	private JTextField searchText;
	private JButton searchButton;
	private JComboBox<String> searchCombo;

	private JButton allButton;

	private JLabel userIDLabel;
	private JTextField userIDText;

	private JLabel userNameLabel;
	private JTextField userNameText;

	private JLabel userPasswordLabel;
	private JTextField userPasswordText;

	private JLabel userRoleLabel;
	private JComboBox<String> userRoleCombo;

	private JButton clearButton;
	private JButton saveButton;
	private JButton deleteButton;
	private JButton createPWButton;
	private JButton savePWButton;
	private JButton closeButton;

	private JLabel necessary;

	private JTable userTable;

	private JScrollPane tableScroll;

	private JMenuBar userMenuBar;
	private JMenu changeMenu;
	private JMenuItem changeMenuItem;
	private JMenu logoutMenu;
	private JMenuItem logoutMenuItem;
	private JMenuItem closeMenuItem;
	private JMenu helpMenu;
	private JMenuItem helpMenuItem;

	private User loginUser;

	/**
	 * Konstruktur (Fensterbeschriftung und Initialisierung der Komponenten)
	 * 
	 * @param frameTitle
	 * @param loginUser
	 */
	public UserGUI(String frameTitle, User loginUser) {

		super(frameTitle);

		this.loginUser = loginUser;

		// Initialisierung der Fenster-Komponenten
		this.initComponents();
	}

	/**
	 * Die Userverwaltungs-GUI wird aufgebaut
	 * 
	 * @param loginUser
	 */

	public static void letStartedUserGUI(User loginUser) {

		// Aufruf des Konstruktors der Klasse UserGUI und Zuweisung der
		// Überschrift
		UserGUI gui = new UserGUI("BOOKWORM - USERVERWALTUNG", loginUser);

		// Fenstergröße wird automatisch an den Inhalt angepasst
		gui.pack();

		// Fenstergröße soll nicht verändert werden können
		gui.setResizable(false);

		// Positionierung am Desktop
		gui.setLocation(200, 200);

		// Window-Close-Funktion
		gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Fenster wird auf sichtbar gesetzt
		gui.setVisible(true);
	}

	/**
	 * Eine Menüleiste und der Inhalt der einzelnen JPanels (North, East, West)
	 * werden zugewiesen
	 */
	private void initComponents() {

		// MenüBar wird erzeugt
		this.initMenuBar();

		// North-, East-, und WestPanels werden erzeugt
		this.initComponentsNorth();
		this.initComponentsEast();
		this.initComponentsWest();
	}

	/**
	 * Zuweisung des Inhalts der einzelnen Komponenten, die in der MenüBar
	 * ausgegeben werden.
	 */
	private void initMenuBar() {

		userMenuBar = new JMenuBar();

		userMenuBar.setBackground(Color.GREEN.darker());

		ActionListener myActionListener = new UserGUIActionListener(this,
				loginUser);

		changeMenu = new JMenu("Wechseln");
		changeMenuItem = new JMenuItem("Zur Bücherverwaltung wechseln");
		changeMenu.add(changeMenuItem);
		changeMenuItem.addActionListener(myActionListener);

		logoutMenu = new JMenu("Abmelden");
		logoutMenuItem = new JMenuItem("Benutzer abmelden");
		closeMenuItem = new JMenuItem("Programm beenden");
		logoutMenu.add(logoutMenuItem);
		logoutMenu.add(closeMenuItem);
		logoutMenuItem.addActionListener(myActionListener);
		closeMenuItem.addActionListener(myActionListener);

		helpMenu = new JMenu("Hilfe");
		helpMenuItem = new JMenuItem("Über das Programm");
		helpMenu.add(helpMenuItem);
		helpMenuItem.addActionListener(myActionListener);

		// Hinzufügen der einzelnen Komponenten zur Menübar
		userMenuBar.add(changeMenu);
		userMenuBar.add(logoutMenu);
		userMenuBar.add(helpMenu);

		// Hinzufügen der Menübar zum Frame
		this.setJMenuBar(userMenuBar);
	}

	/**
	 * Zuweisung von Inhalt und Form der einzelnen Komponenten, die im
	 * North-Panel ausgegeben werden. Mit der Möglichkeit zur Suche nach einem
	 * Anwendernamen bzw. zur Anzeige aller Datensätze
	 */
	private void initComponentsNorth() {

		northPanel = new JPanel();

		ActionListener myActionListener = new UserGUIActionListener(this,
				loginUser);

		searchLabel = new JLabel("Suchkriterium auswählen ");
		searchLabel.setFont(new Font(textFont, labelStyle, 14));

		searchCombo = new JComboBox<String>();
		searchCombo.setBackground(Color.white);
		searchCombo.setFont(new Font(textFont, textStyle, textSize));
		// Festlegung des Inhalts der Combo-Box "searchCombo"
		String[] search = { "Anwender", "Rolle" };
		for (int i = 0; i < search.length; i++) {
			searchCombo.addItem(search[i]);
		}
		searchCombo.addActionListener(myActionListener);

		searchText = new JTextField("Bitte Suchbegriff eingeben", 20);
		searchText.setFont(new Font(textFont, textStyle, textSize));
		searchText.setSelectionStart(0);
		searchText.setSelectionEnd(30);

		// Icon für den Buttton "suchen"
		final Icon searchIcon = new ImageIcon(
				BookGUI.class.getResource("/view/images/searchIcon.png"));
		searchButton = new JButton("suchen  ", searchIcon);
		searchButton.setFont(new Font(labelFont, labelStyle, labelSize));
		searchButton.setBackground(Color.lightGray);
		searchButton.setBorder(BorderFactory.createRaisedBevelBorder());
		searchButton.addActionListener(myActionListener);

		// Icon für den Buttton "alle anzeigen"
		final Icon showAllIcon = new ImageIcon(
				BookGUI.class.getResource("/view/images/peopleIcon.png"));
		allButton = new JButton("alle anzeigen  ", showAllIcon);
		allButton.setFont(new Font(labelFont, labelStyle, labelSize));
		allButton.setBackground(Color.lightGray);
		allButton.setBorder(BorderFactory.createRaisedBevelBorder());
		allButton.addActionListener(myActionListener);

		// Hinzufügen der einzelnen Komponenten zum NorthPanel
		northPanel.add(searchLabel);
		northPanel.add(searchCombo);
		northPanel.add(searchText);
		northPanel.add(searchButton);
		northPanel.add(allButton);

		// Hinzufügen des NorthPanel zum Frame
		this.getContentPane().add(northPanel, BorderLayout.NORTH);
	}

	/**
	 * Zuweisung von Inhalt und Form der einzelnen Komponenten, die im
	 * East-Panel ausgegeben werden. Felder für die Ausgabe, Bearbeitung,
	 * Neuerfassung und Löschen einzelner User
	 */
	private void initComponentsEast() {

		eastPanel = new JPanel();

		// Layout für 10 Zeilen und 2 Spalten
		eastPanel.setLayout(new GridLayout(10, 2));

		// Unsichtbarer Rahmen wird gesetzt, um Abstand zum Frame zu bekommen
		eastPanel.setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 15));

		ActionListener myActionListener = new UserGUIActionListener(this,
				loginUser);

		userIDLabel = new JLabel("User-ID: ");
		userIDLabel.setFont(new Font(labelFont, labelStyle, labelSize));
		// Festlegung der Länge des Textfeldes, die anderen Textfelder werden
		// dann ebenfalls an diese Größe angepasst
		userIDText = new JTextField(25);
		userIDText.setFont(new Font(textFont, textStyle, textSize));

		// Nachdem die User-ID automatisch von der Datenbank vergeben wird, soll
		// das Feld "User-ID" nicht bearbeitet werden können. Aus diesem Grund
		// wird es auf nicht editierbar gesetzt. Aus diesem Feld wird beim
		// Updaten, Speichern oder Löschen eines Datensatzes die User-ID
		// ausgelesen
		userIDText.setEditable(false);

		userNameLabel = new JLabel("Username: *");
		userNameLabel.setFont(new Font(labelFont, labelStyle, labelSize));
		userNameText = new JTextField();
		userNameText.setFont(new Font(textFont, textStyle, textSize));

		// Nachdem das Userpasswort nicht als Klartext ausgelesen werden kann,
		// soll das Feld "Userpasswort" nicht angezeigt werden. Damit
		// soll verhindert werden, dass das gehashte Passwort nochmals gehasht
		// in die Datenbank abgespeichert wird.
		// Soll für einen Anwender ein neues Passwort vergeben werden, ist
		// hierfür der Button "Neues Passwort" zu klicken.
		userPasswordLabel = new JLabel("Userpassword: ");
		userPasswordLabel.setFont(new Font(labelFont, labelStyle, labelSize));
		userPasswordText = new JTextField();
		userPasswordText.setFont(new Font(textFont, textStyle, textSize));
		userPasswordText.setVisible(false);

		userRoleLabel = new JLabel("Rolle: ");
		userRoleLabel.setFont(new Font(labelFont, labelStyle, labelSize));
		userRoleCombo = new JComboBox<String>();
		userRoleCombo.setBackground(Color.white);
		userRoleCombo.setFont(new Font(textFont, textStyle, textSize));
		// Festlegung des Inhalts der Combo-Box "userRoleCombo"
		String[] role = { "Anwender", "Administrator" };
		for (int i = 0; i < role.length; i++) {
			userRoleCombo.addItem(role[i]);
		}

		// Icon für den Buttton "Neu"
		final Icon newIcon = new ImageIcon(
				BookGUI.class.getResource("/view/images/newIcon.png"));
		clearButton = new JButton("     Neu", newIcon);
		clearButton.setFont(new Font(labelFont, labelStyle, labelSize));
		clearButton.setBackground(Color.lightGray);
		clearButton.setBorder(BorderFactory.createRaisedBevelBorder());
		// Wenn auf den Button "neu" geklickt wird, soll der Inhalt der
		// Textfelder im EastPanel zurückgesetzt werden. Ein neuer Datensatz
		// wird erst beim Klick auf den Button "speichern" in die Datenbank
		// eingefügt.
		clearButton.addActionListener(myActionListener);

		// Icon für den Buttton "speichern"
		final Icon saveIcon = new ImageIcon(
				BookGUI.class.getResource("/view/images/saveIcon.png"));
		saveButton = new JButton("     speichern", saveIcon);
		saveButton.setFont(new Font(labelFont, labelStyle, labelSize));
		saveButton.setBackground(Color.lightGray);
		saveButton.setBorder(BorderFactory.createRaisedBevelBorder());
		// wenn auf den Button "speichern" geklickt wird, soll der Datensatz in
		// die Datenbank gespeichert werden
		saveButton.addActionListener(myActionListener);
		// Der Button "speichern" ist zu Beginn/beim Erscheinen des Fensters
		// noch nicht auswählbar; er wird erst sichtbar, wenn ein Datensatz
		// ausgewählt oder der Button "Neu" geklickt wurde
		saveButton.setEnabled(false);

		// Icon für den Buttton "löschen"
		final Icon deleteIcon = new ImageIcon(
				BookGUI.class.getResource("/view/images/deleteIcon.png"));
		deleteButton = new JButton("     löschen", deleteIcon);
		deleteButton.setFont(new Font(labelFont, labelStyle, labelSize));
		deleteButton.setBackground(Color.lightGray);
		deleteButton.setBorder(BorderFactory.createRaisedBevelBorder());
		// wenn auf den Button "löschen" geklickt wird, soll der Datensatz
		// aus der Datenbank gelöscht werden
		deleteButton.addActionListener(myActionListener);
		// Der Button "löschen" ist zu Beginn/beim Erscheinen des Fensters
		// noch nicht auswählbar; er wird erst sichtbar, wenn ein Datensatz
		// ausgewählt wurde
		deleteButton.setEnabled(false);

		// Icon für den Buttton "Passwort vergeben"
		final Icon passwordNewIcon = new ImageIcon(
				BookGUI.class.getResource("/view/images/passwordNewIcon.png"));
		createPWButton = new JButton("Passwort vergeben", passwordNewIcon);
		createPWButton.setFont(new Font(labelFont, labelStyle, labelSize));
		createPWButton.setBackground(Color.lightGray);
		createPWButton.setBorder(BorderFactory.createRaisedBevelBorder());
		// wenn auf den Button "Neues Passwort" geklickt wird, soll für einen
		// bereits erfassten Anwender ein neues Passwort vergeben werden können;
		// die Passwort-Änderung wird erst beim Klick auf den Button
		// "Passwort setzen" durchgeführt
		createPWButton.addActionListener(myActionListener);
		// Der Button "Neues Passwort" ist zu Beginn/beim Erscheinen des
		// Fensters noch nicht auswählbar; er wird erst sichtbar, wenn ein
		// Datensatz ausgewählt wurde
		createPWButton.setEnabled(false);

		// Icon für den Buttton "Passwort vergeben"
		final Icon passwordSetIcon = new ImageIcon(
				BookGUI.class.getResource("/view/images/passwordSetIcon.png"));
		savePWButton = new JButton("Passwort setzen", passwordSetIcon);
		savePWButton.setFont(new Font(labelFont, labelStyle, labelSize));
		savePWButton.setBackground(Color.lightGray);
		savePWButton.setBorder(BorderFactory.createRaisedBevelBorder());
		// wenn auf den Button "Passwort speichern" geklickt wird, soll das neu
		// gesetzte Passwort in die Datenbank gespeichert werden
		savePWButton.addActionListener(myActionListener);
		// Der Button "Passwort setzen" ist zu Beginn/beim Erscheinen des
		// Fensters noch nicht auswählbar; er wird erst sichtbar, wenn ein
		// Datensatz ausgewählt wurde
		savePWButton.setEnabled(false);

		// Icon für den Buttton "Programm beenden"
		final Icon closeIcon = new ImageIcon(
				BookGUI.class.getResource("/view/images/closeIcon.png"));
		closeButton = new JButton(" Programm beenden", closeIcon);
		closeButton.setFont(new Font(labelFont, labelStyle, labelSize));
		closeButton.setBackground(Color.lightGray);
		closeButton.setBorder(BorderFactory.createRaisedBevelBorder());
		// wenn auf den Button "Programm beenden" geklickt wird, soll das
		// Fenster geschlossen werden
		closeButton.addActionListener(myActionListener);

		// Dummy-Labels für Abstand zwischen der Tabelle und den Buttons
		JLabel dummyLabel = new JLabel();
		JLabel dummyLabel2 = new JLabel();
		// Dummy-Labels für Abstand zwischen den Buttons und dem unteren
		// Fensterrand
		JLabel dummyLabel3 = new JLabel();
		JLabel dummyLabel4 = new JLabel();

		necessary = new JLabel("* Pflichteingabe");
		necessary.setFont(new Font(labelFont, labelStyle, 10));

		// Hinzufügen der einzelnen Komponenten zum EastPanel
		eastPanel.add(userIDLabel);
		eastPanel.add(userIDText);

		eastPanel.add(userNameLabel);
		eastPanel.add(userNameText);

		eastPanel.add(userRoleLabel);
		eastPanel.add(userRoleCombo);

		eastPanel.add(userPasswordLabel);
		eastPanel.add(userPasswordText);

		// Dummy-Labels für Abstand zwischen der Tabelle und den Buttons
		eastPanel.add(dummyLabel);
		eastPanel.add(dummyLabel2);

		eastPanel.add(clearButton);
		eastPanel.add(saveButton);
		eastPanel.add(deleteButton);
		eastPanel.add(createPWButton);
		eastPanel.add(savePWButton);
		eastPanel.add(closeButton);

		eastPanel.add(necessary);

		// Dummy-Labels für Abstand zwischen den Buttons und dem unteren
		// Fensterrand
		eastPanel.add(dummyLabel3);
		eastPanel.add(dummyLabel4);

		// Hinzufügen des EastPanel zum Fenster
		this.getContentPane().add(eastPanel, BorderLayout.EAST);
	}

	/**
	 * Zuweisung von Inhalt und Form der einzelnen Komponenten, die im
	 * West-Panel ausgegeben werden.
	 */
	private void initComponentsWest() {
		// Initailisierung der Usertabelle. Nachdem auch von anderen
		// Teilen des Programms auf die Tabelle zugegriffen wird bzw.
		// die Tabelle für die Suchfunktion neu aufgebaut werden muss,
		// wird dieser Teil in eine eigene Methode geschrieben.

		createUserTable();
	}

	/**
	 * Aufbau der Usertabelle (diese wird im WestPanel ausgegeben)
	 * 
	 * @return userTable
	 */
	public JTable createUserTable() {

		// Datenbankverbindung
		UserDB myUserDB = new UserDB();

		// Bei der erstmaligen Initialisierung des Fensters gibt es noch keine
		// Darstellungsprobleme mit der Tabelle. Es werden alle Datensätze
		// angezeigt
		if (getSearchText().getText().matches("Bitte Suchbegriff eingeben")) {
			// alle Datensätze werden angezeigt
			userTable = new JTable(new UserTable(myUserDB.displayAll()));

		} else {
			// Damit bei Eingabe eines Suchbegriffes die Tabelle neu aufgebaut
			// wird, wird sie zunächst einmal aus dem WestPanel entfernt, danach
			// wird nach dem entsprechenden Suchbegriff gesucht (im
			// Datenbankfeld "username") und am Ende werden die Suchergebnisse
			// in einer neu erstellten Tabelle angezeigt
			this.getContentPane().remove(westPanel);

			// Einlesen des Suchkriteriums (Anwender, Rolle)
			String searchKey = String.valueOf(this.getSearchCombo()
					.getSelectedItem());

			userTable = new JTable(new UserTable(myUserDB.findUser(searchKey,
					getSearchText().getText())));
		}
		// WestPanel wird aufgebaut
		this.createteWestTable();

		return userTable;
	}

	/**
	 * WestPanel wird aufgebaut. Weiters wird das Layout der Tabelle im
	 * WestPanel festgelegt.
	 */
	private void createteWestTable() {

		westPanel = new JPanel();

		// Falls die Spalten zu breit für den verfügbaren Platz sind,
		// soll eine Scrollbar zur Verfügung stehen
		tableScroll = new JScrollPane(userTable);
		// Festlegung der Tabellengröße
		tableScroll.setPreferredSize(new Dimension(600, 300));
		// dem WestPanel zuweisen
		westPanel.add(tableScroll);

		// Hinzufügen des WestPanel zum Fenster
		this.getContentPane().add(westPanel, BorderLayout.WEST);
		// Sichtbar machen
		this.setVisible(true);

		// Die automatische Größenausrichtung der Spaltenbreite der Tabelle wird
		// ausgeschalten. Ansonsten kann eine bestimmte Spaltengröße nicht
		// festgelegt werden; die Spaltenbreite würde sonst automatisch nach dem
		// verfügbaren Platz festgelegt werden
		userTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

		// Autosortierung wird gesetzt
		userTable.setAutoCreateRowSorter(true);

		// Festlegung der Schrifteigenschaften
		userTable.setFont(new Font(textFont, textStyle, textSize));

		// Festlegung der Zeilenhöhe
		userTable.setRowHeight(20);

		// Festlegung der Spaltenbreiten
		userTable.getColumnModel().getColumn(0).setPreferredWidth(46);
		userTable.getColumnModel().getColumn(1).setPreferredWidth(150);
		userTable.getColumnModel().getColumn(2).setPreferredWidth(150);
		userTable.getColumnModel().getColumn(3).setPreferredWidth(250);

		// Festlegung des Selektionsmodus - es darf nur eine Zeile ausgewählt
		// werden
		userTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		// Hinzufügen des BookTableListener. Dadurch ist es möglich die
		// Selektion des Users abzufangen und den entsprechenden Datensatz in
		// den Textfeldern des EastPanels anzuzeigen
		userTable.getSelectionModel().addListSelectionListener(
				new UserTableListener(this));
	}

	/**
	 * Wird ein neuer Datensatz in die Datenbank gespeichert, wird die Tabelle
	 * im WestPanel neu aufgebaut, damit der neue Datensatz gleich angezeigt
	 * wird
	 */
	public void reloadWestTable() {

		this.getContentPane().remove(westPanel);
		userTable = new JTable(new UserTable(new UserDB().displayAll()));

		// WestPanel aufbauen
		this.createteWestTable();
	}

	/**
	 * Der Inhalt aller Tabellenfelder im EastPanel wird zurückgesetzt
	 */
	public void resetTableEast() {

		this.getUserIDText().setText("");
		this.getUserNameText().setText("");
		this.getUserPasswordText().setText("");
	}

	/**
	 * Definition der Getter und Setter
	 */

	public JButton getSaveButton() {
		return saveButton;
	}

	public JTextField getUserIDText() {
		return userIDText;
	}

	public void setUserIDText(JTextField userIDText) {
		this.userIDText = userIDText;
	}

	public JTextField getUserNameText() {
		return userNameText;
	}

	public void setUserNameText(JTextField userNameText) {
		this.userNameText = userNameText;
	}

	public JTextField getUserPasswordText() {
		return userPasswordText;
	}

	public void setUserPasswordText(JTextField userPasswordText) {
		this.userPasswordText = userPasswordText;
	}

	public JComboBox<String> getUserRoleCombo() {
		return userRoleCombo;
	}

	public void setUserRoleCombo(JComboBox<String> userRoleCombo) {
		this.userRoleCombo = userRoleCombo;
	}

	public void setSaveButton(JButton saveButton) {
		this.saveButton = saveButton;
	}

	public JTextField getSearchText() {
		return searchText;
	}

	public void setSearchText(JTextField searchText) {
		this.searchText = searchText;
	}

	public JButton getSearchButton() {
		return searchButton;
	}

	public void setSearchButton(JButton searchButton) {
		this.searchButton = searchButton;
	}

	public JComboBox<String> getSearchCombo() {
		return searchCombo;
	}

	public void setSearchCombo(JComboBox<String> searchCombo) {
		this.searchCombo = searchCombo;
	}

	public JButton getAllButton() {
		return allButton;
	}

	public void setAllButton(JButton allButton) {
		this.allButton = allButton;
	}

	public JButton getNewButton() {
		return clearButton;
	}

	public void setNewButton(JButton newButton) {
		this.clearButton = newButton;
	}

	public JTable getUserTable() {
		return userTable;
	}

	public void setUserTable(JTable UserTable) {
		this.userTable = UserTable;
	}

	public JButton getDeleteButton() {
		return deleteButton;
	}

	public void setDeleteButton(JButton deleteButton) {
		this.deleteButton = deleteButton;
	}

	public JButton getCreatePWButton() {
		return createPWButton;
	}

	public void setCreatePWButton(JButton createPWButton) {
		this.createPWButton = createPWButton;
	}

	public JButton getSavePWButton() {
		return savePWButton;
	}

	public void setSavePWButton(JButton savePWButton) {
		this.savePWButton = savePWButton;
	}

	public User getLoginUser() {
		return loginUser;
	}

	public void setLoginUser(User loginUser) {
		this.loginUser = loginUser;
	}

}
