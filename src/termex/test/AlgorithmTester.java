package termex.test;

import java.util.HashMap;
import java.util.Map;

import termex.core.algorithm.AbstractFeatureWrapper;
import termex.core.algorithm.Algorithm;
import termex.core.algorithm.TermExAlgorithm.TermExAlgorithm;
import termex.core.algorithm.TermExAlgorithm.TermExFeatureWrapper;
import termex.core.feature.index.GlobalIndex;

public class AlgorithmTester {

	private Map<Algorithm, AbstractFeatureWrapper> algorithmRepo = new HashMap<Algorithm, AbstractFeatureWrapper>();
	
	public void registerAlgorithm(TermExAlgorithm a, TermExFeatureWrapper w) {
		algorithmRepo.put(a, w);
	}

	public void execute(GlobalIndex index, String output) {
	}

}
