package termex.util.preprocessor;

import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

import termex.util.properties.Property;
import dragon.nlp.tool.lemmatiser.EngLemmatiser;

/**
 * Retrieve the dictionary form of a word.
 * 
 * @author jyfeather88
 *
 */
public class Lemmatizer extends Normalizer{
	private EngLemmatiser lemmatiser;
	private Map<String, Integer> tagLookUp = new HashMap<String, Integer>();
	
	public Lemmatizer () {
		init();
	}
	
	private void init () {
		lemmatiser = new EngLemmatiser(Property.getInstance().getNLPPath() + "lemmatiser", false, true);
		tagLookUp.put("NN", 1);
		tagLookUp.put("NNS", 1);
		tagLookUp.put("NNP", 1);
		tagLookUp.put("NNPS", 1);
		tagLookUp.put("VB", 2);
		tagLookUp.put("VBG", 2);
		tagLookUp.put("VBD", 2);
		tagLookUp.put("VBN", 2);
		tagLookUp.put("VBP", 2);
		tagLookUp.put("VBZ", 2);
		tagLookUp.put("JJ", 3);
		tagLookUp.put("JJR", 3);
		tagLookUp.put("JJS", 3);
		tagLookUp.put("RB", 4);
		tagLookUp.put("RBR", 4);
		tagLookUp.put("RBS", 4);
	}
	
	public String getLemma (String value, String pos) {
		int POS = tagLookUp.get(pos);
		if (POS == 0) {
			return lemmatiser.lemmatize(value);
		} else {
			return lemmatiser.lemmatize(value, POS);
		}
	}

	/**
	 * Lemmatise a phrase or word. If a phrase, only lemmatise the most RHS word.
	 * 
	 * @param value
	 * @return
	 */
	@Override
	public String normalize(String value) {
		// single word / XYZs / XYZ's
		if (value.indexOf(" ") == -1 || value.endsWith("s") || value.endsWith("'s")) {
			return lemmatiser.lemmatize(value, 1).trim();
		}
		
		// a phrase, lemmatise the right part only
		String left_part = value.substring(0, value.lastIndexOf(" "));
		String right_part = lemmatiser.lemmatize(value.substring(value.lastIndexOf(" ") + 1), 1);
		return left_part + " " + right_part.trim();
	}
	
	/**
	 * Lemmatise every word in the input string
	 * 
	 * @param value
	 * @return
	 */
	@Override
	public String normalizeContent(String value) {
		StringBuilder sb = new StringBuilder();
		StringTokenizer tokenizer = new StringTokenizer(value);
		while (tokenizer.hasMoreTokens()) {
			String tok = tokenizer.nextToken();
			sb.append(normalize(tok)).append(" ");
		}
		return sb.toString();
	}
}
