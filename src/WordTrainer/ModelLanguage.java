package WordTrainer;

import java.util.ArrayList;

/**
 * Dies ist das Model f�r die Sprachen. Dieses Model ist eine Art "Kindmodel" vom 
 * Model "Model". Jede Sprache mit welcher man lernen will bekommt ein Objekt dieser
 * Klasse. 
 * 
 * Diese Klasse dient gleichzeitig aber auch als "Elternmodel" f�r andere Models (ModelWordpairSet und
 * ModelWordpair). Alle W�rterlisten mit welchen man lernen will werden als WordpairSets gespeichert
 * und sind in einer ArrayList in dieser Klasse abgelegt.
 * 				
 * @version 1.0
 * @category Model
 * 
 * Das Copyright liegt bei den Autoren:
 * @author Andreas Gyr
 */
public class ModelLanguage {
	
	/**
	 * In dieser Variable wird der "Language Key" (z.B. de, en, etc.) der ersten Sprache gespeichert.
	 */
	private String lang1Key;
	
	/**
	 * In dieser Variable wird der "Language Key" (z.B. de, en, etc.) der zweiten Sprache gespeichert.
	 */
	private String lang2Key;
	
	/**
	 * In dieser ArrayList werden alle Sprachen gespeichert.
	 */
	private ArrayList<ModelWordpairSet> wordpairSets;
	
	
	/**
	 * Konstruktor
	 * Erzeugt ein neues ModelLanguage Objekt.
	 * 
	 * @param lang1Key der Language Key der ersten Sprache
	 * @param lang2Key der Language Key der zweiten Sprache
	 */
	public ModelLanguage(String lang1Key, String lang2Key) {
		this.lang1Key = lang1Key;
		this.lang2Key = lang2Key;
		this.wordpairSets = new ArrayList<ModelWordpairSet>();
	}
	
	
	/**
	 * F�gt eine Wortliste hinzu, sofern diese nicht schon eingetragen ist.
	 * 
	 * @param wordpairSet die Wortliste welche eingetragen werden soll
	 */
	public void addWordpairSet(ModelWordpairSet wordpairSet) {
		// TODO namen �berpr�fen mit this.getWordpairSetIndex
		this.wordpairSets.add(wordpairSet);
	}
	
	
	/**
	 * Gibt die W�rterliste zur�ck, welche den entsprechenden Index in der ArrayList hat.
	 * 
	 * @param index die Position in der ArrayList
	 * @return die W�rterliste
	 */
	public ModelWordpairSet getWordpairSet(int index) {
		return this.wordpairSets.get(index);
	}
	
	
	/**
	 * Gibt den Index der W�rterliste in der ArrayListe zur�ck, welche den namen besitzt.
	 * 
	 * @param name der Name der W�rterliste
	 * @return die Position in der ArrayList oder -1 falls kein Eintrag gefunden wurde
	 */
	public int getWordpairSetIndex(String name) {
		if(this.wordpairSets.size() > 0) {
			int i = 0;
			for(ModelWordpairSet w : this.wordpairSets) {
				if(w.getName().equals(name)) {
					return i;
				}
				i++;
			}
		}
		return -1;
	}
	
	
	/**
	 * Gibt die Anzahl der gespeicherten W�rterlisten zur�ck.
	 * 
	 * @return die Anzahl der gespeicherten W�rterlisten
	 */
	public int getWordpairSetCount() {
		return this.wordpairSets.size();
	}
	
	
	/**
	 * Gibt die W�rterliste zur�ck, welche den entsprechenden Namen hat.
	 * 
	 * @param name der Name der W�rterliste
	 * @return die W�rterliste oder null falls keine gefunden wurde
	 */
	public ModelWordpairSet getWordpairSet(String name) {
		for(ModelWordpairSet w : this.wordpairSets) {
			if(w.getName().equalsIgnoreCase(name))
				return w;
		}
		return null;
	}
	
	
	/**
	 * Gibt die ArrayList zur�ck, in welcher die W�rterlisten gespeichert sind.
	 * 
	 * @return die ArrayList mit den W�rterlisten
	 */
	public ArrayList<ModelWordpairSet> getWordpairSets() {
		return this.wordpairSets;
	}
	
	
	/**
	 * Gibt den Language Key der ersten Sprache zur�ck
	 * @return den language Key
	 */
	public String getLang1() {
		return this.lang1Key;
	}
	
	
	/**
	 * Gibt den Language Key der zweiten Sprache zur�ck
	 * @return den language Key
	 */
	public String getLang2() {
		return this.lang2Key;
	}
}
