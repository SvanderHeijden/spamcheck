import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

/**
 * This class has the purpose of classifying documents using the WordCounter
 * class. The constructor requires a String of focusWords. 
 * Samples can be added to train the program. Documents without
 * classification can be processed to compute probabilities. Furthermore, files
 * can be used to train and use the program. Finally, a confusion matrix is 
 * used to check how well the programm preforms. 
 * 
 * @author 532033sh Sjoerd van der Heijden
 *
 */

public class NaiveBayes {

	private double spamSeen = 0;
	
	private double noSpamSeen = 0;
	
	private ArrayList<WordCounter> wc = new ArrayList<>(); 
	
	/**
	 * This constructor requires a String as an input containing focus words
	 * that are used in the WordCounter class. The focus words are 
	 * processed by the WordCounter object. 
	 * 
	 * @param focusWords String [] that contains focus words.
	 */
	
	public NaiveBayes(String [] focusWords) {
		
		for(String focus : focusWords) {
			
			wc.add(new WordCounter(focus));
		}
	}
	
	/**
	 * When this method is called for with a document with a classification,
	 * the document is processes by the WordCounter object. Next, the 
	 * method counts whether it has seen a spam document or a no spam
	 * document.
	 * 
	 * @param document String containing a spam classifier and words.
	 */
	
	public void addSample(String document) {
		
		for(WordCounter word : wc) {
			
			word.addSample(document);
		}
		
		String [] documentParts = document.split(" ");
		
		for(String doc : documentParts) {
			
			if (doc.equals("1")) {
				
				spamSeen++;

			}
			if (doc.equals("0")) {
				
				noSpamSeen++;

			}
		}
	}
	
	/**
	 * When this method is called for with a String withoud classification, 
	 * the String is classified by computing an initial spam score and a 
	 * no spam score. Next, it checks for every word in the String whether
	 * it hold a WordCounter that has word as its focus word. If it does:
	 * 
	 * 		1.	Spam score is set to the old spam score times the 
	 * 			getContidionalSpam() from the WordCounter class.
	 * 		2.	No spam score is set to the old no spam score times the 
	 * 			getContidionalNoSpam() from the WordCounter class.
	 * 
	 * Aditinally it is checked whether no spam score is smaller that 
	 * the spam score. If this is the case a boolean is set to true. 
	 * If not the boolean is set to false. 
	 * 
	 * @param unclassifiedDocument (String) holds a document with words
	 * to be classified by the method.
	 * 
	 * @return myNaiveBoolean (boolean) is either true or false 
	 * that is conditional on whether the no spam score is smallen than
	 * the spam score. 
	 */
	
	public boolean classify(String unclassifiedDocument) {
		
		double spamScore = spamSeen/(spamSeen+noSpamSeen);
		
		double noSpamScore = noSpamSeen/(spamSeen+noSpamSeen);
	
		boolean myNaiveBoolean;
		
		String [] unclassifiedDocumentParts = unclassifiedDocument.split(" ");
		
		for(String item : unclassifiedDocumentParts) {
			
			for(WordCounter word : wc) {
				
				if(item.equals(word.getFocusWord())) {
						
						spamScore = spamScore * word.getConditionalSpam(); 
							
						noSpamScore = noSpamScore * word.getConditionalNoSpam();
		
				}
			}
		}
		
		if(noSpamScore <= spamScore) {
			
			myNaiveBoolean = true;

		}
		else {
			
			myNaiveBoolean = false;

		}
		
		return myNaiveBoolean;
	}
	
	/**
	 * When this method is called for a File is read in such a way that 
	 * every line of the file is consided a document with a classification
	 * that is processed to train the NaiveBayes object. Every line is fed
	 * into the addSample method. 
	 * 
	 * @param trainingFile (File) containing several lines that are
	 * considered as seperate documents. 
	 * 
	 * @throws IOException is thrown when the file cannot be read or
	 * does not exist. 
	 */
	
	public void trainClassifier(File trainingFile) throws IOException {
		
		try(BufferedReader reader = new BufferedReader(new FileReader(trainingFile))){
			
			String document = reader.readLine();
			
			while (document != null) {
				
				addSample(document);
				
				document = reader.readLine();	
			}
		}
	}
	
	/**
	 * When this method is called for with a File without classifications, 
	 * each line in the File is classified using the classify method. Also,
	 * the boolean is converted to binary. 
	 * 
	 * @param input is a File that contains lines of unclassified documents.
	 * 
	 * @param output is a File that contains zeros and ones that indicate
	 * whether the document is considered spam (1) or no spam (0).
	 * 
	 * @throws IOException is thrown when the file cannot be read or
	 * does not exist. 
	 */
	
	public void classifyFile(File input, File output) throws IOException {
		
		try(BufferedReader reader = new BufferedReader(new FileReader(input));
				
			PrintWriter writer = new PrintWriter(output)){
			
			String document = reader.readLine();
			
			while (document != null) {
				
				boolean myBoolean = classify(document);
				
				if(myBoolean) {
					
					writer.println("1");
				}
				else {
					
					writer.println("0");
				}
				
				document = reader.readLine();	
			}
		}
	}
	
	/**
	 * When this method is called for with a String that contains
	 * a classified document and a String that contains "1" or "0" it removes
	 * the given number from the document. 
	 * 
	 * @param document (String) is a classified document of words.
	 * 
	 * @param number (String) is a classification in a document of "1" or "0".
	 * 
	 * @return doc (String) is a ducument withoud the classification number.
	 */
	
	public String removeWord(String document, String number) {
		
		String doc = document;
		
		doc = doc.replaceAll(number, "");
		
		return doc;
	}
	
	/**
	 * When this method is called for with a File containing classified documents
	 * it computes how well the classifier preforms. The training data is split into
	 * a training set and a test set. This method requires a test set. The test
	 * set is used to check whether documents are classified correclty. 
	 * 
	 * @param testdata (File) contains classifications and words on several
	 * lines.
	 * 
	 * @return cm is a ConfisionMatrix containing the number of true/false positives and
	 * true/false negatives.
	 *  
	 * @throws IOException is thrown when the file cannot be read or
	 * does not exist.
	 */
	
	public ConfusionMatrix computeAccuracy(File testdata) throws IOException{
	
		int truePositive = 0;
		
		int trueNegative = 0;
		
		int falsePositive = 0;
		
		int falseNegative = 0;
		
		ConfusionMatrix cm = new ConfusionMatrix();
		
		try(BufferedReader bf = new BufferedReader(new FileReader(testdata))){
			
			String document = bf.readLine();
			
			while (document != null) {
				
				String [] documentParts = document.split(" ");
				
				String spam = documentParts[0];
				
				if(spam.equals("0")) {
					
					document = removeWord(document,"0");
					
				}
				
				if(spam.equals("1")) {
					
					document = removeWord(document,"1");
					
				}
				
				boolean cf = classify(document);
				
				if(spam.equals("0") && !cf) {
					
					trueNegative++;
					
				}
		
				if(spam.equals("1") && !cf) {
					
					falseNegative++;
				}
				
				if(spam.equals("0") && cf) {
					
					falsePositive++;
				}
				
				if(spam.equals("1") && cf) {
					
					truePositive++;
				}
				
				cm.storeData(trueNegative, truePositive, falseNegative, falsePositive);
				
				document = bf.readLine();
		
			}
		}

		return cm;
		
	}
}