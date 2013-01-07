package termex.model.corpus;

import java.io.File;
import java.net.MalformedURLException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import termex.model.doc.Document;
import termex.model.doc.DocumentImpl;

public class CorpusImpl implements Corpus {

	private Set<Document> docs;
	
	public CorpusImpl(){
		docs = new HashSet<Document>();
	}
	
	public CorpusImpl(String path){
		docs=new HashSet<Document>();
		File targetFolder = new File(path);
		File[] files = targetFolder.listFiles();
		for (File f : files) {
			try {
				this.add(new DocumentImpl(f.toURI().toURL()));
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
		}//for
	}
	
	@Override
	public Iterator<Document> iterator() {
		// TODO Auto-generated method stub
		return docs.iterator();
	}

	@Override
	public boolean add(Document d) {
		// TODO Auto-generated method stub
		return docs.add(d);
	}

	@Override
	public boolean remove(Document d) {
		// TODO Auto-generated method stub
		return docs.remove(d);
	}

	@Override
	public boolean contains(Document d) {
		// TODO Auto-generated method stub
		return docs.contains(d);
	}

	@Override
	public int size() {
		// TODO Auto-generated method stub
		return docs.size();
	}

}
