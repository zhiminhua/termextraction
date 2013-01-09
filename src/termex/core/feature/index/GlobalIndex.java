package termex.core.feature.index;

import java.util.Map;
import java.util.Set;

import termex.model.doc.Document;

/**
 * Index terms (term canonical form, and variants), documents, and the containing/occur-in relation between terms and documents
 * 
 * @author jyfeather88
 *
 */
public abstract class GlobalIndex {
	protected int termCounter = 0;
	protected int docCounter = 0;
	protected int variantCounter = 0;
	
	/**
	 * Given a candidate term's canonical form, index it and return its ID;
	 * 
	 * @param term
	 * @return
	 */
	protected abstract int indexTermCanonical (String term);
	
	/**
	 * Given a candidate term's variant form, index it and return its ID;
	 * 
	 * @param termV
	 * @return
	 */
	protected abstract int indexTermVariant (String termV);
	
	/**
	 * Given a document, index it and return its ID;
	 * 
	 * @param d
	 * @return
	 */
	protected abstract int indexDocument(Document d);
	
	//################### Get Term Canonical Forms ######################
	
	/**
	 * Return all candidate term canonical forms
	 * 
	 * @return
	 */
	public abstract Set<String> getTermsCanonical ();
	
	/**
	 * Given the id of a candidate term, return its canonical form
	 * 
	 * @param id
	 * @return
	 */
	public abstract String getTermCanonical (int id);
	
	/**
	 * Given a candidate term's canonical form, return its id. (If the term has not been index, -1 will be returned
	 * 
	 * @return
	 */
	public abstract int getTermCanonical (String term);
	
	//################### Get Term Variant Forms ######################
	/**
	 * Given a term canonical form, get its variant forms found in the corpus
	 * 
	 * @param term
	 * @return
	 */
	public abstract Set<String> getVariantsByTermCanonical(String term);	
	
	/**
	 * Given an id of a candidate term variant, retrieve the content
	 * 
	 * @param id
	 * @return
	 */
	protected abstract String getVariantsById (int id);
	
	//################### Get Documents  ######################
	
	/**
	 * Return all indexed documents
	 * 
	 * @return
	 */
	public abstract Set<Document> getDocuments();
	
	/**
	 * Given a document, return its id. If the document has not been indexed, return -1
	 * 
	 * @param d
	 * @return
	 */
	public abstract int getDocument(Document d);
	
	//################### Term -- Variant ######################
	
	/**
	 * Given a map containing [term canonical form -- term variant forms], index the mapping
	 * 
	 * @param map
	 */
	protected abstract void indexTermWithVariant (Map<String, Set<String>> map);
	
	//################### Doc -- Term ######################
	
	/**
	 * Given a document d which contains a set of terms (canonical form), index the binary relation that is "document contains term canonical"
	 * 
	 * @param d
	 * @param terms
	 */
	protected abstract void indexDocWithTermsCanonical (Document d, Set<String> terms);
	
	/**
	 * @param t the candidate term's canonical form in question
	 * @return the document ids of which documents contain the candidate term t
	 */
	public abstract Set<Integer> getDocIdsContainingTermCanonical(String t);
	
	/**
	 * @param id the candidate term's canonical form in questoin
	 * @return the document ids of which documents contain the candidate term t
	 */
	public abstract Set<Integer> getDocIdsContainingTermCanonical(int id);
	
	//################### Term -- Doc ######################
	
	/**
	 * Given a candidate term's canonical form t found in document d, index the binary relation "t found_in d"
	 * 
	 * @param s
	 * @param d
	 */
	protected abstract void indexTermCanonicalInDoc (String s, Document d);
	
	/**
	 * @param d
	 * @return candidate term canonical forms in d
	 */
	public abstract Set<String> getTermsCanonicalInDoc (Document d);
	
	/**
	 * @param doc
	 * @return candidate term canonical forms in doc
	 */
    public abstract Set<String> getTermCanonicalInDoc(int doc);
    
	/**
	 * @param d
	 * @return the ids of canonical forms of terms found in the document d
	 */
	public abstract Set<Integer> getTermCanonicalIdsInDoc(Document d) ;

	/**
	 * @param d
	 * @return the ids of canonical forms of terms found in the document d
	 */
	public abstract Set<Integer> getTermCanonicalIdsInDoc(int d);
}
