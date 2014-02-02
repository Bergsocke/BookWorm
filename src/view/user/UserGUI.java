package view.user;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
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

import model.user.UserDB;
import view.book.BookGUI;

/**
 * Die Klasse "UserGUI" ist für den Aufbau der grafischen Oberfläche zuständig.
 * Hier werden die einzelnen Komponenten des Fensters (Textfelder,
 * Bezeichungsfelder, Buttons und die Usertabelle) festgelegt.
 * 
 * @author Bergsocke
 * 
 */

public class UserGUI extends JFrame {

	private static final long serialVersionUID = -2334577762951716368L;

	// Festlegung der Schriftart für die Bezeichnungsfelder und Buttons
	static String labelFont = "Verdana";
	static int labelStyle = Font.BOLD;
	static int labelSize = 12;

	// Festlegung der Schriftart für die Textfelder und der Tabelle
	static String textFont = "Arial";
	static int textStyle = Font.PLAIN;
	static int textSize = 12;

	// Deklaration der erforderlichen Komponenten
	private JPanel northPanel;
	private JPanel eastPanel;
	private JPanel westPanel;

	private JLabel searchLabel;
	private JTextField searchText;
	private JButton searchButton;

	private JButton allButton;

	private JLabel userIDLabel;
	private JTextField userIDText;

	private JLabel userNameLabel;
	private JTextField userNameText;

	private JLabel userPasswordLabel;
	private JTextField userPasswordText;

	private JButton clearButton;
	private JButton saveButton;
	private JButton deleteButton;
	private JButton closeButton;

	private JTable userTable;

	private JScrollPane tableScroll;

	private JMenuBar userMenuBar;
	private JMenu clearMenu;
	private JMenuItem clearMenuItem;
	private JMenu saveMenu;
	private JMenuItem saveMenuItem;
	private JMenu deleteMenu;
	private JMenuItem deleteMenuItem;
	private JMenu changeMenu;
	private JMenuItem changeMenuItem;
	private JMenu helpMenu;
	private JMenuItem helpMenuItem;

	public static void letStartedUserGUI() {

		// Aufruf des Konstruktors der Klasse UserGUI und Zuweisung der
		// Überschrift
		UserGUI gui = new UserGUI("BOOKWORM - USERVERWALTUNG");

		// Fenstergröße wird automatisch an den Inhalt angepasst
		gui.pack();

		// Fenstergröße soll nicht verändert werden können
		gui.setResizable(false);

		// Positionierung am Desktop
		gui.setLocation(400, 200);


		// Window-Close-Funktion
		gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Fenster wird auf sichtbar gesetzt
		gui.setVisible(true);
	}

	/**
	 * Konstruktur (Fensterbeschriftung und Initialisierung der Komponenten)
	 * 
	 * @param frameTitle
	 */
	public UserGUI(String frameTitle) {

		super(frameTitle);

		// Initialisierung der Fenster-Komponenten
		this.initComponents();
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
	public void initMenuBar() {

		userMenuBar = new JMenuBar();

		clearMenu = new JMenu("Neu");
		clearMenuItem = new JMenuItem("Neuen Datensatz anlegen");
		clearMenu.add(clearMenuItem);
		clearMenuItem.addActionListener(new UserGUIActionListener(this));

		saveMenu = new JMenu("Speichern");
		saveMenuItem = new JMenuItem("Datensatz speichern");
		saveMenu.add(saveMenuItem);
		saveMenuItem.addActionListener(new UserGUIActionListener(this));

		deleteMenu = new JMenu("Löschen");
		deleteMenuItem = new JMenuItem("Ausgewählten Datensatz löschen");
		deleteMenu.add(deleteMenuItem);
		deleteMenuItem.addActionListener(new UserGUIActionListener(this));
		
		changeMenu = new JMenu("Wechseln");
		changeMenuItem = new JMenuItem("Zur Bücherverwaltung wechseln");
		changeMenu.add(changeMenuItem);
		changeMenuItem.addActionListener(new UserGUIActionListener(this));

		helpMenu = new JMenu("Hilfe");
		helpMenuItem = new JMenuItem("Über das Programm");
		helpMenu.add(helpMenuItem);
		helpMenuItem.addActionListener(new UserGUIActionListener(this));

		// Hinzufügen der einzelnen Komponenten zur Menübar
		userMenuBar.add(clearMenu);
		userMenuBar.add(saveMenu);
		userMenuBar.add(deleteMenu);
		userMenuBar.add(changeMenu);
		userMenuBar.add(helpMenu);

		// Hinzufügen der Menübar zum Frame
		this.setJMenuBar(userMenuBar);
	}

	/**
	 * Zuweisung von Inhalt und Form der einzelnen Komponenten, die im
	 * North-Panel ausgegeben werden. Mit der Möglichkeit zur Suche nach einem
	 * User bzw. zur Anzeige aller Datensätze
	 */
	private void initComponentsNorth() {

		northPanel = new JPanel();

		searchLabel = new JLabel("Nach User suchen: ");
		searchLabel.setFont(new Font(textFont, labelStyle, 14));

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
		searchButton.addActionListener(new UserGUIActionListener(this));

		// Icon für den Buttton "alle anzeigen"
		final Icon showAllIcon = new ImageIcon(
				BookGUI.class.getResource("/view/images/showAllIcon.png"));
		allButton = new JButton(" alle anzeigen  ", showAllIcon);
		allButton.setFont(new Font(labelFont, labelStyle, labelSize));
		allButton.setBackground(Color.lightGray);
		allButton.setBorder(BorderFactory.createRaisedBevelBorder());
		allButton.addActionListener(new UserGUIActionListener(this));

		// Hinzufügen der einzelnen Komponenten zum NorthPanel
		northPanel.add(searchLabel);
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

		// Layout für 7 Zeilen und 2 Spalten
		eastPanel.setLayout(new GridLayout(7, 2));

		// Unsichtbarer Rahmen wird gesetzt, um Abstand zum Frame zu bekommen
		eastPanel.setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 15));

