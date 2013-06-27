package termex.test;

import java.util.*;

import termex.core.algorithm.TermExAlgorithm.TermExAlgorithm;
import termex.core.algorithm.TermExAlgorithm.TermExFeatureWrapper;
import termex.core.extractor.BasicExtractor;
import termex.core.extractor.NounPhraseExtractor_openNLP;
import termex.core.extractor.WordExtractor;
import termex.core.feature.FeatureBuilderCorpusTermFrequency;
import termex.core.feature.FeatureBuilderDocumentTermFrequency;
import termex.core.feature.FeatureBuilderRefCorpusTermFrequency;
import termex.core.feature.FeatureCorpusTermFrequency;
import termex.core.feature.FeatureDocumentTermFrequency;
import termex.core.feature.FeatureRefCorpusTermFrequency;
import termex.core.feature.index.GlobalIndexBuilderImpl;
import termex.core.feature.index.GlobalIndexImpl;
import termex.model.corpus.CorpusImpl;
import termex.model.doc.Document;
import termex.util.counters.TermFreqCounter;
import termex.util.counters.WordCounter;
import termex.util.preprocessor.Lemmatizer;
import termex.util.preprocessor.StopList;

/**
 * Entrance of the TermExtraction.
 * <br>Usage: java van [corpus_path] [reference_file_path] [output_folder]
 * <br>[corpus_path]: the directory of your original documents
 * <br>[reference_file_path]: reference documents 
 * <br>[output_folder]: the directory of the output 
 * 
 * @author jyfeather88
 *
 */
public class Van {

	public static void main(String[] args) {
		if (args.length < 3) {
			System.out.println("Usage: java van [corpus_path] [reference_file_path] [output_folder]");
		} else {
			try {
				System.out.println("Begin at: " + new Date());
			
				// use StopList
				StopList stop = new StopList();
				
				// use Lemmatizer
				Lemmatizer lemmatizer = new Lemmatizer();
				
				// create noun phrase extractor, which produces candidate terms as noun phrases
				BasicExtractor npextractor = new NounPhraseExtractor_openNLP(stop, lemmatizer);
				// create single word extractors, required by the below algorithms
				BasicExtractor wordextractor = new WordExtractor(stop, lemmatizer); 
				
				// create 2 counters for words and noun phrases
				TermFreqCounter npcounter = new TermFreqCounter();
				WordCounter wordcounter = new WordCounter();
				
				// Create global resource index builder, which indexes global resources, such as documents and terms and their relations
				GlobalIndexBuilderImpl builder = new GlobalIndexBuilderImpl();
				// Build the global resource index of noun phrases
				GlobalIndexImpl termDocIndex = builder.build(new CorpusImpl(args[0]), npextractor);
				// Build the global resource index of words
				GlobalIndexImpl wordDocIndex = builder.build(new CorpusImpl(args[0]), wordextractor);
				
				// Build a feature wrapper for term extraction algorithm
				FeatureDocumentTermFrequency termDocFreq = 
						new FeatureBuilderDocumentTermFrequency(npcounter, wordcounter, lemmatizer).build(termDocIndex);
				FeatureCorpusTermFrequency wordFreq = 
						new FeatureBuilderCorpusTermFrequency(npcounter, wordcounter, lemmatizer).build(wordDocIndex);
				FeatureRefCorpusTermFrequency bncRef = 
						new FeatureBuilderRefCorpusTermFrequency(args[1]).build(null);
				
				// Algorithm Test
				AlgorithmTester tester = new AlgorithmTester();
				tester.registerAlgorithm(new TermExAlgorithm(), new TermExFeatureWrapper(termDocFreq,wordFreq,bncRef));
				tester.execute(termDocIndex, args[2]);
				
				System.out.println("End at: " + new Date());
			} catch (Exception e) {
				e.printStackTrace();
			} 
		}
	}
}
