package termex.core.feature;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import termex.core.feature.index.GlobalIndex;
import termex.model.doc.Document;

/**
 * A feature container that stores information of term distributions in each document.
 * Include:
 * -- number of occurrences of terms found in the corpus.
 * -- number of occurrences of each term found in each document.
 * -- existence of terms in documents.
 * 
 * @author jyfeather88
 *
 */
public class FeatureDocumentTermFrequency extends AbstractFeature {
	/* Map<Term, Map<Doc, Freq>> */
	private Map<Integer, Map<Integer, Integer>> termInDocFreqMap = new ConcurrentHashMap<Integer, Map<Integer,Integer>>();
	private int totalTermFreq;
	
	public FeatureDocumentTermFrequency (GlobalIndex index) {
		this.index = index;
	}
	
    /**
     * Set total number of occurrences of all terms in the corpus
     *
     * @param i
     */
    public void setTotalCorpusTermFreq(int i) {
        totalTermFreq = i;
    }

    public int getTotalCorpusTermFreq() {
        return totalTermFreq;
    }
    
	/**
	 * Increment term t's number of occurrences in d by freq
	 * 
	 * @param s
	 * @param d
	 * @param freq
	 */
	public void addToTermFreqInDoc (String t, Document d, int freq) {
		int termId = index.getTermCanonical(t);
		int docId = index.getDocument(d);
		if (-1 == termId) {
			System.err.println("Term (" + t + ") has not been indexed! Ignored.");
	 	} else if (-1 == docId) {
	 		System.err.println("Document (" + d.getUrl() + ") has not been indexed! Ignored.");
	 	} else {
	 		addToTermFreqInDoc(termId, docId, freq);
	 	}
	} 
	
	/**
	 * Increment term t (id) number of occurrences in d by freq
	 * 
	 * @param t
	 * @param d
	 * @param freq
	 */
    public void addToTermFreqInDoc(int t, int d, int freq) {
    		Map<Integer, Integer> freqs = termInDocFreqMap.get(t);
    		if (null == freqs) {
    			freqs = new HashMap<Integer, Integer>();
    		}
    		freqs.put(d, freq);
    		termInDocFreqMap.put(t, freqs);
    }
    
    /**
     * Given term id, return freqs map in each doc
     * 
     * @param t
     * @return
     */
    public Map<Integer, Integer> getDocFreqMap(int t) {
    		return termInDocFreqMap.get(t);
    }
    
    /**
     * @param t
     * @return the IDs of documents in which term t are found
     */
    public int[] getTermAppear(String t) {
        int termId = index.getTermCanonical(t);
        if (termId == -1) {
            return new int[0];
        } else {
            Set<Integer> keys = termInDocFreqMap.get(termId).keySet();
            int[] rs = new int[keys.size()];
            int c = 0;
            for (Integer k : keys) {
                rs[c] = k;
                c++;
            }
            return rs;
        }
    }

    /**
     * @param term
     * @return number of occurrences of a term in all documents
     */
    public int getSumTermFreqInDocs(String term) {
        int termId = index.getTermCanonical(term);
        if (termId == -1) {
            return 0;
        } else {
            return getSumTermFreqInDocs(termId);
        }
    }

    /**
     * @param term
     * @return number of occurrences of a term in all documents
     */
    public int getSumTermFreqInDocs(int term) {
        Set<Integer> docs = index.getDocIdsContainingTermCanonical(term);
        int sum = 0;
        Iterator<Integer> it = docs.iterator();
        while (it.hasNext())
            sum += getTermFreqInDoc(term, it.next());
        return sum;
    }

    /**
     * @param t
     * @param d
     * @return number of occurrences of a term identified by id t in a document identified by id d
     */
    public int getTermFreqInDoc(int t, int d) {
        Map<Integer, Integer> freqInDocs = termInDocFreqMap.get(t);
        if (freqInDocs == null) return 0;
        return freqInDocs.get(d);
    }

    /**
     * @param t
     * @param d
     * @return number of occurrences of a term t in a document identified by id d
     */
    public int getTermFreqInDoc(String t, int d) {
        int termId = index.getTermCanonical(t);
        if (termId == -1) {
            return 0;
        } else {
            return getTermFreqInDoc(termId, d);
        }
    }
}
