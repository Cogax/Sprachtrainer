package WordTrainer;

import java.util.ArrayList;

/**
 * Das Model dient als eine Art Elternmodel aller anderen Models (ModelLanguage,
 * ModelWordpair, ModelWordpairSet) und hat kein übergeordnetes Model. Dies vereinfacht 
 * die handhabung der gesammten Software weil man immer nur ein Modelobjekt übergeben 
 * muss und darin eigendlich alles was irgendwie mit den Models zutun hat gespeichert ist.
 * 
 * Die Struktur des gesammten Model sieht schlussendlich etwa so aus:
 * - Model
 * 		- ModelLanguage 						z.B. Deutsch-Englisch
 * 			- ModelWordpairSet					z.B. Unit 1
 * 				- ModelWordpair					z.B. Haus - House
 * 				- ModelWordpair					z.B. Auto - Car
 * 				- ModelWordpair					z.B. Zug - Train
 * 			- ModelWordpairSet					z.B. Unit 2
 * 				- ModelWordpair					z.B. Ich bin - I am
 * 				- ModelWordpair					z.B. Du bist - You are
 * 				- ModelWordpair					z.B. Er ist - He is
 * 		- ModelLanguage 						z.B. Deutsch-Französisch
 * 			- ModelWordpairSet					z.B. Unite 1
 * 				- ModelWordpair					z.B. Ja - Qui
 * 				- ModelWordpair					z.B. Nein - Non
 * 				- ModelWordpair					z.B. Guten Tag - Bonjour
 * 			
 * 		
 * @version 1.0
 * @category Model
 * 
 * @author Andreas Gyr
 * @author Daniel Ammann
 * @author Benjamin Lutz
 * @author Nico Florin
 * @author Habib Abdelbaki
 */
public class Model {
	
	/**
	 * In dieser ArrayList werden alle ModelLanguage Objekte gespeichert. Es sind also alle Sprachen,
	 * von welchen man Wörterlisten hat in dieser Liste gespeichert. Die ModelLanguage haben dann
	 * selbst wieder eine ArrayListe mit den Wörterlisten.
	 */
	private ArrayList<ModelLanguage> languages;
	
	
	
	/**
	 * Konstruktor
	 * Erzeugt ein neues Model Objekt
	 */
	public Model() {
		this.languages = new ArrayList<ModelLanguage>();
	}
	
	
	/**
	 * Fügt eine Language hinzu, sofern diese nicht schon eingetragen wurde.
	 * 
	 * @param lang die Sprache die hinzugefügt werden soll
	 */
	public void addLanguage(ModelLanguage lang) {
		if(this.getLanguageIndex(lang.getLang1(), lang.getLang2()) < 0) {
			this.languages.add(lang);
		}
	}
	
	
	/**
	 * Gibt die Position zurück, an welcher ein ModelLanguage Objekt mit den mitgegebenen
	 * Sprachen existiert.
	 * 
	 * @param lang1 die erste Sprache als kürzel (de,en etc.)
	 * @param lang2 die zweite Sprache als kürzel (de,en etc.)
	 * @return die Position in der ArrayList oder -1 falls kein Eintrag gefunden wurde
	 */
	public int getLanguageIndex(String lang1, String lang2) {
		if(this.languages.size() > 0) {
			int i = 0;
			for(ModelLanguage l : this.languages) {
				if(l.getLang1().equals(lang1) && l.getLang2().equals(lang2)) {
					return i;
				}
				i++;
			}
		}
		return -1;
	}
	
	
	/**
	 * Gibt die Anzahl der Language einträge in der ArrayList zurück.
	 * 
	 * @return die anzahl Einträge
	 */
	public int getLanguageCount() {
		return this.languages.size();
	}
	
	
	/**
	 * Entfernt eine Sprache von der ArrayList.
	 * 
	 * @param index die Position in der ArrayList
	 */
	public void removeLanguage(int index) {
		this.languages.remove(index);
	}
	
	
	/**
	 * Entfernt eine Sprache von der ArrayList.
	 * 
	 * @param lang das zu entfernende Objekt
	 */
	public void removeLanguage(ModelLanguage lang) {
		this.languages.remove(lang);
	}
	
	
	/**
	 * Gibt die ArrayList mit den gespeicherten Sprachen zurück.
	 * 
	 * @return die ArrayList mit den Sprachen
	 */
	public ArrayList<ModelLanguage> getLanguages() {
		return this.languages;
	}
	
	
	/**
	 * Gibt eine Sprache aus der ArrayListe zurück.
	 * 
	 * @param index die Position in der ArrayList
	 * @return die Sprache als Objekt
	 */
	public ModelLanguage getLanguage(int index) {
		return this.languages.get(index);
	}
}
