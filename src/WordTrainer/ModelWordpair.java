package WordTrainer;

import java.util.Date;

/**
 * Dies ist das Model f�r die einzelnen Wortpaare. Dieses Model ist eine Art "Kindmodel" vom 
 * Model "ModelWordpairSet". Jedes Wortpaar mit welchem man lernen will bekommt ein Objekt dieser
 * Klasse. 
 * 
 * Diese Klasse speichert viele Informationen zu den einzelnen Wortpaaren. Diese Infos werden
 * f�r den Algorithmus ben�tigt, der das Lernen vereinfachen soll. 
 * 				
 * @version 1.0
 * @category Model
 * 
 * Das Copyright liegt bei den Autoren:
 * @author Andreas Gyr
 */
public class ModelWordpair {
	
	/**
	 * Den Faktor
	 * TODO rausnehmen, kann jederzeit berechnet werden
	 */
	private double factor;
	
	/**
	 * Wort 1 - L�sung zu Wort 2
	 */
	private String word1;
	
	/**
	 * Wort 2 - L�sung zu Wort 1
	 */
	private String word2;
	
	/**
	 * In dieser Variable wird gespeichert wie oft das Wortpaar beim 
	 * lernen richtig beantwortet wurde
	 */
	private int correct;
	
	/**
	 * In dieser Variable wird gespeichert wie oft das Wortpaar beim 
	 * lernen falsch beantwortet wurde
	 */
	private int wrong;
	
	/**
	 * In dieser Variable wird gespeichert wie oft das Wortpaar beim 
	 * lernen aneinander richtig beantwortet wurde
	 */
	private int correctInRow;
	
	/**
	 * In dieser Variable wird gespeichert wie oft das Wortpaar beim 
	 * lernen aneinander falsch beantwortet wurde
	 */
	private int wrongInRow;
	
	/**
	 * In dieser Variable wird gespeichert ob das Wortpaar beim letzten 
	 * mal richtig oder falsch beantwortet wurde
	 */
	private boolean lastAnswer;
	
	/**
	 * In dieser Variable wird die Zeit in milisekunden zwischen dem 1.1.1970
	 * und dem letzten mal als das Wortpaar gelernt wurde gespeichert.
	 */
	private long lastAnswerTimestamp; // Milisekunden seit 1. Jan. 1970 bis letztes Mal die Frage beantwortet wurde

	
	/**
	 * Konstruktor
	 * Erzeugt ein ModelWordpair Objekt.
	 * @param word1 das Wort 1
	 * @param word2 das Wort 2
	 */
	public ModelWordpair(String word1, String word2) {
		this.word1 = word1;
		this.word2 = word2;
		this.lastAnswer = false;
		this.factor = 1;
	}

	
	/**
	 * Pr�ft ob das eingegeben Wort mit dem Wortpaar �bereinstimmt. Case
	 * insensitiv!
	 * 
	 * @param word die eingegebene L�sung
	 * @return boolean
	 */
	public boolean checkIfMatch(String word) {
		if (word2.equalsIgnoreCase(word))
			return true;
		else
			return false;
	}

	
	/**
	 * Wird ausgefuehrt, wenn eine Abfrage korrekt beantwortet wurde
	 */
	public void correct() {
		this.correct++;
		this.wrongInRow = 0;

		// Pruefen ob letztes mal richtig beantwortet wurde
		if (this.lastAnswer) {
			this.correctInRow++;
		}
		this.lastAnswer = true;
		this.lastAnswerTimestamp = new Date().getTime();
	}

	
	/**
	 * Wird ausgefuehrt, wenn eine Abfrage falsch beantwortet wurde
	 */
	public void wrong() {
		this.wrong++;
		this.correctInRow = 0;

		// Pruefen ob letztes mal falsch beantwortet wurde
		if (!this.lastAnswer) {
			this.wrongInRow++;
		}
		this.lastAnswer = false;
		this.lastAnswerTimestamp = new Date().getTime();
	}
	
	
	/**
	 * Setzt das Wort 1 neu
	 * 
	 * @param w das neue Wort
	 */
	public void setWord1(String w) {
		this.word1 = w;
	}
	
	
	/**
	 * Setzt das Wort 2 neu
	 * 
	 * @param w das neue Wort
	 */
	public void setWord2(String w) {
		this.word2 = w;
	}

	
	/**
	 * Gibt das Wort 1 zur�ck
	 * 
	 * @return das Wort 1
	 */
	public String getWord1() {
		return this.word1;
	}

	
	/**
	 * Gibt das Wort 2 zur�ck
	 * 
	 * @return das Wort 2
	 */
	public String getWord2() {
		return this.word2;
	}

	
	/**
	 * Gibt zur�ck wie oft das Wortpaar beim lernen richtig beantwortet wurde.
	 * 
	 * @return die Anzahl
	 */
	public int getCorrect() {
		return this.correct;
	}

	
	/**
	 * Gibt zur�ck wie oft das Wortpaar beim lernen falsch beantwortet wurde.
	 * 
	 * @return die Anzahl
	 */
	public int getWrong() {
		return this.wrong;
	}

	
	/**
	 * Gibt zur�ck wie oft das Wortpaar beim lernen aneinander falsch beantwortet wurde.
	 * 
	 * @return die Anzahl
	 */
	public int getWrongInRow() {
		return this.wrongInRow;
	}

	
	/**
	 * Gibt zur�ck wie oft das Wortpaar beim lernen aneinander richtig beantwortet wurde.
	 * 
	 * @return die Anzahl
	 */
	public int getCorrectInRow() {
		return this.correctInRow;
	}
	
	
	/**
	 * Berechnet den Faktor.
	 * Hier steckt der Algorithmus drin
	 * 
	 * @return den Faktor
	 */
	public double getFactor() {
		// TODO hier faktorberechnung einbauen
		long todayTimestamp = new Date().getTime();
		long diff = todayTimestamp - this.lastAnswerTimestamp;
		
		// je kleiner diff ist desto weniger kann lastAnswer=true gewertet werden
		// je gr�sser differenz zwischen correct und wrong (positiv) desto gr�sser der faktor
		// je gr�sser correct In Row desto gr�sser der Faktor
		
		
		
		
		
		return this.factor;
	}
}