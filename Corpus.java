/**
 * Class for a Corpus item, being a sentence fragment from a published text.
 * @author Kendrick Smith
 *
 */
public class Corpus {

	String text;
	String check;
	String predict;
	int N;
	
	public Corpus(int N) {
		this.N = N;
	}
	
	public Corpus(int N, String text) {
		this.N = N;
		this.text = text;
		this.predict = lastWord(text);
		this.check = butLastWord(text);
		
	}
	
	/**
	 * Adds a new word onto the end of the corpus sentence fragment if the fragment is not yet N-1 in length
	 * @param text word to be added
	 */
	public void add(String text) {
		if(this.text != null) {
			String[] list = this.text.split(" ");
			if(list.length < N) {
				text.strip();
				if(text.split(" ").length < 2) {
					this.text = this.text + " " + text;
				}else {
					System.out.println("Cannot add more than one word to corpus at once.");
				}
			}else {
				System.out.println("Failed. No room in Corpus.");
			}
		}else {
			this.text = text;
		}
		if(this.text.split(" ").length > 1) {
			this.predict = lastWord(this.text);
			this.check = butLastWord(this.text);
		}
	}
	
	public void set(String text) {
		if(text.split(" ").length != N) {
			System.out.println("Sentence fragment wrong size for corpus.");
		}else {
			this.text = text;
		}
		this.predict = lastWord(this.text);
		this.check = butLastWord(this.text);
	}
	
	public String lastWord(String text) {
		String[] list = text.split(" ");
		return list[list.length-1];
		
	}
	
	public String butLastWord(String text) {
		String[] list = text.split(" ");
		String substring = "";
		for(int i = 0; i < list.length - 1; i++) {
			substring = substring + " " + list[i];
		}
		return substring;
	}
	
	public String getCheck() {
		return this.check;
	}
	
	public String getPredict() {
		return this.predict;
	}
}
