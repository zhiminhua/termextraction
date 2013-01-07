package termex.util.nlp;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import opennlp.tools.chunker.Chunker;
import opennlp.tools.chunker.ChunkerME;
import opennlp.tools.chunker.ChunkerModel;
import opennlp.tools.postag.POSModel;
import opennlp.tools.postag.POSTagger;
import opennlp.tools.postag.POSTaggerME;
import opennlp.tools.sentdetect.SentenceDetector;
import opennlp.tools.sentdetect.SentenceDetectorME;
import opennlp.tools.sentdetect.SentenceModel;
import opennlp.tools.tokenize.Tokenizer;
import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;
import opennlp.tools.util.InvalidFormatException;
import termex.util.properties.Property;

/**
 * A singleton class which controls creation and dispatches of OpenNLP tools
 * 
 * @author jyfeather88
 *
 */
public class NLPToolsController_openNLP {
	
	private static NLPToolsController_openNLP ref;
	
	private POSTagger posTagger;
	private Chunker npChunker;
	private SentenceDetector sentDetect;
	private Tokenizer tokenizer;
	
	private NLPToolsController_openNLP() throws InvalidFormatException, FileNotFoundException, IOException {
		// create POSTagger
		POSModel posModel = new POSModel(new FileInputStream(Property.getInstance().getNLPPath() + "/en-pos-maxent.bin"));
		posTagger = new POSTaggerME(posModel);
		
		// create Chunker
		ChunkerModel chunkerModel = new ChunkerModel(new FileInputStream(Property.getInstance().getNLPPath()+"/en-chunker.bin"));
		npChunker = new ChunkerME(chunkerModel);
		
		// create SentenceDetector
		 SentenceModel sentModel = new SentenceModel(new FileInputStream(Property.getInstance().getNLPPath()+"/en-sent.bin"));
		 sentDetect = new SentenceDetectorME(sentModel);
	        
		// create Tokenizer
		TokenizerModel tokenizerModel = new TokenizerModel(new FileInputStream(Property.getInstance().getNLPPath()+"/en-token.bin"));
		tokenizer = new TokenizerME(tokenizerModel);
	}
	
	public static NLPToolsController_openNLP getInstance() throws InvalidFormatException, FileNotFoundException, IOException {
		if (null == ref) ref = new NLPToolsController_openNLP(); 
		return ref;
	}
	
	public Object clone() throws CloneNotSupportedException {
	      throw new CloneNotSupportedException();
	}

	public POSTagger getPosTagger() {
		return posTagger;
	}

	public Chunker getPhraseChunker() {
		return npChunker;
	}

	public SentenceDetector getSentenceSplitter() {
		return sentDetect;
	}

	public Tokenizer getTokeniser() {
		return tokenizer;
	}
}
