package termex.util.preprocessor;

/**
 * Normalizer returns text units to its canonical forms
 * 
 * @author jyfeather88
 *
 */

public abstract class Normalizer {
	
	/**
	 * Normalise only the RHS head word of the input text unit
	 * 
	 * @param unit
	 * @return
	 */
	public abstract String normalize(String unit);
	
	/**
	 * Normalise every token found in the input content, assuming tokens are delimited by a whitespace character.
	 * 
	 * @param content
	 * @return
	 */
	public abstract String normalizeContent(String content);
}
