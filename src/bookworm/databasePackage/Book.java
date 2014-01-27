package bookworm.databasePackage;

/**
 * Die Klasse "Book" ist f�r die Erfassung und R�ckgabe des Inhalts der
 * einzelnen B�cher-Datens�tze zust�ndig
 * 
 * @author Bergsocke
 * 
 */

public class Book {

	private String id;
	private String isbn;
	private String title;
	private String author;
	private String publicationDate;
	private String format;
	private String shortDescription;
	private String comment;
	private String read;

	/**
	 * Konstruktor f�r den Zugriff auf alle Tabellenfelder
	 * 
	 * @param id
	 * @param isbn
	 * @param title
	 * @param author
	 * @param publicationDate
	 * @param format
	 * @param shortDescription
	 * @param comment
	 * @param read
	 */
	public Book(String id, String isbn, String title, String author,
			String publicationDate, String format, String shortDescription,
			String comment, String read) {

		this.id = id;
		this.isbn = isbn;
		this.title = title;
		this.author = author;
		this.publicationDate = publicationDate;
		this.format = format;
		this.shortDescription = shortDescription;
		this.comment = comment;
		this.read = read;

	}

	/**
	 * Konstruktor f�r die Neuerfassung von B�chern
	 * 
	 * @param isbn
	 * @param title
	 * @param author
	 * @param publicationDate
	 * @param format
	 * @param shortDescription
	 * @param comment
	 * @param read
	 */
	public Book(String isbn, String title, String author,
			String publicationDate, String format, String shortDescription,
			String comment, String read) {

		this.isbn = isbn;
		this.title = title;
		this.author = author;
		this.publicationDate = publicationDate;
		this.format = format;
		this.shortDescription = shortDescription;
		this.comment = comment;
		this.read = read;

	}

	/**
	 * Definition der Getter und Setter
	 */
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getPublicationDate() {
		return publicationDate;
	}

	public void setPublicationDate(String publicationDate) {
		this.publicationDate = publicationDate;
	}

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	public String getShortDescription() {
		return shortDescription;
	}

	public void setShortDescription(String shortDescription) {
		this.shortDescription = shortDescription;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getRead() {
		return read;
	}

	public void setRead(String read) {
		this.read = read;
	}

}