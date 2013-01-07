package termex.core.feature;

import java.util.Set;

import termex.core.extractor.BasicExtractor;
import termex.core.feature.index.GlobalIndex;
import termex.model.doc.Document;
import termex.util.counters.TermFreqCounter;
import termex.util.counters.WordCounter;
import termex.util.preprocessor.Normalizer;
import termex.util.properties.Property;

public class FeatureBuilderDocumentTermFrequency extends AbstractFeatureBuilder {

	public FeatureBuilderDocumentTermFrequency(TermFreqCounter tfCounter,
			WordCounter wordCounter, Normalizer normalizer) {
		super(tfCounter, wordCounter, normalizer);
	}

	@Override
	public FeatureDocumentTermFrequency build(GlobalIndex index) throws Exception {
		FeatureDocumentTermFrequency feature = new FeatureDocumentTermFrequency(index);
		if (index.getTermsCanonical().size() == 0 || index.getDocuments().size() == 0) {
			throw new Exception("FROM TermEx: No resources indexed!");
		}
		int totalTermFreq = 0;
		for (Document d : index.getDocuments()) {
			totalTermFreq += wordCounter.wordCounter(d);
			String context = BasicExtractor.applyCharacterReplacement(d.getContent(), Property.TERM_CLEAN_PATTERN);
			Set<String> candidates = index.getTermsCanonicalInDoc(d);
			for (String np : candidates) {
				int tfreq = tfCounter.count(index.getVariantsByTermCanonical(np), context);
				feature.addToTermFreqInDoc(np, d, tfreq);
			}
		}
		feature.setTotalCorpusTermFreq(totalTermFreq);
		return feature;
	}

}
