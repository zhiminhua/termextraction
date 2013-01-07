package termex.util.counters;

import java.io.File;
import java.net.MalformedURLException;
import java.util.StringTokenizer;

import termex.model.corpus.CorpusImpl;
import termex.model.doc.Document;
import termex.model.doc.DocumentImpl;

public class WordCounter {

	public WordCounter() {
		
	}
	
	/**
	 * Count number of words in a corpus, delimited by a space character
	 * 
	 * @param c
	 * @return
	 */
	public int wordCounter (final CorpusImpl c) {
		int total = 0;
		for (Document doc : c) {
			StringTokenizer tokenizer = new StringTokenizer(doc.getContent().replaceAll("\\s+"," ").replaceAll("\\s+"," ")," ");
			total+=tokenizer.countTokens();
		}
		return total;
	}
	
	/**
	 * Count number of words in a document, delimited by a space character
	 * 
	 * @param d
	 * @return
	 */
	public int wordCounter (final Document d) {
		StringTokenizer tokenizer = new StringTokenizer(d.getContent().replaceAll("\\s+"," ").replaceAll("\\s+"," ")," ");
		return tokenizer.countTokens();		
	}
	
	/*
	 * For TEST
	 */
	public static void main(String[] args) {
		File targetFolder = new File(args[0]);
		File[] file_list = targetFolder.listFiles();
		CorpusImpl c = new CorpusImpl();
		for (File f : file_list) {
			try {
			      c.add(new DocumentImpl(f.toURI().toURL()));
		      } catch (MalformedURLException e) {
			      e.printStackTrace();
		      }
		}	
		System.out.println(new WordCounter().wordCounter(c));
	}
}
