import java.io.File;
import java.lang.Math;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;



/**
 * 
 * @author Kendrick Smith
 *
 */
public class LanguageProcessing {
	
	int gram;
	

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ArrayList<Corpus> corpusList = new ArrayList<Corpus>();

		Scanner sc = new Scanner(System.in);
		
		System.out.println("What file should we use for the model?");
		String filename = "warandpeace.txt";//sc.nextLine();
		File file = new File(filename);
		
		System.out.println("What order model should I create?");
		int N = 3;//Integer.parseInt(sc.nextLine());
		String[] wordsSave = null;
		try {
			Scanner reader = new Scanner(file);
			System.out.println(file);
			String fullText = "";
			while(reader.hasNext()) {
				String line = reader.nextLine();
				fullText += line;
			}
			String[] words = fullText.split(" ");
			wordsSave = words;
			for(int i = 0; i < words.length; i++) {
				words[i] = stripWord(words[i]);
			}
			for(int i = 0; i < words.length; i++) {
				Corpus corp = new Corpus(N);
				for(int j = 0; j < N; j++) {
					if(i<words.length-2) {
						corp.add(words[i+j]);
					}
				}
				corpusList.add(corp);
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		System.out.println("Please type the start of the phrase that you would like"
				+ " the model to complete. Every time you hit enter, it will add"
				+ " whatever you have typed to the text, then predict the next word."
				+ " It will continue until you type 'Q'");
		String text = "";
		while(sc.nextLine() != "Q") {
			String addition = sc.nextLine();
			text = text + addition;
			System.out.println(text = text + " " + predict(text,N,corpusList,wordsSave));
		}
		System.out.println("Final sentence created: " + text);
		sc.close();
	}

	/**
	 * Predicts the next word of a sentence given.
	 * @param text the sentence prior to additional words
	 * @return the input text concatenated with the predicted word
	 */
	private static String predict(String text, int N, ArrayList<Corpus> corpusList, String[] fullText) {
		// TODO Auto-generated method stub
		ArrayList<String> possibleList = new ArrayList<String>();
		for (int i = 0; i < corpusList.size(); i++) {
			if(corpusList.get(i).getCheck() == getLastNWords(N-1, text)) {
				possibleList.add(corpusList.get(i).getPredict());
			}
		}
		if(possibleList.size()>0) {
			return possibleList.get((int)(Math.random()*(possibleList.size()-1)));
		}else {
			return fullText[(int)(Math.random()*(fullText.length-1))];
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
	 * 
	 * @param N The number of words to get
	 * @param text
	 * @return
	 */
	public static String getLastNWords(int N, String text) {
		String[] broken = text.split(" ");
		String string = "";
		if (broken.length > 3) {
			for(int i = broken.length -N;i<broken.length;i++) {
				string = string + broken[i];
			}
		}else {
			string = text;
		}
		return string;
	}

}
