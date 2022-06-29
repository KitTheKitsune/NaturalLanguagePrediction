import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

public class LanguageProcessingV2 {

	public static void main(String[] args) throws IOException {
		//Get name of File
		System.out.println("What file should we use for the model?");
		String filename = "warandpeace.txt";//sc.nextLine();
		
		//Pull Contents of File
		String fullText = fileContentsToString(filename);
		String[] allWords = fullText.split(" ");
		
		//Get N of N-gram
		System.out.println("What order model should I create?");
		int N = 3;//Integer.parseInt(sc.nextLine());
		
		//make Corpus
		ArrayList<Corpus> corp = makeFullNgramCorpus(fullText, N);
		
		//Get text to predict after
		System.out.println("Please type the start of the phrase that you would like"
				+ " the model to complete. Every time you hit enter, it will add"
				+ " whatever you have typed to the text, then predict the next word."
				+ " It will continue until you type 'Q'");
		Scanner sc = new Scanner(System.in);
		String text = "";
		String addition = "";
		while(addition != "Q") {
			addition = sc.nextLine();
			text = text + " " +addition;
			System.out.println(text = predict(text,N,corp,allWords));
		}
		System.out.println("Final sentence created: " + text);
		sc.close();
		
	}
	
	/**
	 * Predicts what to add on to the end of a string using an N-gram
	 * @param text
	 * @param N
	 * @param corp
	 * @param fullTextArray
	 * @return
	 */
	public static String predict(String text, int N, ArrayList<Corpus> corp, String[] fullTextArray) {
		ArrayList<String> possibleList = new ArrayList<String>();
		String randomWord = fullTextArray[(int)(Math.random()*(fullTextArray.length-1))];
		for (int i = 0; i < corp.size(); i++) {
			if(corp.get(i).getCheck() == getLastNumWords(N, text)) {
				possibleList.add(corp.get(i).getPredict());
			}
		}
		if(possibleList.size()>0) {
			return text + " " + possibleList.get((int)(Math.random()*(possibleList.size()-1)));
		}else {
			return text + " " + randomWord;
		}
	}
	
	
	
	/**
	 * Converts the contents of a file to a single string
	 * @param filename
	 * @return
	 * @throws IOException
	 */
	public static String fileContentsToString(String filename) throws IOException {
		String text = "";
		return text = new String(Files.readAllBytes(Paths.get(filename)));
	}
	
	/**
	 * Finds the first N words of a string
	 * @param num
	 * @param text
	 * @return
	 */
	public static String getFirstNumWords(int num, String text) {
		String[] words = text.split(" ");
		String newString = "";
		if(words.length < num) {
			return text;
		}else {
			for(int i = 0; i<num; i++) {
				newString = newString + " " + stripWord(words[i]);
			}
			return newString;
		}
		
	}
	
	/**
	 * Cuts the first word off a string
	 * @param text
	 * @return
	 */
	public static String removeFirstWord(String text) {
		String[] words = text.split(" ");
		String newString = "";
		words[0]="";
		for(String i: words) {
			newString = newString + " " + i;
		}
		return newString;
	}
	
	/**
	 * Creates a collection of Corpus 
	 * @param text the full text used to make the corpus
	 * @param num the N-gram of the Corpus
	 * @return
	 */
	public static ArrayList<Corpus> makeFullNgramCorpus(String text, int num) {
		ArrayList<Corpus> newCorp = new ArrayList<Corpus>();
		while(text != "" && text.length() > num) {
			Corpus e = new Corpus(num,getFirstNumWords(num,text));
			newCorp.add(e);
			text = stripWord(removeFirstWord(text));
		}
		return newCorp;
	}
	
	
	/**
	 * Finds the last N words in a string
	 * @param num
	 * @param text
	 * @return
	 */
	public static String getLastNumWords(int num, String text) {
		String[] words = text.split(" ");
		String newString = "";
		if(words.length > num) {
			for(int i = words.length - (num+1); i<words.length;i++) {
				newString = newString + " " + words[i];
			}
				return newString;
		}else {
			return text;
		}
		
	}
	
	/**
	* Strips the provided word of non-letter characters at the beginning
	* and the end of the word.
	* @param word the word to strip
	* @return a lowercase version of the word with leading and
	*     trailing non-letter characters removed
	*/
	public static String stripWord(String word) {
	    String newWord = word;
	    while(newWord.length() > 0 && !Character.isLetter(newWord.charAt(0))) {
	        newWord = newWord.substring(1);
	    }
	    while(newWord.length() > 0 && !Character.isLetter(newWord.charAt(newWord.length()-1))) {
	        newWord = newWord.substring(0, newWord.length()-1);
	    }
	    return newWord.toLowerCase();
	}
	
	/**
	 * Method that strips all words in a collection
	 * @param list
	 * @return
	 */
	private static String[] stripAllWords(String[] list) {
		String[] newList = new String[list.length];
		int n = 0;
		for(String i: list) {
			newList[n] = stripWord(i);
		}
		return newList;
	}

}
