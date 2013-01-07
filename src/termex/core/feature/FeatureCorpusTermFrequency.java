package termex.core.feature;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import termex.core.feature.index.GlobalIndex;

/**
 * A feature container that stores information of term distributions in the corpus.
 * Include:
 * -- number of occurrences of terms found in the corpus.
 * -- number of occurrences of each term found in the corpus.
 * 
 * @author jyfeather88
 *
 */

public class FeatureCorpusTermFrequency extends AbstractFeature {
	private Map<Integer, Integer> termFreqMap = new ConcurrentHashMap<Integer, Integer>();
	private int totalCorpusTermFreq = 0;
	
	protected FeatureCorpusTermFrequency (GlobalIndex index) {
		this.index = index;
	}
	
	public void setTotalCorpusTermFreq (int i) {
		this.totalCorpusTermFreq = i;
	}
	
	public int getTotalCorpusTermFreq () {
		return this.totalCorpusTermFreq;
	}
	
	/**
	 * Increment the number of occurrence of term by freq 
	 * @param term
	 * @param freq
	 */
	public void addToTermFreq (String term, int freq) {
		int termId = index.getTermCanonical(term);
		if (-1 != termId) {
			addToTermFreq(termId, freq);
		}
	}
	
	/**
	 * increment the number of occurrences of term with id t by i
	 * 
	 * @param term
	 * @param freq
	 */
	public void addToTermFreq (int term, int freq) {
		Integer f = termFreqMap.get(term);
		if (null == f) f = 0;
		termFreqMap.put(term, f + freq);
	}
	
	/**
	 * @param term
	 * @return the number of occurrences of a term in the corpus
	 */
	public int getTermFreq (String term) {
		int termid = index.getTermCanonical(term);
		if (termid != -1) return getTermFreq(termid);
		return 0;
	}
	
	/**
	 * @param id
	 * @return the number of occurrences of a term in the corpus
	 */
	public int getTermFreq (int id) {
		Integer freq = termFreqMap.get(id);
		if (freq != null) return freq;
		return 0;
	}
}
