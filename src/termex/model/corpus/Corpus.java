package termex.model.corpus;

import termex.model.doc.Document;

public interface Corpus extends Iterable<Document> {

	boolean add(Document d);
	
	boolean remove(Document d);
	
	boolean contains(Document d);

	int size();
}
