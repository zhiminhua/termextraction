package termex.test;

import java.util.*;

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

public class Van {

	public static void main(String[] args) {
		if (args.length < 3) {
			System.out.println("Usage: java van [corpus_path] [reference_corpus_path] [output_folder]");
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
				
				//############################# Test BIGEN ##############################
				// For NounPhraseExtraction
//				Map<String, Set<String>> test = new HashMap<String, Set<String>>();
//				test = npextractor.extract(new CorpusImpl(args[0]));
//				for (Map.Entry<String, Set<String>> e : test.entrySet()) {
//					String termid = e.getKey();
//					for (String v : e.getValue()) {
//						System.out.println(termid + " : " + v);
//					}
//				}
				
				// For WordExtraction
//				Map<String, Set<String>> test = new HashMap<String, Set<String>>();
//				test = wordextractor.extract(new CorpusImpl(args[0]));
//				for (Map.Entry<String, Set<String>> e : test.entrySet()) {
//					String termid = e.getKey();
//					for (String v : e.getValue()) {
//						System.out.println(termid + " : " + v);
//					}
//				}
				//############################# Test END ###############################
				
				// create 2 counters for words and noun phrases
				TermFreqCounter npcounter = new TermFreqCounter();
				WordCounter wordcounter = new WordCounter();
				
				// Create global resource index builder, which indexes global resources, such as documents and terms and their relations
				GlobalIndexBuilderImpl builder = new GlobalIndexBuilderImpl();
				// Build the global resource index of noun phrases
				GlobalIndexImpl termDocIndex = builder.build(new CorpusImpl(args[0]), npextractor);
				// Build the global resource index of words
				GlobalIndexImpl wordDocIndex = builder.build(new CorpusImpl(args[0]), wordextractor);
				
				//############################# Test BIGEN ##############################
				// For wordDocIndex
//				Iterator<String> it1 = wordDocIndex.getTermIdMap().keySet().iterator();
//				while (it1.hasNext()) {
//					String str1 = it1.next();
//					Integer int1 = wordDocIndex.getTermIdMap().get(str1);
//					System.out.println( str1 + " : " + int1);
//				}
				
//				Iterator<String> it2 = wordDocIndex.getVariantIdMap().keySet().iterator();
//				while (it2.hasNext()) {
//					String str2 = it2.next();
//					Integer int2 = wordDocIndex.getVariantIdMap().get(str2);
//					System.out.println( str2 + " : " + int2);
//				}
				
//				Iterator<Integer> it3 = wordDocIndex.getTerm2Variants().keySet().iterator();
//				while (it3.hasNext()) {
//					Integer int3 = it3.next();
//					System.out.print( int3 + " : ");
//					for (Integer int30 : wordDocIndex.getTerm2Variants().get(int3)) {
//						System.out.print( int30 + " ");
//					}
//					System.out.println();
//				}
				
//				Iterator<Integer> it4 = wordDocIndex.getVariant2Term().keySet().iterator();
//				while (it4.hasNext()) {
//					Integer int4 = it4.next();
//					Integer int40 = wordDocIndex.getVariant2Term().get(int4);
//					System.out.println(int4 + " : " + int40);
//				}
				
//				Iterator<Integer> it5 = wordDocIndex.getDoc2Terms().keySet().iterator();
//				while (it5.hasNext()) {
//					Integer int5 = it5.next();
//					System.out.print( int5 + " : ");
//					for (Integer int50 : wordDocIndex.getDoc2Terms().get(int5)) {
//						System.out.print( int50 + " ");
//					}
//					System.out.println();
//				}
				
//				Iterator<Document> it6 = wordDocIndex.getDocIdMap().keySet().iterator();
//				while (it6.hasNext()) {
//					Document doc6 = it6.next();
//					Integer int6 = wordDocIndex.getDocIdMap().get(doc6);
//					System.out.println(doc6.toString() + " : " + int6);
//				}
				
//				Iterator<Integer> it7 = wordDocIndex.getTerm2Docs().keySet().iterator();
//				while (it7.hasNext()) {
//					Integer int7 = it7.next();
//					System.out.print( int7 + " : ");
//					for (Integer int70 : wordDocIndex.getTerm2Docs().get(int7)) {
//						System.out.print( int70 + " ");
//					}
//					System.out.println();
//				}
				//############################# Test END ###############################
				
				// Build a feature wrapper for term extraction algorithm
				FeatureDocumentTermFrequency termDocFreq = 
						new FeatureBuilderDocumentTermFrequency(npcounter, wordcounter, lemmatizer).build(termDocIndex);
				FeatureCorpusTermFrequency wordFreq = 
						new FeatureBuilderCorpusTermFrequency(npcounter, wordcounter, lemmatizer).build(wordDocIndex);
				FeatureRefCorpusTermFrequency bncRef = 
						new FeatureBuilderRefCorpusTermFrequency(args[1]).build(null);
						
				//############################# Test BIGEN ##############################
				// For termDocFreq
//				System.out.println("Total Word in Corpus : " + termDocFreq.getTotalCorpusTermFreq());
//				Set<String> set2 = termDocIndex.getTermIdMap().keySet();
//				for (String s2 : set2) {
//					Map<Integer, Integer> map1 = termDocFreq.getDocFreqMap(termDocIndex.getTermIdMap().get(s2));
//					System.out.print(s2 + " : ");
//					for (int doc1 : map1.keySet()) {
//						System.out.print(map1.get(doc1) + " in " + doc1 + " | ");
//					}
//					System.out.println();
//				}
				
				// For wordFreq
//				System.out.println("Total Word in Corpus : " + wordFreq.getTotalCorpusTermFreq());
//				Set<String> set1 = wordDocIndex.getTermIdMap().keySet();
//				Iterator<String> it1 = set1.iterator();
//				while (it1.hasNext()) {
//					String s1 = it1.next();
//					System.out.println(s1 + " : " + wordFreq.getTermFreq(s1));
//				}
				
				// For bncRef
//				System.out.println("freq of 'the' is " + bncRef.getTermFreq("the"));
//				System.out.println("normalized freq of 'the' is " + bncRef.getNormalizedTermFreq("the"));
				//############################# Test END ###############################
				
				// Algorithm Test
//				... ...
				
				System.out.println("End at: " + new Date());
			} catch (Exception e) {
				e.printStackTrace();
			} 
		}
	}
}
