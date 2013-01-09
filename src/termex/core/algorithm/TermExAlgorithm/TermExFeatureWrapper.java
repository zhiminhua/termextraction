package termex.core.algorithm.TermExAlgorithm;

import java.util.Set;

import termex.core.algorithm.AbstractFeatureWrapper;
import termex.core.feature.FeatureCorpusTermFrequency;
import termex.core.feature.FeatureDocumentTermFrequency;
import termex.core.feature.FeatureRefCorpusTermFrequency;


/**
 * <code>TermExFeatureWrapper</code> wraps three instances of Feature, include:
 * <br>-- an instance of <code>FeatureDocumentTermFrequency</code>
 * <br>-- an instance of <code>FeatureCorpusTermFrequency</code>
 * <br>-- an instance of <code>FeatureRefCorpusTermFrequency</code>
 * 
 * @author jyfeather88
 *
 */

public class TermExFeatureWrapper extends AbstractFeatureWrapper {

	private FeatureDocumentTermFrequency termDocFreq;
	private FeatureCorpusTermFrequency wordFreq;
	private FeatureRefCorpusTermFrequency refWordFreq;
	
	public TermExFeatureWrapper(FeatureDocumentTermFrequency termDocFreq, FeatureCorpusTermFrequency wordFreq, FeatureRefCorpusTermFrequency refWordFreq) {
		this.termDocFreq = termDocFreq;
		this.wordFreq = wordFreq;
		this.refWordFreq = refWordFreq;
	}

	@Override
	public Set<String> getTerms() {
		return termDocFreq.getGlobalIndex().getTermsCanonical();
	}

	/**
	 * @param wi
	 * @return the number of occurrences of a word in the corpus.
	 */
	public double getWordFreq(String wi) {
		Integer freq = wordFreq.getTermFreq(wi);
		if (0 == freq) {
			int termid = 	wordFreq.getGlobalIndex().getTermCanonical(wi);
			freq = wordFreq.getTermFreq(termid);
		}
		return freq == 0 ? 1 : freq;
	}

	/**
	 * @return total number of occurrences of terms in the corpus.
	 */
	public double getTotalCorpusTermFreq() {
		return termDocFreq.getTotalCorpusTermFreq();
	}

	/**
	 * @param wi
	 * @return the normalized frequency of a word in the reference corpus. 
	 */
	public double getRefWordFreqNorm(String wi) {
		return refWordFreq.getNormalizedTermFreq(wi);
	}

	/**
	 * @param s
	 * @return the IDs of documents in which term t is found.
	 */
	public int[] getTermAppear(String s) {
		return termDocFreq.getTermAppear(s);
	}

	/**
	 * @param s
	 * @param i
	 * @return normalized term frequency in a document with id=d. It is equal to freq of term t in d divided by total term frequency in d.
	 */
	public double getNormFreqInDoc(String s, int d) {
		return (getTermFreqInDoc(s, d) + 0.1) / (getTermFreq(s) + 1);
	}

	/**
	 * @param s
	 * @return the number of occurrences of a candidate term in the corpus
	 */
	public int getTermFreq(String s) {
		int freq = termDocFreq.getSumTermFreqInDocs(s);
		return freq == 0 ? 1 : freq;
	}
	
	/**
	 * @param term
	 * @param d
	 * @return the term's frequency in document with id=d
	 */
	public int getTermFreqInDoc(String term, int d) {
		return termDocFreq.getTermFreqInDoc(term, d);
	}
}
