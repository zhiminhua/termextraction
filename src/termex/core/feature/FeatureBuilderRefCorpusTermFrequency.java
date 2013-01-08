package termex.core.feature;

import java.io.BufferedReader;
import java.io.FileReader;

import termex.core.feature.index.GlobalIndex;
import termex.util.counters.TermFreqCounter;
import termex.util.counters.WordCounter;
import termex.util.preprocessor.Normalizer;

/**
 * A specific type of feature builder that builds an instance of FeatureRefCorpusTermFrequency. 
 * This is a dummy class which reads the data from a text file which stores information as:
 * -- [freq_in_corpus]      [term]
 * 
 * @author jyfeather88
 *
 */
public class FeatureBuilderRefCorpusTermFrequency extends AbstractFeatureBuilder {

	private final String refPath;
	
	public FeatureBuilderRefCorpusTermFrequency(String refPath) {
		super(null, null, null);
		this.refPath = refPath;
	}

	@Override
	public FeatureRefCorpusTermFrequency build(GlobalIndex index) throws Exception {
		FeatureRefCorpusTermFrequency feature = new FeatureRefCorpusTermFrequency();
		
		@SuppressWarnings("resource")
		final BufferedReader reader = new BufferedReader(new FileReader(refPath));
		String line;
		while ((line = reader.readLine()) != null) {
			line = line.trim();
			String[] elements = line.split("\\s+");
			if (Integer.valueOf(elements[0]) < 2) continue;
			feature.addToTermFreq(elements[1].trim(), Integer.valueOf(elements[0]));
		}
		
		return feature;
	}

}
