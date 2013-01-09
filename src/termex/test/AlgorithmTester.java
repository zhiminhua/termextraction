package termex.test;

import java.util.HashMap;
import java.util.Map;

import termex.core.algorithm.AbstractFeatureWrapper;
import termex.core.algorithm.Algorithm;
import termex.core.algorithm.TermExAlgorithm.TermExAlgorithm;
import termex.core.algorithm.TermExAlgorithm.TermExFeatureWrapper;
import termex.core.feature.index.GlobalIndex;
import termex.model.term.Term;

public class AlgorithmTester {

	private Map<Algorithm, AbstractFeatureWrapper> algorithmRepo = new HashMap<Algorithm, AbstractFeatureWrapper>();
	
	public void registerAlgorithm(Algorithm a, AbstractFeatureWrapper w) {
		algorithmRepo.put(a, w);
	}

	public void execute(GlobalIndex index, String output) throws Exception {
		if (algorithmRepo.size() == 0) throw new Exception("No algorithm registered!");
		
		for (Map.Entry<Algorithm, AbstractFeatureWrapper> e : algorithmRepo.entrySet()) {
			Term[] result = e.getKey().execute(e.getValue());
			for (Term s : result) {
				System.out.println(s.getConcept() + " : " + s.getConfidence());
			}
		}
	}

}
