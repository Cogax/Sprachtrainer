package WordTrainer;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class LibCcvWriter {
	private String filename;
	private char splitter;
	private ArrayList<String[]> data;
	
	public LibCcvWriter(String filename, char splitter) {
		this.filename = filename;
		this.splitter = splitter;
		this.data = new ArrayList<String[]>();
	}
	
	public void addLine(String[] args) {
		data.add(args);
	}
	
	public void write() {
		try {
			FileWriter writer = new FileWriter(this.filename);
			for(String[] sArr : this.data) {
				int i = 1;
				for(String s : sArr) {
					writer.append(s);
					if(i != sArr.length) {
						writer.append(this.splitter);
					}
					i++;
				}
				writer.append("\r\n");
			}
			writer.flush();
			writer.close();
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
}
