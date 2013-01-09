package termex.core.feature.index;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import termex.model.doc.Document;

/**
 * <code>GlobalIndexImpl</code> stores information (in memory) of binary relations between candidate terms and corpus.
 * Include:
 * -- candidate term canonical forms and their int IDs
 * -- candidate term variant forms and their int IDs
 * -- mapping from candidate term canonical forms to their variant forms
 * -- documents in corpus and their int IDs
 * -- candidate term and their containing documents (ID-IDs)
 * -- documents and their contained candidate terms (ID-IDs)
 * 
 * @author jyfeather88
 *
 */

public class GlobalIndexImpl extends GlobalIndex {

	protected HashMap<String, Integer> termIdMap = new HashMap<String, Integer>();
    protected HashMap<String, Integer> variantIdMap = new HashMap<String, Integer>();
    protected HashMap<Document, Integer> docIdMap = new HashMap<Document, Integer>();

    protected Map<Integer, Set<Integer>> term2Docs = new HashMap<Integer, Set<Integer>>();
    protected Map<Integer, Set<Integer>> doc2Terms = new HashMap<Integer, Set<Integer>>();
    protected Map<Integer, Set<Integer>> term2Variants = new HashMap<Integer, Set<Integer>>();
    protected Map<Integer, Integer> variant2term = new HashMap<Integer, Integer>();

    public Map<String, Integer> getTermIdMap(){
        return termIdMap;
    }
    public Map<String, Integer> getVariantIdMap(){
        return variantIdMap;
    }
    public Map<Document, Integer> getDocIdMap(){
        return docIdMap;
    }
    public Map<Integer, Set<Integer>> getTerm2Docs(){
        return term2Docs;
    }
    public Map<Integer, Set<Integer>> getDoc2Terms(){
        return doc2Terms;
    }
    public Map<Integer, Set<Integer>> getTerm2Variants(){
        return term2Variants;
    }
    public Map<Integer, Integer> getVariant2Term(){
        return variant2term;
    }
    
    @Override
	protected int indexTermCanonical(String term) {
    		Integer index = termIdMap.get(term);
    		if (null == index) termIdMap.put(term, index = this.termCounter++);
    		return index;
	}

	@Override
	protected int indexTermVariant(String termV) {
		Integer index = variantIdMap.get(termV);
		if (null == index) variantIdMap.put(termV, index = this.variantCounter++);
		return index;
	}

	@Override
	protected int indexDocument(Document d) {
		Integer index = docIdMap.get(d);
		if (null == index) docIdMap.put(d, index = this.docCounter++);
		return index;
	}
	
	@Override
	protected void indexTermWithVariant(Map<String, Set<String>> map) {
		for (Map.Entry<String, Set<String>> e : map.entrySet()) {
			int termid = indexTermCanonical(e.getKey());
			Set<Integer> variants = term2Variants.get(termid);
			if (variants == null) {
				variants = new HashSet<Integer>();
			}
			for (String v : e.getValue()) {
				int varid = indexTermVariant(v);
				variants.add(varid);
				variant2term.put(varid, termid);
			}
			term2Variants.put(termid, variants);
		}
	}

	@Override
	protected void indexDocWithTermsCanonical(Document d, Set<String> terms) {
        Set<Integer> termids = new HashSet<Integer>();
        for (String t : terms) {
        		termids.add(indexTermCanonical(t));
        }
        indexDocWithTermsCanonical(indexDocument(d), termids);
	}
	
	private void indexDocWithTermsCanonical (int d, Set<Integer> terms) {
        Set<Integer> termids = doc2Terms.get(d);
        if (termids != null) termids.addAll(terms);
        doc2Terms.put(d, terms);
	}

	@Override
	protected void indexTermCanonicalInDoc(String s, Document d) {
		indexTermCanonicalInDoc(indexTermCanonical(s), indexDocument(d));
	}
	
	private void indexTermCanonicalInDoc (int t, int d) {
		Set<Integer> docs = term2Docs.get(t);
		if (null == docs) docs = new HashSet<Integer>();
		docs.add(d);
		term2Docs.put(t, docs);
	}
	
	@Override
	public Set<String> getTermsCanonical() {
		return this.termIdMap.keySet();
	}
	
	@Override
	public String getTermCanonical(int id) {
		for (Map.Entry<String, Integer> e : termIdMap.entrySet()) {
			if (e.getValue() == id) return e.getKey();
		}
		return null;
	}
	
	@Override
	public int getTermCanonical(String term) {
		Integer index = this.termIdMap.get(term);
		if (null == index) {
			return -1;
		}
		return index;
	}
	
	@Override
	protected String getVariantsById(int id) {
		for (Map.Entry<String, Integer> e : variantIdMap.entrySet()) {
			if (e.getValue() == id) return e.getKey(); 
		}
		return null;
	}
	
	@Override
	public Set<String> getVariantsByTermCanonical(String term) {
		Set<String> variants = new HashSet<String>();
		Set<Integer> vIDs = this.term2Variants.get(getTermCanonical(term));
		if (null == vIDs) return variants;
		Iterator<Integer> it = vIDs.iterator();
		while (it.hasNext()) {
			variants.add(getVariantsById(it.next()));
		}
		return variants;
	}
	
	@Override
	public Set<Document> getDocuments() {
		return docIdMap.keySet();
	}
	
	@Override
	public int getDocument(Document d) {
		Integer index = docIdMap.get(d);
		if (null == index) return -1;
		return index;
	}
	
	@Override
	public Set<String> getTermsCanonicalInDoc(Document d) {
		return getTermCanonicalInDoc(indexDocument(d));
	}

	@Override
	public Set<String> getTermCanonicalInDoc(int doc) {
		Set<String> res = new HashSet<String>();
		Set<Integer> termids = getTermCanonicalIdsInDoc(doc);
		Iterator<Integer> it = termids.iterator();
		while (it.hasNext()) {
			res.add(getTermCanonical(it.next()));
		}
		return res;
	}
	
	@Override
	public Set<Integer> getTermCanonicalIdsInDoc(Document d) {
		return getTermCanonicalIdsInDoc(indexDocument(d));
	}
	
	@Override
	public Set<Integer> getTermCanonicalIdsInDoc(int d) {
		Set<Integer> res = this.doc2Terms.get(d);
		return res == null ? new HashSet<Integer>() : res;
	}
	
    public Set<Integer> getDocIdsContainingTermCanonical(String t) {
        return getDocIdsContainingTermCanonical(indexTermCanonical(t));
    }

    public Set<Integer> getDocIdsContainingTermCanonical(int id) {
        Set<Integer> res = term2Docs.get(id);
        return res == null ? new HashSet<Integer>() : res;
    }

}
