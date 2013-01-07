package termex.core.feature;

import java.util.Set;

import termex.core.extractor.BasicExtractor;
import termex.core.feature.index.GlobalIndex;
import termex.model.doc.Document;
import termex.util.counters.TermFreqCounter;
import termex.util.counters.WordCounter;
import termex.util.preprocessor.Normalizer;
import termex.util.properties.Property;

/**
 * Build an instance of <code>FeatureCorpusTermFrequency</code> from a GlobalIndex.
 * 
 * @author jyfeather88
 *
 */

public class FeatureBuilderCorpusTermFrequency extends AbstractFeatureBuilder {

	public FeatureBuilderCorpusTermFrequency(TermFreqCounter tfCounter,
			WordCounter wordCounter, Normalizer normalizer) {
		super(tfCounter, wordCounter, normalizer);
	}

	@Override
	public FeatureCorpusTermFrequency build(GlobalIndex index) throws Exception {
		FeatureCorpusTermFrequency feature = new FeatureCorpusTermFrequency(index);
		
		if (index.getTermsCanonical().size() == 0 || index.getDocuments().size() == 0) {
			throw new Exception("FROM TermEx: No resources indexed!");
		}
		
		int totalCorpusTermFreq = 0;
		
		for (Document d : index.getDocuments()) {
			String context = BasicExtractor.applyCharacterReplacement(d.getContent(), Property.TERM_CLEAN_PATTERN);
			totalCorpusTermFreq += wordCounter.wordCounter(d);
			Set<String> candidates = index.getTermsCanonicalInDoc(d);
			
			for (String s : candidates) {
				int freq = tfCounter.count(index.getVariantsByTermCanonical(s), context);
				feature.addToTermFreq(s, freq);
			}
		}
		
		feature.setTotalCorpusTermFreq(totalCorpusTermFreq);
		
		return feature;
	}

}
