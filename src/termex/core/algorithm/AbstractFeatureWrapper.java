package termex.core.algorithm;

import java.util.Set;

/**
 * A feature wrapper wraps one or multiple instances of Feature, and provides manipulations for term extraction algorithms.
 * 
 * @author jyfeather88
 *
 */

public abstract class AbstractFeatureWrapper {

	/**
	 * @return set of candidate term strings
	 */
	public abstract Set<String> getTerms ();
	
	public String toString () {
		return this.getClass().toString();
	}
}
