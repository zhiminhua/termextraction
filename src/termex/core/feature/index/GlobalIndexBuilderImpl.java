package termex.core.feature.index;

import java.util.Map;
import java.util.Set;

import termex.core.extractor.BasicExtractor;
import termex.model.corpus.Corpus;
import termex.model.doc.Document;

public class GlobalIndexBuilderImpl implements GlobalIndexBuilder {

	private boolean BUILD_TERM_TO_DOC_MAP = true;
	private boolean BUILD_DOC_TO_TERM_MAP = true;
	
	public GlobalIndexBuilderImpl() {
		
	}
	
	public GlobalIndexBuilderImpl(boolean textunit2DocMap, boolean doc2TextunitMap) {
		this.BUILD_TERM_TO_DOC_MAP = textunit2DocMap;
		this.BUILD_DOC_TO_TERM_MAP = doc2TextunitMap;
	}
	
	@Override
	public GlobalIndexImpl build(Corpus c, BasicExtractor extractor) throws Exception {
		GlobalIndexImpl index = new GlobalIndexImpl();
		for (Document d : c) {
			Map<String,Set<String>> nps = extractor.extract(d);
			index.indexTermWithVariant(nps);
			if (BUILD_DOC_TO_TERM_MAP) {
				index.indexDocWithTermsCanonical(d, nps.keySet());
			}
			if (BUILD_TERM_TO_DOC_MAP) {
				for (String s : nps.keySet()) {
					index.indexTermCanonicalInDoc(s, d);
				}
			}
		}
		return index;
	}

}
