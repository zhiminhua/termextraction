package termex.core.feature;

import termex.core.feature.index.GlobalIndex;

/**
 * Representation of a term extraction feature built on an instance of <code>GlobalIndex</code>
 * 
 * @author jyfeather88
 *
 */

public abstract class AbstractFeature {
	protected GlobalIndex index;
	
	public GlobalIndex getGlobalIndex () {
		return this.index;
	}
	
	public String toString(){
		return this.getClass().toString();
	}
}
