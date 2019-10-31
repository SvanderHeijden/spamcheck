/**
 * This class stores four numbers:
 * 
 * 		1.	True positive,
 * 		2.	False positive,
 * 		3.	True negative,
 * 		4.	False negative
 * 
 * The these numbers can be returend by calling the different methods in the class. 
 * 
 * @author 532033sh Sjoerd van der Heijden
 *
 *
 */

public class ConfusionMatrix
{
	private int trueNeg;
	
	private int truePos;
	
	private int falseNeg;
	
	private int falsePos;
	
	/**
	 * When this method is called it stores the following numbers:
	 * 
	 *   	1.	True positive,
	 * 		2.	False positive,
	 * 		3.	True negative,
	 * 		4.	False negative
	 * 
	 * @param trueNegative :classifier predicts 0 for a document 
	 * 		that has classification 0
	 * @param truePositive :classifier predicts 1 for a document 
	 * 		that has classification 1
	 * @param falseNegative :classifier predicts 0 for a document 
	 * 		that has classification 1
	 * @param falsePositive : classifier predicts 1 for a document 
	 * 		that has classification 0
	 */
	
	public void storeData(int trueNegative, int truePositive, int falseNegative, int falsePositive) {
		
		trueNeg = trueNegative;
		
		truePos = truePositive;
		
		falseNeg = falseNegative;
		
		falsePos = falsePositive;
		
	}
	
	/**
	 * When this method is called it returns the trueNegative number.
	 * 
	 * @return trueNeg is the trueNegative number
	 */
	
	public int getTrueNegatives() {
		
		return trueNeg;
		
	}
	
	/**
	 * When this method is called it returns the falseNegative number.
	 * 
	 * @return falseNeg is the falseNegative number
	 */
	
	public int getFalseNegatives() {
		
		return falseNeg;
		
	}
	
	/**
	 * When this method is called it returns the truePositive number.
	 * 
	 * @return truePos is the truePositive number
	 */
	
	public int getTruePositives() {
		
		return truePos;
		
	}
	
	/**
	 * When this method is called it returns the falsePositive number.
	 * 
	 * @return falsePos is the falsePositive number
	 */
	
	public int getFalsePositives() {
		
		return falsePos;
		
	}
}