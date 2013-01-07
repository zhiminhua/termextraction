package termex.util.properties;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


/**
 * Property that could be modified according to manual specific process.
 * 
 * @author jyfeather88
 *
 */
public class Property {
	private Properties prop = new Properties(); 
	private static Property ref = null;
	
	private static final String NLP_PATH = "termex.nlp";
	public static final String TERM_CLEAN_PATTERN = "[^a-zA-Z0-9\\-]";
    public static final String TERM_MAX_WORDS = "termex.maxwords";
    public static final String TERM_IGNORE_DIGITS = "termex.ignore_digits";
	
	private Property () {
		try {
			read();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static Property getInstance () {
		if (ref == null) {
			ref = new Property();
		}
		return ref;
	}
	
	private void read () throws IOException {
		InputStream in = null;
		in = getClass().getResourceAsStream("./termex.properties");
		prop.load(in);
		
		if (in != null) {
			in.close();
			in = null;
		}
	}
	
	private String getProperty (String key) {
		return prop.getProperty(key);
	}
	
	public String getNLPPath () {
		return getProperty(NLP_PATH);
	}

	public int getMaxMultipleWords() {
        try {
            return Integer.valueOf(getProperty(TERM_MAX_WORDS));
        } catch (NumberFormatException e) {
            return 5;
        }
	}

	public boolean isIgnoringDigits() {
        try {
            return Boolean.valueOf(getProperty(TERM_IGNORE_DIGITS));
        } catch (Exception e) {
            return true;
        }
	}
	
}
