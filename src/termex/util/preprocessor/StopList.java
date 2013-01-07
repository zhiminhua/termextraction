package termex.util.preprocessor;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;

import termex.util.properties.Property;

/**
 * Class about the stop words list, which should not occur in a valid term.
 * 
 * @author jyfeather88
 *
 */
@SuppressWarnings("serial")
public class StopList extends HashSet<String>{
	
	public StopList () throws IOException {
		super();
		loadStopList(new File(Property.getInstance().getNLPPath() + "stoplist.txt"));
	}
	
	private void loadStopList (final File stopListFile) throws IOException {
		final BufferedReader reader = new BufferedReader(new FileReader(stopListFile));
		String line;
		while ((line = reader.readLine()) != null) {
			line = line.trim();
	         if (line.equals("") || line.startsWith("//")) continue;
	         this.add(line.toLowerCase());
		}
	}
	
	public boolean isStopWord (String word) {
		return contains(word.toLowerCase());
	}
}
