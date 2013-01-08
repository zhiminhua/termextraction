package termex.core.feature;

import java.util.HashMap;
import java.util.Map;

/**
 * A feature store that contains information of term distributions over a reference corpus. 
 * Include:
 * -- total number of occurrences of all terms found in the reference corpus, which is the sum of occurrences of each term
 * -- number of occurrences of each term found in the reference corpus
 * 
 * @author jyfeather88
 *
 */
public class FeatureRefCorpusTermFrequency extends AbstractFeature {
	private Map<String, Integer> refTermFreqMap = new HashMap<String, Integer>();
	private int totalCorpusTermFreq = 0;
	
	protected FeatureRefCorpusTermFrequency () {
		this.index = null;
	}
	
	public int getTotalRefCorpusTermFreq(){
		return totalCorpusTermFreq;
	}
	
	public void setTotalRefCorpusTermFreq(int i){
		totalCorpusTermFreq=i;
	}
	
	public void addToTermFreq(String t, int i){
		Integer freq = refTermFreqMap.get(t);
		if(freq == null) refTermFreqMap.put(t,i);
		else refTermFreqMap.put(t,freq+i);
		totalCorpusTermFreq += i;
	}

	public int getTermFreq(String t){
		Integer freq = refTermFreqMap.get(t);
		return freq == null ? 0 : freq;
	}

	/**
	 * Get the normalized frequency of a term in the corpus, which is the number of occurrences of that term as a fraction
	 * of the total number of occurrences of all terms in the corpus.
	 * @param w the id of the term
	 * @return
	 */
	public double getNormalizedTermFreq(String w){
		return (double) getTermFreq(w) / ((double) getTotalRefCorpusTermFreq()+1);
	}

}
