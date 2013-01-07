package termex.util.counters;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * Count frequencies of phrases / n-grams in corpus
 * 
 * @author jyfeather88
 *
 */

public class TermFreqCounter {

	/**
	 * Count whole number of occurrences of a string in a context 
	 * 
	 * @param noun
	 * @param context
	 * @return
	 */
	public int count (String noun, String context) {
		int freq = 0;
		int start = 0;
		int next;
		while (start <= context.length()) {
			next = context.indexOf(noun, start);
			char prefix = next - 1 < 0 ? ' ' : context.charAt(next - 1);
			char suffix = next + noun.length() >= context.length() ? ' ' : context.charAt(next + noun.length());
			if (next != -1 && isValidChar(prefix) && isValidChar(suffix)) {
				freq++;
			}
			if (next == -1 || next == context.length()) break;
			start = next + noun.length();
		}
		return freq;
	}
	
	/**
	 * Count the offset of occurrences of a string in a context
	 * 
	 * @param noun
	 * @param context
	 * @return
	 */
	public Set<Integer> countOffsets (String noun, String context) {
        Set<Integer> offsets = new HashSet<Integer>();
        int next;
        int start = 0;
        while (start <= context.length()) {
            next = context.indexOf(noun, start);
            char prefix = next - 1 < 0 ? ' ' : context.charAt(next - 1);
            char suffix = next + noun.length() >= context.length() ? ' ' : context.charAt(next + noun.length());
            if (next != -1 && isValidChar(prefix) && isValidChar(suffix)) {
                offsets.add(next);
            }
            if (next == -1) break;
            start = next + noun.length();
        }
        return offsets;
	}
	
	/**
	 * Count total number of occurrences of a set of strings in a context
	 * 
	 * @param terms
	 * @param context
	 * @return
	 */
	public int count (Set<String> terms, String context) {
		Set<Integer> offsets = new HashSet<Integer>();
		if (terms != null) {
			for (String s : terms) {
				offsets.addAll(countOffsets(s, context));
			}
		}
		return offsets.size();
	}
	
	private boolean isValidChar (char c) {
		return !Character.isLetter(c) && !Character.isDigit(c);
	}
	
	/*
	 *  For TEST
	 */
	public static void main (String args[]) {
		TermFreqCounter tfc = new TermFreqCounter();
		String term = "me";
		String context = "Love me like me miss me!";
		
		// public int count (String noun, String context);
		int num = tfc.count(term, context);
		System.out.println("Number of " + term + " is = " + num);
		
		// public Set<Integer> countOffsets (String noun, String context);
		Set<Integer> test = tfc.countOffsets(term, context);
		Iterator<Integer> it = test.iterator();
		while (it.hasNext()) {
			System.out.println(it.next());
		}
		
		// public int count (Set<String> terms, String context);
		Set<String> set = new HashSet<String>();
		set.add(term);
		set.add("like");
		int num2 = tfc.count(set, context);
		System.out.println("Number of the set is = " + num2);
	}
}
