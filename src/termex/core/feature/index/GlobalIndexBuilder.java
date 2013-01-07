package termex.core.feature.index;

import termex.core.extractor.BasicExtractor;
import termex.model.corpus.Corpus;

/**
 * Build an instance of <code>GlobalIndex</code>
 * 
 * @author jyfeather88
 *
 */
public interface GlobalIndexBuilder {
	
	public GlobalIndex build (Corpus c, BasicExtractor extractor) throws Exception;
}
