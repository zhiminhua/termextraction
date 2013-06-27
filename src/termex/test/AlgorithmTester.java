package termex.test;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import termex.core.algorithm.AbstractFeatureWrapper;
import termex.core.algorithm.Algorithm;
import termex.core.feature.index.GlobalIndex;
import termex.io.ResultWrite2File;
import termex.model.term.Term;

/**
 * Tester for all algorithms of term extraction
 * 
 * @author jyfeather88
 *
 */
public class AlgorithmTester {

	private Map<Algorithm, AbstractFeatureWrapper> algorithmRepo = new HashMap<Algorithm, AbstractFeatureWrapper>();
	
	/**
	 * Register one algorithm
	 * 
	 * @param a
	 * @param w
	 */
	public void registerAlgorithm(Algorithm a, AbstractFeatureWrapper w) {
		algorithmRepo.put(a, w);
	}

	/**
	 * Execute the tester to analysis this algorithm
	 * 
	 * @param index
	 * @param output
	 * @throws Exception
	 */
	public void execute(GlobalIndex index, String output) throws Exception {
		ResultWrite2File writer = new ResultWrite2File(index);
		if (algorithmRepo.size() == 0) throw new Exception("No algorithm registered!");
		
		for (Map.Entry<Algorithm, AbstractFeatureWrapper> e : algorithmRepo.entrySet()) {
			Term[] result = e.getKey().execute(e.getValue());
			writer.output(result, output + File.separator + e.getKey().toString() + ".txt");
		}
	}

}
