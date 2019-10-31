/**
 * This class had the purpose to count how many times a certain word
 * apprears in a document it has seen. Furthermore, utility methods
 * extracts the necessary information from an imput file. Additionally,
 * probability methods compute coditional and uncondition probabilities
 * that a document is spam. 
 * 
 * @author 532033sh Sjoerd van der Heijden
 *
 */

public class WordCounter {
	
	private String focusWord;
	
	private double wordCountSpam;
	
	private double wordCountNoSpam;
	
	private double focusWordCountSpam;
	
	private double focusWordCountNoSpam;
	
	/**
	 * When this method is called for with a String it initiates a WordCounter
	 *  
	 * @param focusWord stores a certain word 
	 */
	
	public WordCounter(String focusWord) {
		
		this.focusWord = focusWord;	
	}
	
	/**
	 * When this method is called it returns the focus word.
	 * 
	 * @return focusWordReturn is the focus word used in this class.
	 */
	
	public String getFocusWord() {
		
		String focusWordReturn = focusWord;
		
		return focusWordReturn;
	}
	
	/**
	 * When this method is called with a String it extracts the following 
	 * information:
	 * 
	 * 		1.	Whether the String has classification spam or no spam.
	 * 		2.	How many times the focusWord appears.
	 * 		3.	The total number of words in the String.
	 * 
	 * @param document (String) that contains words and a classification (0 or 1)
	 * that is indicates if a document is spam or not. 
	 */
	
	public void addSample(String document) {

		String [] documentParts = document.split(" ");		
		
		for(String doc : documentParts) {
			
			if(doc.equals("0")) {
				
				wordCountNoSpam = wordCountNoSpam + documentParts.length - 1;
							
				for(String item : documentParts) {
					
					if(item.equals(focusWord)) {
						
						focusWordCountNoSpam++;
						
					}
				}
			}
			if(doc.equals("1")) {
				
				wordCountSpam = wordCountSpam + documentParts.length - 1;
				
				for(String item : documentParts) {
					
					if(item.contentEquals(focusWord)){
						
						focusWordCountSpam++;
						
					}
				}
			}
		}
	}
	
	/**
	 * When this method is called for it checks whether WordCounter has seen enough information to 
	 * consider that it is properly trained. The following must hold:
	 * 
	 * 		1.	A focusWords has been seen in at least one document.
	 * 		2.	It has seen at least one spam document.
	 * 		3.	At least one no spam document has been seen.
	 * 
	 * @return myBoolean (boolean) that is true when the WordCounter has been trained
	 * and false when it is not. 
	 */
	
	public boolean isCounterTrained() {
		boolean trained = focusWordCountSpam+focusWordCountNoSpam >= 1 && wordCountSpam >= 1 && wordCountNoSpam >= 1;
		
		if (trained) {
			boolean myBoolean = true;
			
			return myBoolean;
		}
		
		else {
			boolean myBoolean = false;
			
			return myBoolean;
		}
	}
	
	/**
	 * When this method is called for it returns estimates for the probability that a random word
	 * from a document is the focusWord conditional on the fact that the document
	 * is classified as no spam. 
	 * 
	 * @return myConditionalNoSpam (double) probability that a random word
	 * from a document is the focusword conditional on whether the document
	 * is classified as no spam. 
	 */
	
	
	public double getConditionalNoSpam(){
		
		boolean checkBoolean = isCounterTrained();
		
		if (!checkBoolean) {
			throw new IllegalStateException("Object not trained!");
		}
	
		double myConditionalNoSpam = focusWordCountNoSpam/wordCountNoSpam;
			
		return myConditionalNoSpam;
		
	}

	/**
	 * When this method is called for it returns estimates for the probability that a random word
	 * from a document is the focusWord conditional on the fact that the document
	 * is classified as spam. 
	 * 
	 * @return myConditionalSpam (double) probability that a random word
	 * from a document is the focusword conditional on whether the document
	 * is classified as spam. 
	 */
	
	public double getConditionalSpam() {
		
		boolean checkBoolean = isCounterTrained();
		
		if (!checkBoolean) {
			throw new IllegalStateException("Object not trained!");
		}
	
		double myConditionalSpam = focusWordCountSpam/wordCountSpam;
		
		return myConditionalSpam;
	}
}
