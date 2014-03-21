package WordTrainer;
import java.util.Locale;

public class LibGlbSet {
	private static Locale locale = Locale.ENGLISH;
	public static final String[] langArr = {
		"de", "en", "fr"
	};

	public static Locale getLocale() {
		return locale;
	}

	public static void setLocale(Locale l) {
		locale = l;
		Locale.setDefault(l);
	}
	
	public static boolean inLangArr(String lang) {
		for(String l : langArr) {
			if(l.equalsIgnoreCase(lang)) return true;
		}
		return false;
	}
	
	public static int getLangArrKey(String lang) {
		int i = 0;
		for(String l : langArr) {
			if(l.equalsIgnoreCase(lang)) return i;
			
			i++;
		}
		return -1;
	}
	
}