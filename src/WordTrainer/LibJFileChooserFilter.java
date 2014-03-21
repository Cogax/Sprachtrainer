package WordTrainer;
import java.io.File;

import javax.swing.filechooser.FileFilter;

/**
 * Dateifilter für den JFileChooser
 *
 */
public class LibJFileChooserFilter extends FileFilter {
	private String ending;
	
	public LibJFileChooserFilter(String ending) {
		this.ending = ending;
	}
	
	@Override
	public boolean accept(File f) {
		// Falls leeres Objekt oder Verzeichnis gewaehlt wird false zurückgegeben
		if(f == null) return false;
		
		// Falls die Endung mit der vorgegebenen endung uebereinstimmt wird true zurueckgegeben
		if(f.isDirectory()) return true;
		return f.getName().toLowerCase().endsWith(this.ending);
	}

	@Override
	public String getDescription() {
		return this.ending;
	}

}