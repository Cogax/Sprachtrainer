package WordTrainer;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * CSV Reader
 *
 */
public class LibCsvReader {
	private String splitter;
	private String absolutePath;
	private int argumentsPerLine;
	private ArrayList<String[]> list;
	
	public LibCsvReader(String absolutePath, int argumentsPerLine, String splitter) {
		this.absolutePath = absolutePath;
		this.argumentsPerLine = argumentsPerLine;
		this.splitter = splitter;
		this.list = new ArrayList<String[]>();
		
		// CSV File lesen
		readData();
	}

	/**
	 * Liest die CSV Datei und speichert die einzelnen Argumente-Arrays in einer ArrayListe ab.
	 */
	private void readData() {
		BufferedReader data =null;
		try {
				FileReader file = new FileReader(this.absolutePath);
				data = new BufferedReader(file);
				String zeile;
				while ((zeile = data.readLine()) != null) {
					String[] split = zeile.split(this.splitter);
					
					// Pruefen ob die anzahl Argumente in einer Zeile stimmen
					if(split.length != this.argumentsPerLine) {
						throw new IncorrectCsvArgumentsException();
					}
					
					// Argumente in ArrayList speichern
					this.list.add(split);
					
				}
		} catch (FileNotFoundException e) {
			System.err.println("File not found");
		} catch (IOException e) {
			System.err.println("I/O-Exception");
		} catch (IncorrectCsvArgumentsException e) {
			System.err.println("Incorrect Csv Arguments");
		} finally {
				try {
					data.close();
				} catch (IOException e) {
					System.err.println("I/O-Exception");
				}
		}
	}
	
	class IncorrectCsvArgumentsException extends Exception {
		private static final long serialVersionUID = 1L;

		public IncorrectCsvArgumentsException() {
			super("To Much or less Arguments in CSV File!");
		}
	}

	
	public ArrayList<String[]> getData() {
		return this.list;
	}

}
