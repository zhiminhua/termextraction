package termex.core.extractor;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import opennlp.tools.util.InvalidFormatException;

import termex.model.corpus.Corpus;
import termex.model.doc.Document;
import termex.util.nlp.NLPToolsController_openNLP;
import termex.util.preprocessor.Normalizer;
import termex.util.preprocessor.StopList;
import termex.util.properties.Property;

/**
 * Nounphrase extractor implemented with OpenNLP tools. It applies certain heuristics to clean a candidate noun phrase and
 * return it to the normalised root. These heuristics include:
 * 
 * -Stopwords will be trimmed from the head and tails of a phrase. E.g, "the cat on the mat" becomes "cat on the mat".
 * -phrases containing "or" "and" will be split, e.g., "Tom and Jerry" becomes "tom" "jerry"
 * -must have letters
 * -must have at least two characters
 * -characters that do not match the pattern [a-zA-Z\-] are replaced with whitespaces.
 * -may or may not have digits, this is set by the property file
 * -must contain no more than N words, this is set by the property file
 * 
 * @author jyfeather88
 *
 */

public class NounPhraseExtractor_openNLP extends BasicExtractor {

    public NounPhraseExtractor_openNLP(StopList stop, Normalizer normaliser) throws IOException {
        this.stoplist = stop;
        this.normaliser = normaliser;
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
		Map<String, Set<String>> res = new HashMap<String, Set<String>>();
		for (String s : NLPToolsController_openNLP.getInstance().getSentenceSplitter().sentDetect(d.getContent())) {
			for (Map.Entry<String, Set<String>> e : extract(s).entrySet()) {
				Set<String> variants = res.get(e.getKey());
                variants = variants == null ? new HashSet<String>() : variants;
                variants.addAll(e.getValue());
                res.put(e.getKey(), variants);
			}
		}
		return res;
	}

	@Override
	public Map<String, Set<String>> extract(String content) throws Exception {
		Map<String, Set<String>> nouns = new HashMap<String, Set<String>>();
		
        String[] tokens = NLPToolsController_openNLP.getInstance().getTokeniser().tokenize(content);
        String[] pos = NLPToolsController_openNLP.getInstance().getPosTagger().tag(tokens);
        String[] candidates = chunkNPs(tokens, pos);
        
        for (String c : candidates) {
            c = applyCharacterReplacement(c, Property.TERM_CLEAN_PATTERN);	
            String[] e = applySplitList(c);
            
            for (String str : e) {
                String stopremoved = applyTrimStopwords(str, stoplist, normaliser);
                if (stopremoved == null) continue;
                String original = stopremoved;
                str = normaliser.normalize(stopremoved.toLowerCase()).trim();
                String[] nelements = str.split("\\s+");
                
                if (nelements.length < 1 || nelements.length > Integer.valueOf(Property.getInstance().getMaxMultipleWords())) continue;
                if (Property.getInstance().isIgnoringDigits() && containDigit(str)) continue;
                if (!containLetter(str)) continue;
                if (!hasReasonableNumChars(str)) continue;
                
                if (c.toLowerCase().indexOf(str) != -1) {
                    Set<String> variants = nouns.get(str);
                    variants = variants == null ? new HashSet<String>() : variants;
                    variants.add(original);
                    nouns.put(str, variants);
                }
            }
        }
		return nouns;
	}

    private String[] chunkNPs(String[] tokens, String[] pos) throws InvalidFormatException, FileNotFoundException, IOException {
        String[] phrases = NLPToolsController_openNLP.getInstance().getPhraseChunker().chunk(tokens, pos);
        List<String> candidates = new ArrayList<String>();
        String phrase = "";
        for (int n = 0; n < tokens.length; n++) {
        	if (phrases[n].equals("B-NP")) {
                phrase = tokens[n];
                for (int m = n + 1; m < tokens.length; m++) {
                    if (phrases[m].equals("I-NP")) {
                        phrase = phrase + " " + tokens[m];
                    } else {
                        n = m;
                        break;
                    }
                }
                phrase = phrase.replaceAll("\\s+", " ").trim();
                if (phrase.length() > 0) candidates.add(phrase);
            }
        }
        return candidates.toArray(new String[0]);
    }
}