		userIDLabel = new JLabel("User-ID: ");
		userIDLabel.setFont(new Font(labelFont, labelStyle, labelSize));
		// Festlegung der Länge des Textfeldes, die anderen Textfelder werden
		// dann ebenfalls an diese Größe angepasst
		userIDText = new JTextField(25);
		userIDText.setFont(new Font(textFont, textStyle, textSize));

		// Nachdem die Buch-ID automatisch von der Datenbank vergeben wird, soll
		// das Feld "Buch-ID" nicht bearbeitet werden können. Aus diesem Grund
		// wird es auf nicht editierbar gesetzt. Aus diesem Feld wird beim
		// Updaten, Speichern oder Löschen eines Datensatzes die Buch-ID
		// ausgelesen
		userIDText.setEditable(false);

		userNameLabel = new JLabel("Username: ");
		userNameLabel.setFont(new Font(labelFont, labelStyle, labelSize));
		userNameText = new JTextField();
		userNameText.setFont(new Font(textFont, textStyle, textSize));

		userPasswordLabel = new JLabel("Userpassword: ");
		userPasswordLabel.setFont(new Font(labelFont, labelStyle, labelSize));
		userPasswordText = new JTextField();
		userPasswordText.setFont(new Font(textFont, textStyle, textSize));

		// Icon für den Buttton "neu"
		final Icon newIcon = new ImageIcon(
				BookGUI.class.getResource("/view/images/newIcon.png"));
		clearButton = new JButton("     neu", newIcon);
		clearButton.setFont(new Font(labelFont, labelStyle, labelSize));
		clearButton.setBackground(Color.lightGray);
		clearButton.setBorder(BorderFactory.createRaisedBevelBorder());
		// Wenn auf den Button "neu" geklickt wird, soll der Inhalt der
		// Textfelder im EastPanel zurückgesetzt werden. Ein neuer Datensatz
		// wird erst beim Klick auf den Button "speichern" in die Datenbank
		// eingefügt. Der Button "löschen" soll deaktiviert werden.
		clearButton.addActionListener(new UserGUIActionListener(this));

		// Icon für den Buttton "speichern"
		final Icon saveIcon = new ImageIcon(
				BookGUI.class.getResource("/view/images/saveIcon.png"));
		saveButton = new JButton("     speichern", saveIcon);
		saveButton.setFont(new Font(labelFont, labelStyle, labelSize));
		saveButton.setBackground(Color.lightGray);
		saveButton.setBorder(BorderFactory.createRaisedBevelBorder());
		// wenn auf den Button "speichern" geklickt wird, soll der Datensatz in
		// die Datenbank gespeichert werden
		saveButton.addActionListener(new UserGUIActionListener(this));

