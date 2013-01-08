package termex.core.algorithm.TermExAlgorithm;

import java.util.Set;

import termex.core.algorithm.AbstractFeatureWrapper;
import termex.core.feature.FeatureCorpusTermFrequency;
import termex.core.feature.FeatureDocumentTermFrequency;
import termex.core.feature.FeatureRefCorpusTermFrequency;

public class TermExFeatureWrapper extends AbstractFeatureWrapper {

	private FeatureDocumentTermFrequency termFreq;
	private FeatureCorpusTermFrequency wordFreq;
	
	public TermExFeatureWrapper(FeatureDocumentTermFrequency termDocFreq,
			FeatureCorpusTermFrequency wordFreq2,
			FeatureRefCorpusTermFrequency bncRef) {
		// TODO Auto-generated constructor stub
	}

	@Override
	public Set<String> getTerms() {
		return termFreq.getGlobalIndex().getTermsCanonical();
	}

	public double getWordFreq(String wi) {
		// TODO Auto-generated method stub
		return 0;
	}

	public double getTotalCorpusTermFreq() {
		// TODO Auto-generated method stub
		return 0;
	}

	public double getRefWordFreqNorm(String wi) {
		// TODO Auto-generated method stub
		return 0;
	}

	public int[] getTermAppear(String s) {
		// TODO Auto-generated method stub
		return null;
	}

	public double getNormFreqInDoc(String s, int i) {
		// TODO Auto-generated method stub
		return 0;
	}

	public int getTermFreq(String s) {
		// TODO Auto-generated method stub
		return 0;
	}

}
