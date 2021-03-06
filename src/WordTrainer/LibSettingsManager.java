package WordTrainer;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class LibSettingsManager {
	private String configFilePath;
    private Properties properties = new Properties();
    private boolean isXML;
    
    public LibSettingsManager(String configFilePath, boolean isXML) throws IOException {
        this.configFilePath = configFilePath;
        this.isXML = isXML;
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(configFilePath);
            if (isXML) {
                properties.loadFromXML(fis);
            } else {
                properties.load(fis);
            }
        } catch (FileNotFoundException ex) {
            // creates the configuration file and set default properties
            setDefaults();
            save();
        } finally {
            if (fis != null) {
                fis.close();
            }
        }
    }
    
    private void setDefaults() {
    	//Evtl. Default speichern
    }
    
    public void save() throws IOException {
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(configFilePath);
            if (isXML) {
                properties.storeToXML(fos, "My Application Settings");
            } else {
                properties.store(fos, "My Application Settings");
            }
        } finally {
            if (fos != null) {
                fos.close();
            }
        }
    }
    
    public String getProperty(String key) {
        return properties.getProperty(key);
    }
    
    public String getProperty(String key, String defaultValue) {
        return properties.getProperty(key, defaultValue);
    }
    
    public void setProperty(String key, String value) {
        properties.setProperty(key, value);
    }
}
