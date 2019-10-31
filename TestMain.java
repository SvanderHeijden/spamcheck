import java.io.File;
import java.io.IOException;

public class TestMain
{

	public static void main(String [] args) throws IOException
	{
		testWordCounter();
		testClassification();
		testFileReadingWriting();
		testConfusionMatrix();
	}
	
	public static void testWordCounter()
	{
		System.out.println("testWordCounter");
		System.out.println(" ");
		WordCounter wc = new WordCounter("good");
		System.out.println(wc.getFocusWord());
		wc.addSample("1 good bad bad bad");
		wc.addSample("0 bad good good");
		wc.addSample("0 bad good");
		System.out.println(wc.getConditionalSpam());
		System.out.println(wc.getConditionalNoSpam());	
		System.out.println(" ");
	}
	
	public static void testClassification()
	{
		System.out.println("testClassification");
		System.out.println(" ");
		String [] words = {"good", "bad"};
		NaiveBayes nb = new NaiveBayes(words);
		nb.addSample("1 good bad bad bad casino");
		nb.addSample("0 bad good good pizza");
		nb.addSample("0 bad good tapas");
	
		System.out.println(nb.classify("good"));
		System.out.println(nb.classify("bad"));
		System.out.println(nb.classify("good bad bad"));
		System.out.println(nb.classify("pizza"));
		System.out.println(nb.classify("casino"));	
		System.out.println(" ");
	}
	
	public static void testFileReadingWriting() throws IOException
	{
		System.out.println("testFileReadingWriting");
		System.out.println(" ");
		String [] words = {"good", "bad"};
		NaiveBayes nb = new NaiveBayes(words);
		File trainData = new File("src/traindata.txt");
		nb.trainClassifier(trainData);
		
		File newData = new File("src/newdata.txt");
		nb.classifyFile(newData, new File("classifications.txt"));
	}
	
	public static void testConfusionMatrix() throws IOException
	{
		String [] words = {"good", "bad"};
		NaiveBayes nb = new NaiveBayes(words);
		nb.trainClassifier(new File("src/traindata.txt"));
		ConfusionMatrix cm = nb.computeAccuracy(new File("src/testdata.txt"));
		System.out.println(cm.getTruePositives());
		System.out.println(cm.getFalsePositives());
		System.out.println(cm.getTrueNegatives());
		System.out.println(cm.getFalseNegatives());		
	}
	
}
