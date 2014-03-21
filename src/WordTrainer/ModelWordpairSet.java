package WordTrainer;

import java.util.ArrayList;

/**
 * Dies ist das Model f�r die W�rterlisten. Dieses Model ist eine Art "Kindmodel" vom 
 * Model "ModelLanguage". Jede W�rterliste mit welcher man lernen will bekommt ein Objekt dieser
 * Klasse. 
 * 
 * Diese Klasse dient gleichzeitig aber auch als "Elternmodel" f�r ein Model (ModelWordpair). 
 * Alle W�rter mit welchen man lernen will werden als Wordpair gespeichert
 * und sind in einer ArrayList in dieser Klasse abgelegt.
 * 				
 * @version 1.0
 * @category Model
 * 
 * Das Copyright liegt bei den Autoren:
 * @author Andreas Gyr
 */
public class ModelWordpairSet {
	
	/**
	 * Diese Variable speichern den Namen der W�rterliste.
	 */
	private String name;
	
	/**
	 * In dieser ArrayList werden die einzelnen W�rter gespeichert
	 */
	private ArrayList<ModelWordpair> wordpairs;
	
	
	/**
	 * Konstruktor
	 * Erzeugt ein neues ModelWordpairSet Objekt
	 * 
	 * @param name der Name der W�rterliste
	 */
	public ModelWordpairSet(String name) {
		this.name = name;
		this.wordpairs = new ArrayList<ModelWordpair>();
	}
	
	
	/**
	 * Entfernt alle W�rter der W�rterliste
	 */
	public void removeAllWordpairs() {
		this.wordpairs.clear();
	}
	
	
	/**
	 * F�gt ein neues Wortpaar der W�rterliste hinzu, sofern es noch nicht vorhanden ist.
	 * 
	 * @param wordpair das Wortpaar welches eingetragen werden soll
	 */
	public void addWordpair(ModelWordpair wordpair) {
		// TODO check if alredy exists
		this.wordpairs.add(wordpair);
	}
	
	
	/**
	 * Pr�ft ob ein Wortpaar bereits in der Liste eingetragen ist.
	 * 
	 * @param wp das Wortpaar
	 * @return true oder false
	 */
	public boolean wordpairExists(ModelWordpair wp) {
		if(this.wordpairs.size() > 0) {
			for(ModelWordpair w : this.wordpairs) {
				if(w.getWord1().equals(wp.getWord1()) && w.getWord2().equals(wp.getWord2())) {
					return true;
				}
			}
		}
		return false;
	}
	
	
	/**
	 * Gibt die Aanzahl der gespeicherten Wortpaare zur�ck.
	 * 
	 * @return die Anzahl der gespeicherten Wortpaare
	 */
	public int getWordpairCount() {
		return this.wordpairs.size();
	}
	
	
	/**
	 * Gibt die ArrayList mit den gespeicherten Wortpaaren zur�ck.
	 * 
	 * @return die ArrayList mit den Wortpaaren
	 */
	public ArrayList<ModelWordpair> getWordpairs() {
		return this.wordpairs;
	}
	
	
	/**
	 * L�scht ein Wortpaar aus der ArrayList.
	 * 
	 * @param wordpair das Wortpaar welches aus der Liste entfernt werden soll
	 */
	public void delete(ModelWordpair wordpair) {
		this.wordpairs.remove(wordpair);
	}

	
	/**
	 * Gibt den Namen der Wortliste zur�ck.
	 * 
	 * @return den Namen der Wortliste
	 */
	public String getName() {
		return name;
	}
}