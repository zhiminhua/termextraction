package termex.model.term;

/**
 * Define Term Meaning
 * 
 * @author jyfeather88
 *
 */
public class Term implements Comparable<Term> {
	
	private String singular;
	private double confidences;

	/**
	 * @param lemmatised form of the term.
	 * @param confidence the relevance of the term to the corpus from which it is extracted
	 */
	public Term(String s, double score) {
		singular = s;
		confidences = score;
	}

	@Override
	public int compareTo(final Term o) {
		return getConfidence() > o.getConfidence() ? -1 : getConfidence() < o.getConfidence() ? 1 : 0;
	}
	
	public boolean equals (Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		final Term that = (Term)o;
		return that.getConcept().equals(getConcept());
	}
	
	public int hashCode () {
		return getConcept().hashCode();
	}
	
	public String getConcept () {
		return this.singular;
	}
	
	public void setConcept (String s) {
		this.singular = s;
	}
	
	public double getConfidence () {
		return this.confidences;
	}
	
	public void setConfidence (double score) {
		this.confidences = score;
	}

}