		// Icon für den Buttton "löschen"
		final Icon deleteIcon = new ImageIcon(
				BookGUI.class.getResource("/view/images/deleteIcon.png"));
		deleteButton = new JButton("     löschen", deleteIcon);
		deleteButton.setFont(new Font(labelFont, labelStyle, labelSize));
		deleteButton.setBackground(Color.lightGray);
		deleteButton.setBorder(BorderFactory.createRaisedBevelBorder());
		// wenn auf den Button "löschen" geklickt wird, soll der Datensatz
		// aus der Datenbank gelöscht werden
		deleteButton.addActionListener(new UserGUIActionListener(this));
		// Der Button "löschen" ist zu Beginn/beim Erscheinen des Fensters
		// noch ncht auswählbar; er wird erst sichtbar, wenn ein Datensatz
		// ausgewählt wurde
		deleteButton.setEnabled(false);

		// Icon für den Buttton "Programm beenden"
		final Icon closeIcon = new ImageIcon(
				BookGUI.class.getResource("/view/images/closeIcon.png"));
		closeButton = new JButton(" Programm beenden", closeIcon);
		closeButton.setFont(new Font(labelFont, labelStyle, labelSize));
		closeButton.setBackground(Color.lightGray);
		closeButton.setBorder(BorderFactory.createRaisedBevelBorder());
		// wenn auf den Button "Programm beenden" geklickt wird, soll das
		// Fenster geschlossen werden
		closeButton.addActionListener(new UserGUIActionListener(this));

		// Dummy-Labels für Abstand zwischen der Tabelle und den Buttons
		JLabel dummyLabel = new JLabel();
		JLabel dummyLabel2 = new JLabel();
		// Dummy-Labels für Abstand zwischen den Buttons und dem unteren
		// Fensterrand
		JLabel dummyLabel3 = new JLabel();
		JLabel dummyLabel4 = new JLabel();

		// Hinzufügen der einzelnen Komponenten zum EastPanel
		eastPanel.add(userIDLabel);
		eastPanel.add(userIDText);

		eastPanel.add(userNameLabel);
		eastPanel.add(userNameText);

		eastPanel.add(userPasswordLabel);
		eastPanel.add(userPasswordText);

		// Dummy-Labels für Abstand zwischen der Tabelle und den Buttons
		eastPanel.add(dummyLabel);
		eastPanel.add(dummyLabel2);

		eastPanel.add(clearButton);
		eastPanel.add(saveButton);
		eastPanel.add(deleteButton);
		eastPanel.add(closeButton);

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
		// Initailisierung der Büchertabelle. Nachdem auch von anderen
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

		// Bei der erstmaligen Initialisierung des Fensters gibt es noch keine
		// Darstellungsprobleme mit der Tabelle. Es werden alle Datensätze
		// angezeigt
		if (getSearchText().getText().matches("Bitte Suchbegriff eingeben")) {
			// alle Datensätze werden angezeigt
			userTable = new JTable(new UserTable(UserDB.displayAll()));

		} else {
			// Damit bei Eingabe eines Suchbegriffes die Tabelle neu aufgebaut
			// wird, wird sie zunächst einmal aus dem WestPanel entfernt, danach
			// wird nach dem entsprechenden Suchbegriff gesucht (im
			// Datenbankfeld "username") und am Ende werden die Suchergebnisse
			// in einer neu erstellten Tabelle angezeigt

			this.getContentPane().remove(westPanel);
			userTable = new JTable(new UserTable(
					UserDB.findByUserName(getSearchText().getText())));
		}

		// WestPanel wird aufgebaut
		this.createteWestTable();

		return userTable;
	}

	/**
	 * WestPanel wird aufgebaut. Weiters wird das Layout der Tabelle im
	 * WestPanel festgelegt.
	 */
	public void createteWestTable() {

		westPanel = new JPanel();

		// Falls die Spalten zu breit für den verfügbaren Platz sind,
		// soll eine Scrollbar zur Verfügung stehen
		tableScroll = new JScrollPane(userTable);
		// Festlegung der Tabellengröße
		tableScroll.setPreferredSize(new Dimension(450, 300));
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

		// Festlegung der Schrifteigenschaften
		userTable.setFont(new Font(textFont, textStyle, textSize));

		// Festlegung der Zeilenhöhe
		userTable.setRowHeight(20);

		// Festlegung der Spaltenbreiten
		userTable.getColumnModel().getColumn(0).setPreferredWidth(46);
		userTable.getColumnModel().getColumn(1).setPreferredWidth(150);
		userTable.getColumnModel().getColumn(2).setPreferredWidth(250);

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
		userTable = new JTable(new UserTable(UserDB.displayAll()));

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

}
