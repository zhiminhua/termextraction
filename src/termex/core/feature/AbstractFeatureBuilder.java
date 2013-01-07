package termex.core.feature;

import termex.core.feature.index.GlobalIndex;
import termex.util.counters.TermFreqCounter;
import termex.util.counters.WordCounter;
import termex.util.preprocessor.Normalizer;

/**
 * Feature builder that builds a feature from a GlobalIndex
 * 
 * @author jyfeather88
 *
 */

public abstract class AbstractFeatureBuilder {
	protected Normalizer normalizer;
	protected WordCounter wordCounter;
	protected TermFreqCounter tfCounter;
	
	public AbstractFeatureBuilder(TermFreqCounter tfCounter, WordCounter wordCounter, Normalizer normalizer) {
		this.tfCounter = tfCounter;
		this.wordCounter = wordCounter;
		this.normalizer = normalizer;
	}
	
	/**
	 * Build an instance of feature
	 * 
	 * @param index
	 * @return
	 * @throws Exception
	 */
	public abstract AbstractFeature build (GlobalIndex index) throws Exception;
}
