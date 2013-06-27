package termex.io;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Set;

import termex.core.feature.index.GlobalIndex;
import termex.model.term.Term;

/**
 * A helper class for writing output into files
 * 
 * @author jyfeather88
 *
 */
public class ResultWrite2File {

	private GlobalIndex _index;
	
	/**
	 * The writer will read mapping data from term canonical from to variant forms and output the result
	 * 
	 * @param index
	 */
	public ResultWrite2File(GlobalIndex index) {
		_index = index;
	}
	
	public void output (Term[] result, String path) {
		try {
			PrintWriter pw = new PrintWriter(new FileWriter(path));
			if (null == _index) {
				for (Term c : result) {
					pw.println(c.getConcept() + "\t\t\t" + c.getConfidence());
				}
			} else {
				for (Term c : result) {
					Set<String> originals = _index.getVariantsByTermCanonical(c.getConcept());
					if(originals==null)
						pw.println(c.getConcept() + "\t\t\t" + c.getConfidence());
					else
						pw.println(c.getConcept()+" |"+writeToString(originals) + "\t\t\t" + c.getConfidence());
				}
			}
			pw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private String writeToString(Set<String> container) {
		StringBuilder sb = new StringBuilder();
		for (String s : container) {
			sb.append(s).append(" |");
		}
		return sb.toString().substring(0, sb.lastIndexOf("|"));
	}
}
