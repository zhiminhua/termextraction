package termex.core.extractor;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import termex.model.corpus.Corpus;
import termex.model.doc.Document;
import termex.util.preprocessor.Normalizer;
import termex.util.preprocessor.StopList;
import termex.util.properties.Property;

/**
 * Extracts words from texts. Words will be normalized to reduce inflections . Characters that do not match the pattern [a-zA-A\-] are replaced by whitespaces.
 * 
 * @author jyfeather88
 *
 */
public class WordExtractor extends BasicExtractor{

    private boolean removeStop = true;
    private int minCharsInWord = 2;
    
    public WordExtractor(StopList stop, Normalizer normalizer) {
        this.stoplist = stop;
        this.normaliser = normalizer;
    }
    
    public WordExtractor(StopList stop, Normalizer normalizer, boolean removeStop, int minCharsInWord) {
    		this.stoplist = stop;
        this.normaliser = normalizer;
        this.removeStop = removeStop;
        this.minCharsInWord = minCharsInWord;
    }
    
	@Override
	public Map<String, Set<String>> extract(Corpus c) throws Exception {
		Map<String, Set<String>> res = new HashMap<String, Set<String>>();
		for (Document d : c) {
			 for (Map.Entry<String, Set<String>> e : extract(d).entrySet()) {
	                Set<String> variants = res.get(e.getKey());
	                variants = variants == null ? new HashSet<String>() : variants;
	                variants.addAll(e.getValue());
	                res.put(e.getKey(), variants);
			 }
		}
		return res;
	}

	@Override
	public Map<String, Set<String>> extract(Document d) throws Exception {
		return extract(d.getContent());
	}

	@Override
	public Map<String, Set<String>> extract(String s) throws Exception {
		String[] words = applyCharacterReplacement(s, Property.TERM_CLEAN_PATTERN).split(" ");
		Map<String, Set<String>> result = new HashMap<String, Set<String>>();
		
		for (String w : words) {
			String nw = w.trim();
			
			nw = nw.toLowerCase();
			nw = normaliser.normalize(nw).trim();
			
			if (!containLetter(nw) && !containDigit(nw)) continue;
			if (nw.length() < minCharsInWord) continue;
			if (removeStop && (stoplist.isStopWord(nw) || stoplist.isStopWord(w.trim()))) continue;
			
			if (nw.length() > 0) {
				Set<String> variants = result.get(nw);
				variants = variants == null ? new HashSet<String>() : variants;
				variants.add(w);
				result.put(nw, variants);
			}
		}
		return result;
	}
}
