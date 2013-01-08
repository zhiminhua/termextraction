package termex.core.algorithm.TermExAlgorithm;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import termex.core.algorithm.AbstractFeatureWrapper;
import termex.core.algorithm.Algorithm;
import termex.model.term.Term;

/**
 * Implement of this paper <TermExtractor: a Web Application to Learn the Common Terminology of Interest Groups and Research Communities.>
 * In this formula w(t, Di) = a* DR + B* DC + Y* LC, default values of a, B, and Y are 0.33. 
 * 
 * @author jyfeather88
 *
 */

public class TermExAlgorithm implements Algorithm {
	private final double alpha;
	private final double beta;
	private final double zeta;
	
	public TermExAlgorithm () {
		this(0.33, 0.33, 0.34);
	}
	
	public TermExAlgorithm (double a, double b, double c) {
		alpha = a;
		beta = b;
		zeta = c;
	}
	
	public String toString () {
		return "Term Extraction Algorithm";
	}

	@Override
	public Term[] execute(AbstractFeatureWrapper store) throws Exception {
		if (!(store instanceof TermExFeatureWrapper)) throw new Exception("Required: TermExFeatureWrapper");
		TermExFeatureWrapper feature = (TermExFeatureWrapper)store;
		Set<Term> result = new HashSet<Term>();
		
		for (String s : feature.getTerms()) {
			double score;
			String[] elements = s.split(" ");
			double T = (double)elements.length;
			double SUMwi = 0;
			double SUMfwi = 0;
			
			for (int i = 0; i < T; i++) {
				String wi = elements[i];
				SUMwi += (double) feature.getWordFreq(wi) / (double) feature.getTotalCorpusTermFreq() /
						(feature.getRefWordFreqNorm(wi) + ((double) feature.getWordFreq(wi) / (double) feature.getTotalCorpusTermFreq()));
				SUMfwi += (double) feature.getWordFreq(wi);
			}
			
			/* Calculate DC */
			int[] docs = feature.getTermAppear(s);
			double sum = 0;
			for (int i : docs) {
				double norm = feature.getNormFreqInDoc(s, i);
				if (norm == 0) {
					sum += 0;
				} else {
					sum += norm * Math.log(norm + 0.1);
				}
			}
			
			double DR = SUMwi;
			double DC = sum;
			double LC = (T + Math.log(feature.getTermFreq(s) + 1)) * feature.getTermFreq(s) / SUMfwi;
			
			score = alpha * DR + beta * DC + zeta * LC;
			result.add(new Term(s, score));
		}
		
		Term[] all = result.toArray(new Term[0]);
		Arrays.sort(all);
		return null;
	}

}
