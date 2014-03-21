package WordTrainer;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.StaxDriver;

public class LibXMLWriter {
	
	private static void write(String pathname, String content) {
		File file = new File(pathname);
		FileWriter writer;
		
		try {
			writer = new FileWriter(file);
			writer.write(content);
			writer.flush();
			writer.close();
		} catch(IOException e) {
			System.out.println(e.getStackTrace());
		}
	}
	
	/**
	 * Model Objekt in XML umwandeln und speichern in xmlfile
	 * @param filename
	 */
	public static void exportModel(String xmlFilename, Model model) {
		XStream xStream = new XStream(new StaxDriver());
		xStream.processAnnotations(Model.class);
		String xml = xStream.toXML(model);
		write(xmlFilename, xml);
		System.out.println(xml);
	}
	
	/**
	 * XML auslesen und wordList Objekt erstellen
	 * @param filename
	 */
	public static Model importModel(String xmlFilename) {
		Model model = null;
		try {
			XStream xStream = new XStream(new StaxDriver());
			xStream.processAnnotations(Model.class);
			BufferedReader in = new BufferedReader(new FileReader(xmlFilename));
			String zeile = null;
			zeile = in.readLine();
			model = (Model)xStream.fromXML(zeile);
			in.close();
		}catch (Exception e) {
			e.getStackTrace();
		}
		return model;
	}
}