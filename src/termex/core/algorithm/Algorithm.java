package termex.core.algorithm;

import termex.model.term.Term;

/**
 * Interface of term extraction algorithms.
 * This class represents the core scoring and ranking algorithm which given a candidate term, produces a confident score indicating the relationship to the corpus from which it has been extracted.
 * Note that an instance of algorithm does not do pre- or post-processing to filter candidate terms.
 *  
 * @author jyfeather88
 *
 */

public interface Algorithm {
	
	/**
	 * Execute the algorithm by analyzing the features stored in the <code>AbstractFeatureWrapper</code> and return terms sorted by their relevance 
	 * 
	 * @return
	 */
	Term[] execute (AbstractFeatureWrapper store);
}
